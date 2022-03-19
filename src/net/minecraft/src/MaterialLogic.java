package net.minecraft.src;

public class MaterialLogic extends Material {
   public MaterialLogic(MapColor par1MapColor) {
      super(par1MapColor);
      this.setAdventureModeExempt();
   }

   public boolean isSolid() {
      return false;
   }

   public boolean getCanBlockGrass() {
      return false;
   }

   public boolean blocksMovement() {
      return false;
   }
}
