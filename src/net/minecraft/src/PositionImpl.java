package net.minecraft.src;

public class PositionImpl implements IPosition {
   protected final double x;
   protected final double y;
   protected final double z;

   public PositionImpl(double par1, double par3, double par5) {
      this.x = par1;
      this.y = par3;
      this.z = par5;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }
}
