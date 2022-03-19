package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {
   private String translator = "en_US";
   public NetServerHandler playerNetServerHandler;
   public MinecraftServer mcServer;
   public ItemInWorldManager theItemInWorldManager;
   public double managedPosX;
   public double managedPosZ;
   public final List loadedChunks = new LinkedList();
   public final List destroyedItemsNetCache = new LinkedList();
   private float field_130068_bO = Float.MIN_VALUE;
   private float lastHealth = -1.0E8F;
   private int lastFoodLevel = -99999999;
   private boolean wasHungry = true;
   private int lastExperience = -99999999;
   private int initialInvulnerability = 60;
   private int renderDistance;
   private int chatVisibility;
   private boolean chatColours = true;
   private long field_143005_bX = 0L;
   private int currentWindowId;
   public boolean playerInventoryBeingManipulated;
   public int ping;
   public boolean playerConqueredTheEnd;

   public EntityPlayerMP(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager) {
      super(par2World, par3Str);
      par4ItemInWorldManager.thisPlayerMP = this;
      this.theItemInWorldManager = par4ItemInWorldManager;
      this.renderDistance = par1MinecraftServer.getConfigurationManager().getViewDistance();
      ChunkCoordinates var5 = par2World.getSpawnPoint();
      int var6 = var5.posX;
      int var7 = var5.posZ;
      int var8 = var5.posY;
      if(!par2World.provider.hasNoSky && par2World.getWorldInfo().getGameType() != EnumGameType.ADVENTURE) {
         int var9 = Math.max(5, par1MinecraftServer.getSpawnProtectionSize() - 6);
         var6 += this.rand.nextInt(var9 * 2) - var9;
         var7 += this.rand.nextInt(var9 * 2) - var9;
         var8 = par2World.getTopSolidOrLiquidBlock(var6, var7);
      }

      this.mcServer = par1MinecraftServer;
      this.stepHeight = 0.0F;
      this.yOffset = 0.0F;
      this.setLocationAndAngles((double)var6 + 0.5D, (double)var8, (double)var7 + 0.5D, 0.0F, 0.0F);

      while(!par2World.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
         this.setPosition(this.posX, this.posY + 1.0D, this.posZ);
      }
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      if(par1NBTTagCompound.hasKey("playerGameType")) {
         if(MinecraftServer.getServer().getForceGamemode()) {
            this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
         } else {
            this.theItemInWorldManager.setGameType(EnumGameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
         }
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
   }

   public void addExperienceLevel(int par1) {
      super.addExperienceLevel(par1);
      this.lastExperience = -1;
   }

   public void addSelfToInternalCraftingInventory() {
      this.openContainer.addCraftingToCrafters(this);
   }

   protected void resetHeight() {
      this.yOffset = 0.0F;
   }

   public float getEyeHeight() {
      return 1.62F;
   }

   public void onUpdate() {
      this.theItemInWorldManager.updateBlockRemoving();
      --this.initialInvulnerability;
      this.openContainer.detectAndSendChanges();
      if(!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
         this.closeScreen();
         this.openContainer = this.inventoryContainer;
      }

      while(!this.destroyedItemsNetCache.isEmpty()) {
         int var1 = Math.min(this.destroyedItemsNetCache.size(), 127);
         int[] var2 = new int[var1];
         Iterator var3 = this.destroyedItemsNetCache.iterator();
         int var4 = 0;

         while(var3.hasNext() && var4 < var1) {
            var2[var4++] = ((Integer)var3.next()).intValue();
            var3.remove();
         }

         this.playerNetServerHandler.sendPacketToPlayer(new Packet29DestroyEntity(var2));
      }

      if(!this.loadedChunks.isEmpty()) {
         ArrayList var6 = new ArrayList();
         Iterator var7 = this.loadedChunks.iterator();
         ArrayList var8 = new ArrayList();

         while(var7.hasNext() && var6.size() < 5) {
            ChunkCoordIntPair var9 = (ChunkCoordIntPair)var7.next();
            var7.remove();
            if(var9 != null && this.worldObj.blockExists(var9.chunkXPos << 4, 0, var9.chunkZPos << 4)) {
               var6.add(this.worldObj.getChunkFromChunkCoords(var9.chunkXPos, var9.chunkZPos));
               var8.addAll(((WorldServer)this.worldObj).getAllTileEntityInBox(var9.chunkXPos * 16, 0, var9.chunkZPos * 16, var9.chunkXPos * 16 + 16, 256, var9.chunkZPos * 16 + 16));
            }
         }

         if(!var6.isEmpty()) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet56MapChunks(var6));
            Iterator var10 = var8.iterator();

            while(var10.hasNext()) {
               TileEntity var5 = (TileEntity)var10.next();
               this.sendTileEntityToPlayer(var5);
            }

            var10 = var6.iterator();

            while(var10.hasNext()) {
               Chunk var11 = (Chunk)var10.next();
               this.getServerForPlayer().getEntityTracker().func_85172_a(this, var11);
            }
         }
      }

      if(this.field_143005_bX > 0L && this.mcServer.func_143007_ar() > 0 && MinecraftServer.getSystemTimeMillis() - this.field_143005_bX > (long)(this.mcServer.func_143007_ar() * 1000 * 60)) {
         this.playerNetServerHandler.kickPlayerFromServer("You have been idle for too long!");
      }
   }

   public void onUpdateEntity() {
      try {
         super.onUpdate();

         for(int var1 = 0; var1 < this.inventory.getSizeInventory(); ++var1) {
            ItemStack var6 = this.inventory.getStackInSlot(var1);
            if(var6 != null && Item.itemsList[var6.itemID].isMap() && this.playerNetServerHandler.packetSize() <= 5) {
               Packet var8 = ((ItemMapBase)Item.itemsList[var6.itemID]).createMapDataPacket(var6, this.worldObj, this);
               if(var8 != null) {
                  this.playerNetServerHandler.sendPacketToPlayer(var8);
               }
            }
         }

         if(this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0F != this.wasHungry) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet8UpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
            this.lastHealth = this.getHealth();
            this.lastFoodLevel = this.foodStats.getFoodLevel();
            this.wasHungry = this.foodStats.getSaturationLevel() == 0.0F;
         }

         if(this.getHealth() + this.getAbsorptionAmount() != this.field_130068_bO) {
            this.field_130068_bO = this.getHealth() + this.getAbsorptionAmount();
            Collection var5 = this.getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.health);
            Iterator var7 = var5.iterator();

            while(var7.hasNext()) {
               ScoreObjective var9 = (ScoreObjective)var7.next();
               this.getWorldScoreboard().func_96529_a(this.getEntityName(), var9).func_96651_a(Arrays.asList(new EntityPlayer[]{this}));
            }
         }

         if(this.experienceTotal != this.lastExperience) {
            this.lastExperience = this.experienceTotal;
            this.playerNetServerHandler.sendPacketToPlayer(new Packet43Experience(this.experience, this.experienceTotal, this.experienceLevel));
         }
      } catch (Throwable var4) {
         CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
         CrashReportCategory var3 = var2.makeCategory("Player being ticked");
         this.addEntityCrashInfo(var3);
         throw new ReportedException(var2);
      }
   }

   public void onDeath(DamageSource par1DamageSource) {
      this.mcServer.getConfigurationManager().sendChatMsg(this.func_110142_aN().func_94546_b());
      if(!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
         this.inventory.dropAllItems();
      }

      Collection var2 = this.worldObj.getScoreboard().func_96520_a(ScoreObjectiveCriteria.deathCount);
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         ScoreObjective var4 = (ScoreObjective)var3.next();
         Score var5 = this.getWorldScoreboard().func_96529_a(this.getEntityName(), var4);
         var5.func_96648_a();
      }

      EntityLivingBase var6 = this.func_94060_bK();
      if(var6 != null) {
         var6.addToPlayerScore(this, this.scoreValue);
      }

      this.addStat(StatList.deathsStat, 1);
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.isEntityInvulnerable()) {
         return false;
      } else {
         boolean var3 = this.mcServer.isDedicatedServer() && this.mcServer.isPVPEnabled() && "fall".equals(par1DamageSource.damageType);
         if(!var3 && this.initialInvulnerability > 0 && par1DamageSource != DamageSource.outOfWorld) {
            return false;
         } else {
            if(par1DamageSource instanceof EntityDamageSource) {
               Entity var4 = par1DamageSource.getEntity();
               if(var4 instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var4)) {
                  return false;
               }

               if(var4 instanceof EntityArrow) {
                  EntityArrow var5 = (EntityArrow)var4;
                  if(var5.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var5.shootingEntity)) {
                     return false;
                  }
               }
            }

            return super.attackEntityFrom(par1DamageSource, par2);
         }
      }
   }

   public boolean canAttackPlayer(EntityPlayer par1EntityPlayer) {
      return !this.mcServer.isPVPEnabled()?false:super.canAttackPlayer(par1EntityPlayer);
   }

   public void travelToDimension(int par1) {
      if(this.dimension == 1 && par1 == 1) {
         this.triggerAchievement(AchievementList.theEnd2);
         this.worldObj.removeEntity(this);
         this.playerConqueredTheEnd = true;
         this.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(4, 0));
      } else {
         if(this.dimension == 0 && par1 == 1) {
            this.triggerAchievement(AchievementList.theEnd);
            ChunkCoordinates var2 = this.mcServer.worldServerForDimension(par1).getEntrancePortalLocation();
            if(var2 != null) {
               this.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY, (double)var2.posZ, 0.0F, 0.0F);
            }

            par1 = 1;
         } else {
            this.triggerAchievement(AchievementList.portal);
         }

         this.mcServer.getConfigurationManager().transferPlayerToDimension(this, par1);
         this.lastExperience = -1;
         this.lastHealth = -1.0F;
         this.lastFoodLevel = -1;
      }
   }

   private void sendTileEntityToPlayer(TileEntity par1TileEntity) {
      if(par1TileEntity != null) {
         Packet var2 = par1TileEntity.getDescriptionPacket();
         if(var2 != null) {
            this.playerNetServerHandler.sendPacketToPlayer(var2);
         }
      }
   }

   public void onItemPickup(Entity par1Entity, int par2) {
      super.onItemPickup(par1Entity, par2);
      this.openContainer.detectAndSendChanges();
   }

   public EnumStatus sleepInBedAt(int par1, int par2, int par3) {
      EnumStatus var4 = super.sleepInBedAt(par1, par2, par3);
      if(var4 == EnumStatus.OK) {
         Packet17Sleep var5 = new Packet17Sleep(this, 0, par1, par2, par3);
         this.getServerForPlayer().getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, var5);
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         this.playerNetServerHandler.sendPacketToPlayer(var5);
      }

      return var4;
   }

   public void wakeUpPlayer(boolean par1, boolean par2, boolean par3) {
      if(this.isPlayerSleeping()) {
         this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 3));
      }

      super.wakeUpPlayer(par1, par2, par3);
      if(this.playerNetServerHandler != null) {
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      }
   }

   public void mountEntity(Entity par1Entity) {
      super.mountEntity(par1Entity);
      this.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(0, this, this.ridingEntity));
      this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
   }

   protected void updateFallState(double par1, boolean par3) {}

   public void updateFlyingState(double par1, boolean par3) {
      super.updateFallState(par1, par3);
   }

   public void displayGUIEditSign(TileEntity par1TileEntity) {
      if(par1TileEntity instanceof TileEntitySign) {
         ((TileEntitySign)par1TileEntity).func_142010_a(this);
         this.playerNetServerHandler.sendPacketToPlayer(new Packet133TileEditorOpen(0, par1TileEntity.xCoord, par1TileEntity.yCoord, par1TileEntity.zCoord));
      }
   }

   private void incrementWindowID() {
      this.currentWindowId = this.currentWindowId % 100 + 1;
   }

   public void displayGUIWorkbench(int par1, int par2, int par3) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 1, "Crafting", 9, true));
      this.openContainer = new ContainerWorkbench(this.inventory, this.worldObj, par1, par2, par3);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 4, par4Str == null?"":par4Str, 9, par4Str != null));
      this.openContainer = new ContainerEnchantment(this.inventory, this.worldObj, par1, par2, par3);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIAnvil(int par1, int par2, int par3) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 8, "Repairing", 9, true));
      this.openContainer = new ContainerRepair(this.inventory, this.worldObj, par1, par2, par3, this);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIChest(IInventory par1IInventory) {
      if(this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory(), par1IInventory.isInvNameLocalized()));
      this.openContainer = new ContainerChest(this.inventory, par1IInventory);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIHopper(TileEntityHopper par1TileEntityHopper) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 9, par1TileEntityHopper.getInvName(), par1TileEntityHopper.getSizeInventory(), par1TileEntityHopper.isInvNameLocalized()));
      this.openContainer = new ContainerHopper(this.inventory, par1TileEntityHopper);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 9, par1EntityMinecartHopper.getInvName(), par1EntityMinecartHopper.getSizeInventory(), par1EntityMinecartHopper.isInvNameLocalized()));
      this.openContainer = new ContainerHopper(this.inventory, par1EntityMinecartHopper);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory(), par1TileEntityFurnace.isInvNameLocalized()));
      this.openContainer = new ContainerFurnace(this.inventory, par1TileEntityFurnace);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, par1TileEntityDispenser instanceof TileEntityDropper?10:3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory(), par1TileEntityDispenser.isInvNameLocalized()));
      this.openContainer = new ContainerDispenser(this.inventory, par1TileEntityDispenser);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory(), par1TileEntityBrewingStand.isInvNameLocalized()));
      this.openContainer = new ContainerBrewingStand(this.inventory, par1TileEntityBrewingStand);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIBeacon(TileEntityBeacon par1TileEntityBeacon) {
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 7, par1TileEntityBeacon.getInvName(), par1TileEntityBeacon.getSizeInventory(), par1TileEntityBeacon.isInvNameLocalized()));
      this.openContainer = new ContainerBeacon(this.inventory, par1TileEntityBeacon);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str) {
      this.incrementWindowID();
      this.openContainer = new ContainerMerchant(this.inventory, par1IMerchant, this.worldObj);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
      InventoryMerchant var3 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 6, par2Str == null?"":par2Str, var3.getSizeInventory(), par2Str != null));
      MerchantRecipeList var4 = par1IMerchant.getRecipes(this);
      if(var4 != null) {
         try {
            ByteArrayOutputStream var5 = new ByteArrayOutputStream();
            DataOutputStream var6 = new DataOutputStream(var5);
            var6.writeInt(this.currentWindowId);
            var4.writeRecipiesToStream(var6);
            this.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TrList", var5.toByteArray()));
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      }
   }

   public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory) {
      if(this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 11, par2IInventory.getInvName(), par2IInventory.getSizeInventory(), par2IInventory.isInvNameLocalized(), par1EntityHorse.entityId));
      this.openContainer = new ContainerHorseInventory(this.inventory, par2IInventory, par1EntityHorse);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack) {
      if(!(par1Container.getSlot(par2) instanceof SlotCrafting)) {
         if(!this.playerInventoryBeingManipulated) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
         }
      }
   }

   public void sendContainerToPlayer(Container par1Container) {
      this.sendContainerAndContentsToPlayer(par1Container, par1Container.getInventory());
   }

   public void sendContainerAndContentsToPlayer(Container par1Container, List par2List) {
      this.playerNetServerHandler.sendPacketToPlayer(new Packet104WindowItems(par1Container.windowId, par2List));
      this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, this.inventory.getItemStack()));
   }

   public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {
      this.playerNetServerHandler.sendPacketToPlayer(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
   }

   public void closeScreen() {
      this.playerNetServerHandler.sendPacketToPlayer(new Packet101CloseWindow(this.openContainer.windowId));
      this.closeContainer();
   }

   public void updateHeldItem() {
      if(!this.playerInventoryBeingManipulated) {
         this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, this.inventory.getItemStack()));
      }
   }

   public void closeContainer() {
      this.openContainer.onContainerClosed(this);
      this.openContainer = this.inventoryContainer;
   }

   public void setEntityActionState(float par1, float par2, boolean par3, boolean par4) {
      if(this.ridingEntity != null) {
         if(par1 >= -1.0F && par1 <= 1.0F) {
            this.moveStrafing = par1;
         }

         if(par2 >= -1.0F && par2 <= 1.0F) {
            this.moveForward = par2;
         }

         this.isJumping = par3;
         this.setSneaking(par4);
      }
   }

   public void addStat(StatBase par1StatBase, int par2) {
      if(par1StatBase != null) {
         if(!par1StatBase.isIndependent) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, par2));
         }
      }
   }

   public void mountEntityAndWakeUp() {
      if(this.riddenByEntity != null) {
         this.riddenByEntity.mountEntity(this);
      }

      if(this.sleeping) {
         this.wakeUpPlayer(true, false, false);
      }
   }

   public void setPlayerHealthUpdated() {
      this.lastHealth = -1.0E8F;
   }

   public void addChatMessage(String par1Str) {
      this.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(ChatMessageComponent.createFromTranslationKey(par1Str)));
   }

   protected void onItemUseFinish() {
      this.playerNetServerHandler.sendPacketToPlayer(new Packet38EntityStatus(this.entityId, (byte)9));
      super.onItemUseFinish();
   }

   public void setItemInUse(ItemStack par1ItemStack, int par2) {
      super.setItemInUse(par1ItemStack, par2);
      if(par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat) {
         this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 5));
      }
   }

   public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2) {
      super.clonePlayer(par1EntityPlayer, par2);
      this.lastExperience = -1;
      this.lastHealth = -1.0F;
      this.lastFoodLevel = -1;
      this.destroyedItemsNetCache.addAll(((EntityPlayerMP)par1EntityPlayer).destroyedItemsNetCache);
   }

   protected void onNewPotionEffect(PotionEffect par1PotionEffect) {
      super.onNewPotionEffect(par1PotionEffect);
      this.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.entityId, par1PotionEffect));
   }

   protected void onChangedPotionEffect(PotionEffect par1PotionEffect, boolean par2) {
      super.onChangedPotionEffect(par1PotionEffect, par2);
      this.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.entityId, par1PotionEffect));
   }

   protected void onFinishedPotionEffect(PotionEffect par1PotionEffect) {
      super.onFinishedPotionEffect(par1PotionEffect);
      this.playerNetServerHandler.sendPacketToPlayer(new Packet42RemoveEntityEffect(this.entityId, par1PotionEffect));
   }

   public void setPositionAndUpdate(double par1, double par3, double par5) {
      this.playerNetServerHandler.setPlayerLocation(par1, par3, par5, this.rotationYaw, this.rotationPitch);
   }

   public void onCriticalHit(Entity par1Entity) {
      this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 6));
   }

   public void onEnchantmentCritical(Entity par1Entity) {
      this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 7));
   }

   public void sendPlayerAbilities() {
      if(this.playerNetServerHandler != null) {
         this.playerNetServerHandler.sendPacketToPlayer(new Packet202PlayerAbilities(this.capabilities));
      }
   }

   public WorldServer getServerForPlayer() {
      return (WorldServer)this.worldObj;
   }

   public void setGameType(EnumGameType par1EnumGameType) {
      this.theItemInWorldManager.setGameType(par1EnumGameType);
      this.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(3, par1EnumGameType.getID()));
   }

   public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent) {
      this.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(par1ChatMessageComponent));
   }

   public boolean canCommandSenderUseCommand(int par1, String par2Str) {
      return "seed".equals(par2Str) && !this.mcServer.isDedicatedServer()?true:(!"tell".equals(par2Str) && !"help".equals(par2Str) && !"me".equals(par2Str)?(this.mcServer.getConfigurationManager().isPlayerOpped(this.username)?this.mcServer.func_110455_j() >= par1:false):true);
   }

   public String getPlayerIP() {
      String var1 = this.playerNetServerHandler.netManager.getSocketAddress().toString();
      var1 = var1.substring(var1.indexOf("/") + 1);
      var1 = var1.substring(0, var1.indexOf(":"));
      return var1;
   }

   public void updateClientInfo(Packet204ClientInfo par1Packet204ClientInfo) {
      this.translator = par1Packet204ClientInfo.getLanguage();
      int var2 = 256 >> par1Packet204ClientInfo.getRenderDistance();
      if(var2 > 3 && var2 < 15) {
         this.renderDistance = var2;
      }

      this.chatVisibility = par1Packet204ClientInfo.getChatVisibility();
      this.chatColours = par1Packet204ClientInfo.getChatColours();
      if(this.mcServer.isSinglePlayer() && this.mcServer.getServerOwner().equals(this.username)) {
         this.mcServer.setDifficultyForAllWorlds(par1Packet204ClientInfo.getDifficulty());
      }

      this.setHideCape(1, !par1Packet204ClientInfo.getShowCape());
   }

   public int getChatVisibility() {
      return this.chatVisibility;
   }

   public void requestTexturePackLoad(String par1Str, int par2) {
      String var3 = par1Str + "\u0000" + par2;
      this.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TPack", var3.getBytes()));
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5D), MathHelper.floor_double(this.posZ));
   }

   public void func_143004_u() {
      this.field_143005_bX = MinecraftServer.getSystemTimeMillis();
   }
}
