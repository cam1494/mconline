package net.minecraft.src;

public abstract class EntityAITarget extends EntityAIBase {
   protected EntityCreature taskOwner;
   protected boolean shouldCheckSight;
   private boolean nearbyOnly;
   private int targetSearchStatus;
   private int targetSearchDelay;
   private int field_75298_g;

   public EntityAITarget(EntityCreature par1EntityCreature, boolean par2) {
      this(par1EntityCreature, par2, false);
   }

   public EntityAITarget(EntityCreature par1EntityCreature, boolean par2, boolean par3) {
      this.taskOwner = par1EntityCreature;
      this.shouldCheckSight = par2;
      this.nearbyOnly = par3;
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.taskOwner.getAttackTarget();
      if(var1 == null) {
         return false;
      } else if(!var1.isEntityAlive()) {
         return false;
      } else {
         double var2 = this.getTargetDistance();
         if(this.taskOwner.getDistanceSqToEntity(var1) > var2 * var2) {
            return false;
         } else {
            if(this.shouldCheckSight) {
               if(this.taskOwner.getEntitySenses().canSee(var1)) {
                  this.field_75298_g = 0;
               } else if(++this.field_75298_g > 60) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   protected double getTargetDistance() {
      AttributeInstance var1 = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
      return var1 == null?16.0D:var1.getAttributeValue();
   }

   public void startExecuting() {
      this.targetSearchStatus = 0;
      this.targetSearchDelay = 0;
      this.field_75298_g = 0;
   }

   public void resetTask() {
      this.taskOwner.setAttackTarget((EntityLivingBase)null);
   }

   protected boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2) {
      if(par1EntityLivingBase == null) {
         return false;
      } else if(par1EntityLivingBase == this.taskOwner) {
         return false;
      } else if(!par1EntityLivingBase.isEntityAlive()) {
         return false;
      } else if(!this.taskOwner.canAttackClass(par1EntityLivingBase.getClass())) {
         return false;
      } else {
         if(this.taskOwner instanceof EntityOwnable && org.apache.commons.lang3.StringUtils.isNotEmpty(((EntityOwnable)this.taskOwner).getOwnerName())) {
            if(par1EntityLivingBase instanceof EntityOwnable && ((EntityOwnable)this.taskOwner).getOwnerName().equals(((EntityOwnable)par1EntityLivingBase).getOwnerName())) {
               return false;
            }

            if(par1EntityLivingBase == ((EntityOwnable)this.taskOwner).getOwner()) {
               return false;
            }
         } else if(par1EntityLivingBase instanceof EntityPlayer && !par2 && ((EntityPlayer)par1EntityLivingBase).capabilities.disableDamage) {
            return false;
         }

         if(!this.taskOwner.func_110176_b(MathHelper.floor_double(par1EntityLivingBase.posX), MathHelper.floor_double(par1EntityLivingBase.posY), MathHelper.floor_double(par1EntityLivingBase.posZ))) {
            return false;
         } else if(this.shouldCheckSight && !this.taskOwner.getEntitySenses().canSee(par1EntityLivingBase)) {
            return false;
         } else {
            if(this.nearbyOnly) {
               if(--this.targetSearchDelay <= 0) {
                  this.targetSearchStatus = 0;
               }

               if(this.targetSearchStatus == 0) {
                  this.targetSearchStatus = this.canEasilyReach(par1EntityLivingBase)?1:2;
               }

               if(this.targetSearchStatus == 2) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean canEasilyReach(EntityLivingBase par1EntityLivingBase) {
      this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
      PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(par1EntityLivingBase);
      if(var2 == null) {
         return false;
      } else {
         PathPoint var3 = var2.getFinalPathPoint();
         if(var3 == null) {
            return false;
         } else {
            int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLivingBase.posX);
            int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLivingBase.posZ);
            return (double)(var4 * var4 + var5 * var5) <= 2.25D;
         }
      }
   }
}
