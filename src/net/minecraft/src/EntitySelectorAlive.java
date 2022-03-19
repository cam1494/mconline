package net.minecraft.src;

final class EntitySelectorAlive implements IEntitySelector {
   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity.isEntityAlive();
   }
}
