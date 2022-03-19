package net.minecraft.src;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
   EntityTameable theDefendingTameable;
   EntityLivingBase theOwnerAttacker;
   private int field_142051_e;

   public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable) {
      super(par1EntityTameable, false);
      this.theDefendingTameable = par1EntityTameable;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(!this.theDefendingTameable.isTamed()) {
         return false;
      } else {
         EntityLivingBase var1 = this.theDefendingTameable.func_130012_q();
         if(var1 == null) {
            return false;
         } else {
            this.theOwnerAttacker = var1.getAITarget();
            int var2 = var1.func_142015_aE();
            return var2 != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.func_142018_a(this.theOwnerAttacker, var1);
         }
      }
   }

   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.theOwnerAttacker);
      EntityLivingBase var1 = this.theDefendingTameable.func_130012_q();
      if(var1 != null) {
         this.field_142051_e = var1.func_142015_aE();
      }

      super.startExecuting();
   }
}
