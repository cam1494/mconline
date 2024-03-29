package net.minecraft.src;

public class EntitySquid extends EntityWaterMob {
   public float squidPitch;
   public float prevSquidPitch;
   public float squidYaw;
   public float prevSquidYaw;
   public float squidRotation;
   public float prevSquidRotation;
   public float tentacleAngle;
   public float prevTentacleAngle;
   private float randomMotionSpeed;
   private float rotationVelocity;
   private float field_70871_bB;
   private float randomMotionVecX;
   private float randomMotionVecY;
   private float randomMotionVecZ;

   public EntitySquid(World par1World) {
      super(par1World);
      this.setSize(0.95F, 0.95F);
      this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10.0D);
   }

   protected String getLivingSound() {
      return null;
   }

   protected String getHurtSound() {
      return null;
   }

   protected String getDeathSound() {
      return null;
   }

   protected float getSoundVolume() {
      return 0.4F;
   }

   protected int getDropItemId() {
      return 0;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = this.rand.nextInt(3 + par2) + 1;

      for(int var4 = 0; var4 < var3; ++var4) {
         this.entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
      }
   }

   public boolean isInWater() {
      return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.prevSquidPitch = this.squidPitch;
      this.prevSquidYaw = this.squidYaw;
      this.prevSquidRotation = this.squidRotation;
      this.prevTentacleAngle = this.tentacleAngle;
      this.squidRotation += this.rotationVelocity;
      if(this.squidRotation > ((float)Math.PI * 2F)) {
         this.squidRotation -= ((float)Math.PI * 2F);
         if(this.rand.nextInt(10) == 0) {
            this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
         }
      }

      if(this.isInWater()) {
         float var1;
         if(this.squidRotation < (float)Math.PI) {
            var1 = this.squidRotation / (float)Math.PI;
            this.tentacleAngle = MathHelper.sin(var1 * var1 * (float)Math.PI) * (float)Math.PI * 0.25F;
            if((double)var1 > 0.75D) {
               this.randomMotionSpeed = 1.0F;
               this.field_70871_bB = 1.0F;
            } else {
               this.field_70871_bB *= 0.8F;
            }
         } else {
            this.tentacleAngle = 0.0F;
            this.randomMotionSpeed *= 0.9F;
            this.field_70871_bB *= 0.99F;
         }

         if(!this.worldObj.isRemote) {
            this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
            this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
            this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
         }

         var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI - this.renderYawOffset) * 0.1F;
         this.rotationYaw = this.renderYawOffset;
         this.squidYaw += (float)Math.PI * this.field_70871_bB * 1.5F;
         this.squidPitch += (-((float)Math.atan2((double)var1, this.motionY)) * 180.0F / (float)Math.PI - this.squidPitch) * 0.1F;
      } else {
         this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;
         if(!this.worldObj.isRemote) {
            this.motionX = 0.0D;
            this.motionY -= 0.08D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ = 0.0D;
         }

         this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
      }
   }

   public void moveEntityWithHeading(float par1, float par2) {
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
   }

   protected void updateEntityActionState() {
      ++this.entityAge;
      if(this.entityAge > 100) {
         this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0.0F;
      } else if(this.rand.nextInt(50) == 0 || !this.inWater || this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F) {
         float var1 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
         this.randomMotionVecX = MathHelper.cos(var1) * 0.2F;
         this.randomMotionVecY = -0.1F + this.rand.nextFloat() * 0.2F;
         this.randomMotionVecZ = MathHelper.sin(var1) * 0.2F;
      }

      this.despawnEntity();
   }

   public boolean getCanSpawnHere() {
      return this.posY > 45.0D && this.posY < 63.0D && super.getCanSpawnHere();
   }
}
