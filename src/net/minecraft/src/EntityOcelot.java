package net.minecraft.src;

public class EntityOcelot extends EntityTameable {
   private EntityAITempt aiTempt;

   public EntityOcelot(World par1World) {
      super(par1World);
      this.setSize(0.6F, 0.8F);
      this.getNavigator().setAvoidsWater(true);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, this.aiSit);
      this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6D, Item.fishRaw.itemID, true));
      this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
      this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
      this.tasks.addTask(6, new EntityAIOcelotSit(this, 1.33D));
      this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
      this.tasks.addTask(8, new EntityAIOcelotAttack(this));
      this.tasks.addTask(9, new EntityAIMate(this, 0.8D));
      this.tasks.addTask(10, new EntityAIWander(this, 0.8D));
      this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 750, false));
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
   }

   public void updateAITick() {
      if(this.getMoveHelper().isUpdating()) {
         double var1 = this.getMoveHelper().getSpeed();
         if(var1 == 0.6D) {
            this.setSneaking(true);
            this.setSprinting(false);
         } else if(var1 == 1.33D) {
            this.setSneaking(false);
            this.setSprinting(true);
         } else {
            this.setSneaking(false);
            this.setSprinting(false);
         }
      } else {
         this.setSneaking(false);
         this.setSprinting(false);
      }
   }

   protected boolean canDespawn() {
      return !this.isTamed() && this.ticksExisted > 2400;
   }

   public boolean isAIEnabled() {
      return true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.30000001192092896D);
   }

   protected void fall(float par1) {}

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("CatType", this.getTameSkin());
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.setTameSkin(par1NBTTagCompound.getInteger("CatType"));
   }

   protected String getLivingSound() {
      return this.isTamed()?(this.isInLove()?"mob.cat.purr":(this.rand.nextInt(4) == 0?"mob.cat.purreow":"mob.cat.meow")):"";
   }

   protected String getHurtSound() {
      return "mob.cat.hitt";
   }

   protected String getDeathSound() {
      return "mob.cat.hitt";
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected int getDropItemId() {
      return Item.leather.itemID;
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.isEntityInvulnerable()) {
         return false;
      } else {
         this.aiSit.setSitting(false);
         return super.attackEntityFrom(par1DamageSource, par2);
      }
   }

   protected void dropFewItems(boolean par1, int par2) {}

   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if(this.isTamed()) {
         if(par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(var2)) {
            this.aiSit.setSitting(!this.isSitting());
         }
      } else if(this.aiTempt.isRunning() && var2 != null && var2.itemID == Item.fishRaw.itemID && par1EntityPlayer.getDistanceSqToEntity(this) < 9.0D) {
         if(!par1EntityPlayer.capabilities.isCreativeMode) {
            --var2.stackSize;
         }

         if(var2.stackSize <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
         }

         if(!this.worldObj.isRemote) {
            if(this.rand.nextInt(3) == 0) {
               this.setTamed(true);
               this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
               this.setOwner(par1EntityPlayer.getCommandSenderName());
               this.playTameEffect(true);
               this.aiSit.setSitting(true);
               this.worldObj.setEntityState(this, (byte)7);
            } else {
               this.playTameEffect(false);
               this.worldObj.setEntityState(this, (byte)6);
            }
         }

         return true;
      }

      return super.interact(par1EntityPlayer);
   }

   public EntityOcelot spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      EntityOcelot var2 = new EntityOcelot(this.worldObj);
      if(this.isTamed()) {
         var2.setOwner(this.getOwnerName());
         var2.setTamed(true);
         var2.setTameSkin(this.getTameSkin());
      }

      return var2;
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.itemID == Item.fishRaw.itemID;
   }

   public boolean canMateWith(EntityAnimal par1EntityAnimal) {
      if(par1EntityAnimal == this) {
         return false;
      } else if(!this.isTamed()) {
         return false;
      } else if(!(par1EntityAnimal instanceof EntityOcelot)) {
         return false;
      } else {
         EntityOcelot var2 = (EntityOcelot)par1EntityAnimal;
         return !var2.isTamed()?false:this.isInLove() && var2.isInLove();
      }
   }

   public int getTameSkin() {
      return this.dataWatcher.getWatchableObjectByte(18);
   }

   public void setTameSkin(int par1) {
      this.dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
   }

   public boolean getCanSpawnHere() {
      if(this.worldObj.rand.nextInt(3) == 0) {
         return false;
      } else {
         if(this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.boundingBox.minY);
            int var3 = MathHelper.floor_double(this.posZ);
            if(var2 < 63) {
               return false;
            }

            int var4 = this.worldObj.getBlockId(var1, var2 - 1, var3);
            if(var4 == Block.grass.blockID || var4 == Block.leaves.blockID) {
               return true;
            }
         }

         return false;
      }
   }

   public String getEntityName() {
      return this.hasCustomNameTag()?this.getCustomNameTag():(this.isTamed()?"entity.Cat.name":super.getEntityName());
   }

   public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData) {
      par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
      if(this.worldObj.rand.nextInt(7) == 0) {
         for(int var2 = 0; var2 < 2; ++var2) {
            EntityOcelot var3 = new EntityOcelot(this.worldObj);
            var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            var3.setGrowingAge(-24000);
            this.worldObj.spawnEntityInWorld(var3);
         }
      }

      return par1EntityLivingData;
   }

   public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
      return this.spawnBabyAnimal(par1EntityAgeable);
   }
}
