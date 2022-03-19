package net.minecraft.src;

public class EntityEnderEye extends Entity {
   private double targetX;
   private double targetY;
   private double targetZ;
   private int despawnTimer;
   private boolean shatterOrDrop;

   public EntityEnderEye(World par1World) {
      super(par1World);
      this.setSize(0.25F, 0.25F);
   }

   protected void entityInit() {}

   public boolean isInRangeToRenderDist(double par1) {
      double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
      var3 *= 64.0D;
      return par1 < var3 * var3;
   }

   public EntityEnderEye(World par1World, double par2, double par4, double par6) {
      super(par1World);
      this.despawnTimer = 0;
      this.setSize(0.25F, 0.25F);
      this.setPosition(par2, par4, par6);
      this.yOffset = 0.0F;
   }

   public void moveTowards(double par1, int par3, double par4) {
      double var6 = par1 - this.posX;
      double var8 = par4 - this.posZ;
      float var10 = MathHelper.sqrt_double(var6 * var6 + var8 * var8);
      if(var10 > 12.0F) {
         this.targetX = this.posX + var6 / (double)var10 * 12.0D;
         this.targetZ = this.posZ + var8 / (double)var10 * 12.0D;
         this.targetY = this.posY + 8.0D;
      } else {
         this.targetX = par1;
         this.targetY = (double)par3;
         this.targetZ = par4;
      }

      this.despawnTimer = 0;
      this.shatterOrDrop = this.rand.nextInt(5) > 0;
   }

   public void setVelocity(double par1, double par3, double par5) {
      this.motionX = par1;
      this.motionY = par3;
      this.motionZ = par5;
      if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
         this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
         this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var7) * 180.0D / Math.PI);
      }
   }

   public void onUpdate() {
      this.lastTickPosX = this.posX;
      this.lastTickPosY = this.posY;
      this.lastTickPosZ = this.posZ;
      super.onUpdate();
      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

      for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
         ;
      }

      while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
      this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
      if(!this.worldObj.isRemote) {
         double var2 = this.targetX - this.posX;
         double var4 = this.targetZ - this.posZ;
         float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
         float var7 = (float)Math.atan2(var4, var2);
         double var8 = (double)var1 + (double)(var6 - var1) * 0.0025D;
         if(var6 < 1.0F) {
            var8 *= 0.8D;
            this.motionY *= 0.8D;
         }

         this.motionX = Math.cos((double)var7) * var8;
         this.motionZ = Math.sin((double)var7) * var8;
         if(this.posY < this.targetY) {
            this.motionY += (1.0D - this.motionY) * 0.014999999664723873D;
         } else {
            this.motionY += (-1.0D - this.motionY) * 0.014999999664723873D;
         }
      }

      float var10 = 0.25F;
      if(this.isInWater()) {
         for(int var3 = 0; var3 < 4; ++var3) {
            this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var10, this.posY - this.motionY * (double)var10, this.posZ - this.motionZ * (double)var10, this.motionX, this.motionY, this.motionZ);
         }
      } else {
         this.worldObj.spawnParticle("portal", this.posX - this.motionX * (double)var10 + this.rand.nextDouble() * 0.6D - 0.3D, this.posY - this.motionY * (double)var10 - 0.5D, this.posZ - this.motionZ * (double)var10 + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX, this.motionY, this.motionZ);
      }

      if(!this.worldObj.isRemote) {
         this.setPosition(this.posX, this.posY, this.posZ);
         ++this.despawnTimer;
         if(this.despawnTimer > 80 && !this.worldObj.isRemote) {
            this.setDead();
            if(this.shatterOrDrop) {
               this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.eyeOfEnder)));
            } else {
               this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
            }
         }
      }
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

   public float getShadowSize() {
      return 0.0F;
   }

   public float getBrightness(float par1) {
      return 1.0F;
   }

   public int getBrightnessForRender(float par1) {
      return 15728880;
   }

   public boolean canAttackWithItem() {
      return false;
   }
}
