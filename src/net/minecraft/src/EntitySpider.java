package net.minecraft.src;

public class EntitySpider extends EntityMob {
   public EntitySpider(World par1World) {
      super(par1World);
      this.setSize(1.4F, 0.9F);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, new Byte((byte)0));
   }

   public void onUpdate() {
      super.onUpdate();
      if(!this.worldObj.isRemote) {
         this.setBesideClimbableBlock(this.isCollidedHorizontally);
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(16.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.800000011920929D);
   }

   protected Entity findPlayerToAttack() {
      float var1 = this.getBrightness(1.0F);
      if(var1 < 0.5F) {
         double var2 = 16.0D;
         return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
      } else {
         return null;
      }
   }

   protected String getLivingSound() {
      return "mob.spider.say";
   }

   protected String getHurtSound() {
      return "mob.spider.say";
   }

   protected String getDeathSound() {
      return "mob.spider.death";
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
      this.playSound("mob.spider.step", 0.15F, 1.0F);
   }

   protected void attackEntity(Entity par1Entity, float par2) {
      float var3 = this.getBrightness(1.0F);
      if(var3 > 0.5F && this.rand.nextInt(100) == 0) {
         this.entityToAttack = null;
      } else {
         if(par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0) {
            if(this.onGround) {
               double var4 = par1Entity.posX - this.posX;
               double var6 = par1Entity.posZ - this.posZ;
               float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
               this.motionX = var4 / (double)var8 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
               this.motionZ = var6 / (double)var8 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
               this.motionY = 0.4000000059604645D;
            }
         } else {
            super.attackEntity(par1Entity, par2);
         }
      }
   }

   protected int getDropItemId() {
      return Item.silk.itemID;
   }

   protected void dropFewItems(boolean par1, int par2) {
      super.dropFewItems(par1, par2);
      if(par1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + par2) > 0)) {
         this.dropItem(Item.spiderEye.itemID, 1);
      }
   }

   public boolean isOnLadder() {
      return this.isBesideClimbableBlock();
   }

   public void setInWeb() {}

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.ARTHROPOD;
   }

   public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
      return par1PotionEffect.getPotionID() == Potion.poison.id?false:super.isPotionApplicable(par1PotionEffect);
   }

   public boolean isBesideClimbableBlock() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
   }

   public void setBesideClimbableBlock(boolean par1) {
      byte var2 = this.dataWatcher.getWatchableObjectByte(16);
      if(par1) {
         var2 = (byte)(var2 | 1);
      } else {
         var2 &= -2;
      }

      this.dataWatcher.updateObject(16, Byte.valueOf(var2));
   }

   public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData) {
      Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);
      if(this.worldObj.rand.nextInt(100) == 0) {
         EntitySkeleton var2 = new EntitySkeleton(this.worldObj);
         var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
         var2.onSpawnWithEgg((EntityLivingData)null);
         this.worldObj.spawnEntityInWorld(var2);
         var2.mountEntity(this);
      }

      if(par1EntityLivingData1 == null) {
         par1EntityLivingData1 = new SpiderEffectsGroupData();
         if(this.worldObj.difficultySetting > 2 && this.worldObj.rand.nextFloat() < 0.1F * this.worldObj.getLocationTensionFactor(this.posX, this.posY, this.posZ)) {
            ((SpiderEffectsGroupData)par1EntityLivingData1).func_111104_a(this.worldObj.rand);
         }
      }

      if(par1EntityLivingData1 instanceof SpiderEffectsGroupData) {
         int var4 = ((SpiderEffectsGroupData)par1EntityLivingData1).field_111105_a;
         if(var4 > 0 && Potion.potionTypes[var4] != null) {
            this.addPotionEffect(new PotionEffect(var4, Integer.MAX_VALUE));
         }
      }

      return (EntityLivingData)par1EntityLivingData1;
   }
}
