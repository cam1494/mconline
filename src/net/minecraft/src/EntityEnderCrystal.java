package net.minecraft.src;

public class EntityEnderCrystal extends Entity {
   public int innerRotation;
   public int health;

   public EntityEnderCrystal(World par1World) {
      super(par1World);
      this.preventEntitySpawning = true;
      this.setSize(2.0F, 2.0F);
      this.yOffset = this.height / 2.0F;
      this.health = 5;
      this.innerRotation = this.rand.nextInt(100000);
   }

   public EntityEnderCrystal(World par1World, double par2, double par4, double par6) {
      this(par1World);
      this.setPosition(par2, par4, par6);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      this.dataWatcher.addObject(8, Integer.valueOf(this.health));
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      ++this.innerRotation;
      this.dataWatcher.updateObject(8, Integer.valueOf(this.health));
      int var1 = MathHelper.floor_double(this.posX);
      int var2 = MathHelper.floor_double(this.posY);
      int var3 = MathHelper.floor_double(this.posZ);
      if(this.worldObj.getBlockId(var1, var2, var3) != Block.fire.blockID) {
         this.worldObj.setBlock(var1, var2, var3, Block.fire.blockID);
      }
   }

   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

   public float getShadowSize() {
      return 0.0F;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.isEntityInvulnerable()) {
         return false;
      } else {
         if(!this.isDead && !this.worldObj.isRemote) {
            this.health = 0;
            if(this.health <= 0) {
               this.setDead();
               if(!this.worldObj.isRemote) {
                  this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 6.0F, true);
               }
            }
         }

         return true;
      }
   }
}
