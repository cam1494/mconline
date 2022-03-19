package net.minecraft.src;

public class EntityCreeper extends EntityMob {
   private int lastActiveTime;
   private int timeSinceIgnited;
   private int fuseTime = 30;
   private int explosionRadius = 3;

   public EntityCreeper(World par1World) {
      super(par1World);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAICreeperSwell(this));
      this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
      this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
      this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(6, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
   }

   public boolean isAIEnabled() {
      return true;
   }

   public int getMaxSafePointTries() {
      return this.getAttackTarget() == null?3:3 + (int)(this.getHealth() - 1.0F);
   }

   protected void fall(float par1) {
      super.fall(par1);
      this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5F);
      if(this.timeSinceIgnited > this.fuseTime - 5) {
         this.timeSinceIgnited = this.fuseTime - 5;
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
      this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      if(this.dataWatcher.getWatchableObjectByte(17) == 1) {
         par1NBTTagCompound.setBoolean("powered", true);
      }

      par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
      par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered")?1:0)));
      if(par1NBTTagCompound.hasKey("Fuse")) {
         this.fuseTime = par1NBTTagCompound.getShort("Fuse");
      }

      if(par1NBTTagCompound.hasKey("ExplosionRadius")) {
         this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
      }
   }

   public void onUpdate() {
      if(this.isEntityAlive()) {
         this.lastActiveTime = this.timeSinceIgnited;
         int var1 = this.getCreeperState();
         if(var1 > 0 && this.timeSinceIgnited == 0) {
            this.playSound("random.fuse", 1.0F, 0.5F);
         }

         this.timeSinceIgnited += var1;
         if(this.timeSinceIgnited < 0) {
            this.timeSinceIgnited = 0;
         }

         if(this.timeSinceIgnited >= this.fuseTime) {
            this.timeSinceIgnited = this.fuseTime;
            if(!this.worldObj.isRemote) {
               boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
               if(this.getPowered()) {
                  this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), var2);
               } else {
                  this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, var2);
               }

               this.setDead();
            }
         }
      }

      super.onUpdate();
   }

   protected String getHurtSound() {
      return "mob.creeper.say";
   }

   protected String getDeathSound() {
      return "mob.creeper.death";
   }

   public void onDeath(DamageSource par1DamageSource) {
      super.onDeath(par1DamageSource);
      if(par1DamageSource.getEntity() instanceof EntitySkeleton) {
         int var2 = Item.record13.itemID + this.rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1);
         this.dropItem(var2, 1);
      }
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return true;
   }

   public boolean getPowered() {
      return this.dataWatcher.getWatchableObjectByte(17) == 1;
   }

   public float getCreeperFlashIntensity(float par1) {
      return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / (float)(this.fuseTime - 2);
   }

   protected int getDropItemId() {
      return Item.gunpowder.itemID;
   }

   public int getCreeperState() {
      return this.dataWatcher.getWatchableObjectByte(16);
   }

   public void setCreeperState(int par1) {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
   }

   public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
      super.onStruckByLightning(par1EntityLightningBolt);
      this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
   }
}
