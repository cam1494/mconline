package net.minecraft.src;

public class EntityAISwimming extends EntityAIBase {
   private EntityLiving theEntity;

   public EntityAISwimming(EntityLiving par1EntityLiving) {
      this.theEntity = par1EntityLiving;
      this.setMutexBits(4);
      par1EntityLiving.getNavigator().setCanSwim(true);
   }

   public boolean shouldExecute() {
      return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
   }

   public void updateTask() {
      if(this.theEntity.getRNG().nextFloat() < 0.8F) {
         this.theEntity.getJumpHelper().setJumping();
      }
   }
}
