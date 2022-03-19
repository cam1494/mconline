package net.minecraft.src;

public class EntityPlayerSP extends AbstractClientPlayer {
   public MovementInput movementInput;
   protected Minecraft mc;
   protected int sprintToggleTimer;
   public int sprintingTicksLeft;
   public float renderArmYaw;
   public float renderArmPitch;
   public float prevRenderArmYaw;
   public float prevRenderArmPitch;
   private int horseJumpPowerCounter;
   private float horseJumpPower;
   private MouseFilter field_71162_ch = new MouseFilter();
   private MouseFilter field_71160_ci = new MouseFilter();
   private MouseFilter field_71161_cj = new MouseFilter();
   public float timeInPortal;
   public float prevTimeInPortal;

   public EntityPlayerSP(Minecraft par1Minecraft, World par2World, Session par3Session, int par4) {
      super(par2World, par3Session.getUsername());
      this.mc = par1Minecraft;
      this.dimension = par4;
   }

   public void updateEntityActionState() {
      super.updateEntityActionState();
      this.moveStrafing = this.movementInput.moveStrafe;
      this.moveForward = this.movementInput.moveForward;
      this.isJumping = this.movementInput.jump;
      this.prevRenderArmYaw = this.renderArmYaw;
      this.prevRenderArmPitch = this.renderArmPitch;
      this.renderArmPitch = (float)((double)this.renderArmPitch + (double)(this.rotationPitch - this.renderArmPitch) * 0.5D);
      this.renderArmYaw = (float)((double)this.renderArmYaw + (double)(this.rotationYaw - this.renderArmYaw) * 0.5D);
   }

   public void onLivingUpdate() {
      if(this.sprintingTicksLeft > 0) {
         --this.sprintingTicksLeft;
         if(this.sprintingTicksLeft == 0) {
            this.setSprinting(false);
         }
      }

      if(this.sprintToggleTimer > 0) {
         --this.sprintToggleTimer;
      }

      if(this.mc.playerController.enableEverythingIsScrewedUpMode()) {
         this.posX = this.posZ = 0.5D;
         this.posX = 0.0D;
         this.posZ = 0.0D;
         this.rotationYaw = (float)this.ticksExisted / 12.0F;
         this.rotationPitch = 10.0F;
         this.posY = 68.5D;
      } else {
         if(!this.mc.statFileWriter.hasAchievementUnlocked(AchievementList.openInventory)) {
            this.mc.guiAchievement.queueAchievementInformation(AchievementList.openInventory);
         }

         this.prevTimeInPortal = this.timeInPortal;
         if(this.inPortal) {
            if(this.mc.currentScreen != null) {
               this.mc.displayGuiScreen((GuiScreen)null);
            }

            if(this.timeInPortal == 0.0F) {
               this.mc.sndManager.playSoundFX("portal.trigger", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            }

            this.timeInPortal += 0.0125F;
            if(this.timeInPortal >= 1.0F) {
               this.timeInPortal = 1.0F;
            }

            this.inPortal = false;
         } else if(this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
            this.timeInPortal += 0.006666667F;
            if(this.timeInPortal > 1.0F) {
               this.timeInPortal = 1.0F;
            }
         } else {
            if(this.timeInPortal > 0.0F) {
               this.timeInPortal -= 0.05F;
            }

            if(this.timeInPortal < 0.0F) {
               this.timeInPortal = 0.0F;
            }
         }

         if(this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
         }

         boolean var1 = this.movementInput.jump;
         float var2 = 0.8F;
         boolean var3 = this.movementInput.moveForward >= var2;
         this.movementInput.updatePlayerMoveState();
         if(this.isUsingItem() && !this.isRiding()) {
            this.movementInput.moveStrafe *= 0.2F;
            this.movementInput.moveForward *= 0.2F;
            this.sprintToggleTimer = 0;
         }

         if(this.movementInput.sneak && this.ySize < 0.2F) {
            this.ySize = 0.2F;
         }

         this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ + (double)this.width * 0.35D);
         this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ - (double)this.width * 0.35D);
         this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ - (double)this.width * 0.35D);
         this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ + (double)this.width * 0.35D);
         boolean var4 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
         if(this.onGround && !var3 && this.movementInput.moveForward >= var2 && !this.isSprinting() && var4 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
            if(this.sprintToggleTimer == 0) {
               this.sprintToggleTimer = 7;
            } else {
               this.setSprinting(true);
               this.sprintToggleTimer = 0;
            }
         }

         if(this.isSneaking()) {
            this.sprintToggleTimer = 0;
         }

         if(this.isSprinting() && (this.movementInput.moveForward < var2 || this.isCollidedHorizontally || !var4)) {
            this.setSprinting(false);
         }

         if(this.capabilities.allowFlying && !var1 && this.movementInput.jump) {
            if(this.flyToggleTimer == 0) {
               this.flyToggleTimer = 7;
            } else {
               this.capabilities.isFlying = !this.capabilities.isFlying;
               this.sendPlayerAbilities();
               this.flyToggleTimer = 0;
            }
         }

         if(this.capabilities.isFlying) {
            if(this.movementInput.sneak) {
               this.motionY -= 0.15D;
            }

            if(this.movementInput.jump) {
               this.motionY += 0.15D;
            }
         }

         if(this.isRidingHorse()) {
            if(this.horseJumpPowerCounter < 0) {
               ++this.horseJumpPowerCounter;
               if(this.horseJumpPowerCounter == 0) {
                  this.horseJumpPower = 0.0F;
               }
            }

            if(var1 && !this.movementInput.jump) {
               this.horseJumpPowerCounter = -10;
               this.func_110318_g();
            } else if(!var1 && this.movementInput.jump) {
               this.horseJumpPowerCounter = 0;
               this.horseJumpPower = 0.0F;
            } else if(var1) {
               ++this.horseJumpPowerCounter;
               if(this.horseJumpPowerCounter < 10) {
                  this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
               } else {
                  this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
               }
            }
         } else {
            this.horseJumpPower = 0.0F;
         }

         super.onLivingUpdate();
         if(this.onGround && this.capabilities.isFlying) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
         }
      }
   }

   public float getFOVMultiplier() {
      float var1 = 1.0F;
      if(this.capabilities.isFlying) {
         var1 *= 1.1F;
      }

      AttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      var1 = (float)((double)var1 * ((var2.getAttributeValue() / (double)this.capabilities.getWalkSpeed() + 1.0D) / 2.0D));
      if(this.isUsingItem() && this.getItemInUse().itemID == Item.bow.itemID) {
         int var3 = this.getItemInUseDuration();
         float var4 = (float)var3 / 20.0F;
         if(var4 > 1.0F) {
            var4 = 1.0F;
         } else {
            var4 *= var4;
         }

         var1 *= 1.0F - var4 * 0.15F;
      }

      return var1;
   }

   public void closeScreen() {
      super.closeScreen();
      this.mc.displayGuiScreen((GuiScreen)null);
   }

   public void displayGUIEditSign(TileEntity par1TileEntity) {
      if(par1TileEntity instanceof TileEntitySign) {
         this.mc.displayGuiScreen(new GuiEditSign((TileEntitySign)par1TileEntity));
      } else if(par1TileEntity instanceof TileEntityCommandBlock) {
         this.mc.displayGuiScreen(new GuiCommandBlock((TileEntityCommandBlock)par1TileEntity));
      }
   }

   public void displayGUIBook(ItemStack par1ItemStack) {
      Item var2 = par1ItemStack.getItem();
      if(var2 == Item.writtenBook) {
         this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, false));
      } else if(var2 == Item.writableBook) {
         this.mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, true));
      }
   }

   public void displayGUIChest(IInventory par1IInventory) {
      this.mc.displayGuiScreen(new GuiChest(this.inventory, par1IInventory));
   }

   public void displayGUIHopper(TileEntityHopper par1TileEntityHopper) {
      this.mc.displayGuiScreen(new GuiHopper(this.inventory, par1TileEntityHopper));
   }

   public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper) {
      this.mc.displayGuiScreen(new GuiHopper(this.inventory, par1EntityMinecartHopper));
   }

   public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory) {
      this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, par2IInventory, par1EntityHorse));
   }

   public void displayGUIWorkbench(int par1, int par2, int par3) {
      this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, par1, par2, par3));
   }

   public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str) {
      this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, par1, par2, par3, par4Str));
   }

   public void displayGUIAnvil(int par1, int par2, int par3) {
      this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj, par1, par2, par3));
   }

   public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace) {
      this.mc.displayGuiScreen(new GuiFurnace(this.inventory, par1TileEntityFurnace));
   }

   public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand) {
      this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, par1TileEntityBrewingStand));
   }

   public void displayGUIBeacon(TileEntityBeacon par1TileEntityBeacon) {
      this.mc.displayGuiScreen(new GuiBeacon(this.inventory, par1TileEntityBeacon));
   }

   public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser) {
      this.mc.displayGuiScreen(new GuiDispenser(this.inventory, par1TileEntityDispenser));
   }

   public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str) {
      this.mc.displayGuiScreen(new GuiMerchant(this.inventory, par1IMerchant, this.worldObj, par2Str));
   }

   public void onCriticalHit(Entity par1Entity) {
      this.mc.effectRenderer.addEffect(new EntityCrit2FX(this.mc.theWorld, par1Entity));
   }

   public void onEnchantmentCritical(Entity par1Entity) {
      EntityCrit2FX var2 = new EntityCrit2FX(this.mc.theWorld, par1Entity, "magicCrit");
      this.mc.effectRenderer.addEffect(var2);
   }

   public void onItemPickup(Entity par1Entity, int par2) {
      this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, par1Entity, this, -0.5F));
   }

   public boolean isSneaking() {
      return this.movementInput.sneak && !this.sleeping;
   }

   public void setPlayerSPHealth(float par1) {
      float var2 = this.getHealth() - par1;
      if(var2 <= 0.0F) {
         this.setHealth(par1);
         if(var2 < 0.0F) {
            this.hurtResistantTime = this.maxHurtResistantTime / 2;
         }
      } else {
         this.lastDamage = var2;
         this.setHealth(this.getHealth());
         this.hurtResistantTime = this.maxHurtResistantTime;
         this.damageEntity(DamageSource.generic, var2);
         this.hurtTime = this.maxHurtTime = 10;
      }
   }

   public void addChatMessage(String par1Str) {
      this.mc.ingameGUI.getChatGUI().addTranslatedMessage(par1Str, new Object[0]);
   }

   public void addStat(StatBase par1StatBase, int par2) {
      if(par1StatBase != null) {
         if(par1StatBase.isAchievement()) {
            Achievement var3 = (Achievement)par1StatBase;
            if(var3.parentAchievement == null || this.mc.statFileWriter.hasAchievementUnlocked(var3.parentAchievement)) {
               if(!this.mc.statFileWriter.hasAchievementUnlocked(var3)) {
                  this.mc.guiAchievement.queueTakenAchievement(var3);
               }

               this.mc.statFileWriter.readStat(par1StatBase, par2);
            }
         } else {
            this.mc.statFileWriter.readStat(par1StatBase, par2);
         }
      }
   }

   private boolean isBlockTranslucent(int par1, int par2, int par3) {
      return this.worldObj.isBlockNormalCube(par1, par2, par3);
   }

   protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
      int var7 = MathHelper.floor_double(par1);
      int var8 = MathHelper.floor_double(par3);
      int var9 = MathHelper.floor_double(par5);
      double var10 = par1 - (double)var7;
      double var12 = par5 - (double)var9;
      if(this.isBlockTranslucent(var7, var8, var9) || this.isBlockTranslucent(var7, var8 + 1, var9)) {
         boolean var14 = !this.isBlockTranslucent(var7 - 1, var8, var9) && !this.isBlockTranslucent(var7 - 1, var8 + 1, var9);
         boolean var15 = !this.isBlockTranslucent(var7 + 1, var8, var9) && !this.isBlockTranslucent(var7 + 1, var8 + 1, var9);
         boolean var16 = !this.isBlockTranslucent(var7, var8, var9 - 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 - 1);
         boolean var17 = !this.isBlockTranslucent(var7, var8, var9 + 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 + 1);
         byte var18 = -1;
         double var19 = 9999.0D;
         if(var14 && var10 < var19) {
            var19 = var10;
            var18 = 0;
         }

         if(var15 && 1.0D - var10 < var19) {
            var19 = 1.0D - var10;
            var18 = 1;
         }

         if(var16 && var12 < var19) {
            var19 = var12;
            var18 = 4;
         }

         if(var17 && 1.0D - var12 < var19) {
            var19 = 1.0D - var12;
            var18 = 5;
         }

         float var21 = 0.1F;
         if(var18 == 0) {
            this.motionX = (double)(-var21);
         }

         if(var18 == 1) {
            this.motionX = (double)var21;
         }

         if(var18 == 4) {
            this.motionZ = (double)(-var21);
         }

         if(var18 == 5) {
            this.motionZ = (double)var21;
         }
      }

      return false;
   }

   public void setSprinting(boolean par1) {
      super.setSprinting(par1);
      this.sprintingTicksLeft = par1?600:0;
   }

   public void setXPStats(float par1, int par2, int par3) {
      this.experience = par1;
      this.experienceTotal = par2;
      this.experienceLevel = par3;
   }

   public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent) {
      this.mc.ingameGUI.getChatGUI().printChatMessage(par1ChatMessageComponent.toStringWithFormatting(true));
   }

   public boolean canCommandSenderUseCommand(int par1, String par2Str) {
      return par1 <= 0;
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5D), MathHelper.floor_double(this.posY + 0.5D), MathHelper.floor_double(this.posZ + 0.5D));
   }

   public ItemStack getHeldItem() {
      return this.inventory.getCurrentItem();
   }

   public void playSound(String par1Str, float par2, float par3) {
      this.worldObj.playSound(this.posX, this.posY - (double)this.yOffset, this.posZ, par1Str, par2, par3, false);
   }

   public boolean isClientWorld() {
      return true;
   }

   public boolean isRidingHorse() {
      return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse;
   }

   public float getHorseJumpPower() {
      return this.horseJumpPower;
   }

   protected void func_110318_g() {}
}
