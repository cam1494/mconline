package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowParent extends EntityAIBase {
   EntityAnimal childAnimal;
   EntityAnimal parentAnimal;
   double field_75347_c;
   private int field_75345_d;

   public EntityAIFollowParent(EntityAnimal par1EntityAnimal, double par2) {
      this.childAnimal = par1EntityAnimal;
      this.field_75347_c = par2;
   }

   public boolean shouldExecute() {
      if(this.childAnimal.getGrowingAge() >= 0) {
         return false;
      } else {
         List var1 = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.boundingBox.expand(8.0D, 4.0D, 8.0D));
         EntityAnimal var2 = null;
         double var3 = Double.MAX_VALUE;
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            EntityAnimal var6 = (EntityAnimal)var5.next();
            if(var6.getGrowingAge() >= 0) {
               double var7 = this.childAnimal.getDistanceSqToEntity(var6);
               if(var7 <= var3) {
                  var3 = var7;
                  var2 = var6;
               }
            }
         }

         if(var2 == null) {
            return false;
         } else if(var3 < 9.0D) {
            return false;
         } else {
            this.parentAnimal = var2;
            return true;
         }
      }
   }

   public boolean continueExecuting() {
      if(!this.parentAnimal.isEntityAlive()) {
         return false;
      } else {
         double var1 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
         return var1 >= 9.0D && var1 <= 256.0D;
      }
   }

   public void startExecuting() {
      this.field_75345_d = 0;
   }

   public void resetTask() {
      this.parentAnimal = null;
   }

   public void updateTask() {
      if(--this.field_75345_d <= 0) {
         this.field_75345_d = 10;
         this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.field_75347_c);
      }
   }
}
