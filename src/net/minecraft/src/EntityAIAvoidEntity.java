package net.minecraft.src;

import java.util.List;

public class EntityAIAvoidEntity extends EntityAIBase {
   public final IEntitySelector field_98218_a = new EntityAIAvoidEntitySelector(this);
   private EntityCreature theEntity;
   private double farSpeed;
   private double nearSpeed;
   private Entity closestLivingEntity;
   private float distanceFromEntity;
   private PathEntity entityPathEntity;
   private PathNavigate entityPathNavigate;
   private Class targetEntityClass;

   public EntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, double par4, double par6) {
      this.theEntity = par1EntityCreature;
      this.targetEntityClass = par2Class;
      this.distanceFromEntity = par3;
      this.farSpeed = par4;
      this.nearSpeed = par6;
      this.entityPathNavigate = par1EntityCreature.getNavigator();
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(this.targetEntityClass == EntityPlayer.class) {
         if(this.theEntity instanceof EntityTameable && ((EntityTameable)this.theEntity).isTamed()) {
            return false;
         }

         this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, (double)this.distanceFromEntity);
         if(this.closestLivingEntity == null) {
            return false;
         }
      } else {
         List var1 = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double)this.distanceFromEntity, 3.0D, (double)this.distanceFromEntity), this.field_98218_a);
         if(var1.isEmpty()) {
            return false;
         }

         this.closestLivingEntity = (Entity)var1.get(0);
      }

      Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
      if(var2 == null) {
         return false;
      } else if(this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
         return false;
      } else {
         this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
         return this.entityPathEntity == null?false:this.entityPathEntity.isDestinationSame(var2);
      }
   }

   public boolean continueExecuting() {
      return !this.entityPathNavigate.noPath();
   }

   public void startExecuting() {
      this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
   }

   public void resetTask() {
      this.closestLivingEntity = null;
   }

   public void updateTask() {
      if(this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
         this.theEntity.getNavigator().setSpeed(this.nearSpeed);
      } else {
         this.theEntity.getNavigator().setSpeed(this.farSpeed);
      }
   }

   static EntityCreature func_98217_a(EntityAIAvoidEntity par0EntityAIAvoidEntity) {
      return par0EntityAIAvoidEntity.theEntity;
   }
}
