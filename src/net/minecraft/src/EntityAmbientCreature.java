package net.minecraft.src;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals {
   public EntityAmbientCreature(World par1World) {
      super(par1World);
   }

   public boolean allowLeashing() {
      return false;
   }

   protected boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
   }
}
