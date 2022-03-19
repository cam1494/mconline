package net.minecraft.src;

final class EntityWitherAttackFilter implements IEntitySelector {
   public boolean isEntityApplicable(Entity par1Entity) {
      return par1Entity instanceof EntityLivingBase && ((EntityLivingBase)par1Entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
   }
}
