package net.minecraft.src;

import java.util.Iterator;

public class EntityItem extends Entity {
   public int age;
   public int delayBeforeCanPickup;
   private int health;
   public float hoverStart;

   public EntityItem(World par1World, double par2, double par4, double par6) {
      super(par1World);
      this.health = 5;
      this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
      this.setSize(0.25F, 0.25F);
      this.yOffset = this.height / 2.0F;
      this.setPosition(par2, par4, par6);
      this.rotationYaw = (float)(Math.random() * 360.0D);
      this.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
      this.motionY = 0.20000000298023224D;
      this.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
   }

   public EntityItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
      this(par1World, par2, par4, par6);
      this.setEntityItemStack(par8ItemStack);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public EntityItem(World par1World) {
      super(par1World);
      this.health = 5;
      this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
      this.setSize(0.25F, 0.25F);
      this.yOffset = this.height / 2.0F;
   }

   protected void entityInit() {
      this.getDataWatcher().addObjectByDataType(10, 5);
   }

   public void onUpdate() {
      super.onUpdate();
      if(this.delayBeforeCanPickup > 0) {
         --this.delayBeforeCanPickup;
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY -= 0.03999999910593033D;
      this.noClip = this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      boolean var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;
      if(var1 || this.ticksExisted % 25 == 0) {
         if(this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
            this.motionY = 0.20000000298023224D;
            this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
         }

         if(!this.worldObj.isRemote) {
            this.searchForOtherItemsNearby();
         }
      }

      float var2 = 0.98F;
      if(this.onGround) {
         var2 = 0.58800006F;
         int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
         if(var3 > 0) {
            var2 = Block.blocksList[var3].slipperiness * 0.98F;
         }
      }

      this.motionX *= (double)var2;
      this.motionY *= 0.9800000190734863D;
      this.motionZ *= (double)var2;
      if(this.onGround) {
         this.motionY *= -0.5D;
      }

      ++this.age;
      if(!this.worldObj.isRemote && this.age >= 6000) {
         this.setDead();
      }
   }

   private void searchForOtherItemsNearby() {
      Iterator var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();

      while(var1.hasNext()) {
         EntityItem var2 = (EntityItem)var1.next();
         this.combineItems(var2);
      }
   }

   public boolean combineItems(EntityItem par1EntityItem) {
      if(par1EntityItem == this) {
         return false;
      } else if(par1EntityItem.isEntityAlive() && this.isEntityAlive()) {
         ItemStack var2 = this.getEntityItem();
         ItemStack var3 = par1EntityItem.getEntityItem();
         if(var3.getItem() != var2.getItem()) {
            return false;
         } else if(var3.hasTagCompound() ^ var2.hasTagCompound()) {
            return false;
         } else if(var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) {
            return false;
         } else if(var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage()) {
            return false;
         } else if(var3.stackSize < var2.stackSize) {
            return par1EntityItem.combineItems(this);
         } else if(var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
            return false;
         } else {
            var3.stackSize += var2.stackSize;
            par1EntityItem.delayBeforeCanPickup = Math.max(par1EntityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
            par1EntityItem.age = Math.min(par1EntityItem.age, this.age);
            par1EntityItem.setEntityItemStack(var3);
            this.setDead();
            return true;
         }
      } else {
         return false;
      }
   }

   public void setAgeToCreativeDespawnTime() {
      this.age = 4800;
   }

   public boolean handleWaterMovement() {
      return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
   }

   protected void dealFireDamage(int par1) {
      this.attackEntityFrom(DamageSource.inFire, (float)par1);
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.isEntityInvulnerable()) {
         return false;
      } else if(this.getEntityItem() != null && this.getEntityItem().itemID == Item.netherStar.itemID && par1DamageSource.isExplosion()) {
         return false;
      } else {
         this.setBeenAttacked();
         this.health = (int)((float)this.health - par2);
         if(this.health <= 0) {
            this.setDead();
         }

         return false;
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("Health", (short)((byte)this.health));
      par1NBTTagCompound.setShort("Age", (short)this.age);
      if(this.getEntityItem() != null) {
         par1NBTTagCompound.setCompoundTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
      }
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.health = par1NBTTagCompound.getShort("Health") & 255;
      this.age = par1NBTTagCompound.getShort("Age");
      NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
      this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
      if(this.getEntityItem() == null) {
         this.setDead();
      }
   }

   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
      if(!this.worldObj.isRemote) {
         ItemStack var2 = this.getEntityItem();
         int var3 = var2.stackSize;
         if(this.delayBeforeCanPickup == 0 && par1EntityPlayer.inventory.addItemStackToInventory(var2)) {
            if(var2.itemID == Block.wood.blockID) {
               par1EntityPlayer.triggerAchievement(AchievementList.mineWood);
            }

            if(var2.itemID == Item.leather.itemID) {
               par1EntityPlayer.triggerAchievement(AchievementList.killCow);
            }

            if(var2.itemID == Item.diamond.itemID) {
               par1EntityPlayer.triggerAchievement(AchievementList.diamonds);
            }

            if(var2.itemID == Item.blazeRod.itemID) {
               par1EntityPlayer.triggerAchievement(AchievementList.blazeRod);
            }

            this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            par1EntityPlayer.onItemPickup(this, var3);
            if(var2.stackSize <= 0) {
               this.setDead();
            }
         }
      }
   }

   public String getEntityName() {
      return StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
   }

   public boolean canAttackWithItem() {
      return false;
   }

   public void travelToDimension(int par1) {
      super.travelToDimension(par1);
      if(!this.worldObj.isRemote) {
         this.searchForOtherItemsNearby();
      }
   }

   public ItemStack getEntityItem() {
      ItemStack var1 = this.getDataWatcher().getWatchableObjectItemStack(10);
      if(var1 == null) {
         if(this.worldObj != null) {
            this.worldObj.getWorldLogAgent().logSevere("Item entity " + this.entityId + " has no item?!");
         }

         return new ItemStack(Block.stone);
      } else {
         return var1;
      }
   }

   public void setEntityItemStack(ItemStack par1ItemStack) {
      this.getDataWatcher().updateObject(10, par1ItemStack);
      this.getDataWatcher().setObjectWatched(10);
   }
}
