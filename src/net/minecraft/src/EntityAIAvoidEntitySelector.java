package net.minecraft.src;

class EntityAIAvoidEntitySelector implements IEntitySelector {
   final EntityAIAvoidEntity entityAvoiderAI;

   EntityAIAvoidEntitySelector(EntityAIAvoidEntity par1EntityAIAvoidEntity) {
      this.entityAvoiderAI = par1EntityAIAvoidEntity;
   }

   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity.isEntityAlive() && EntityAIAvoidEntity.func_98217_a(this.entityAvoiderAI).getEntitySenses().canSee(par1Entity);
   }
}
