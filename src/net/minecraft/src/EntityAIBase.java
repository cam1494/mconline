package net.minecraft.src;

public abstract class EntityAIBase {
   private int mutexBits;

   public abstract boolean shouldExecute();

   public boolean continueExecuting() {
      return this.shouldExecute();
   }

   public boolean isInterruptible() {
      return true;
   }

   public void startExecuting() {}

   public void resetTask() {}

   public void updateTask() {}

   public void setMutexBits(int par1) {
      this.mutexBits = par1;
   }

   public int getMutexBits() {
      return this.mutexBits;
   }
}
