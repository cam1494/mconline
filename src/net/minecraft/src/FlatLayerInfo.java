package net.minecraft.src;

public class FlatLayerInfo {
   private int layerCount;
   private int layerFillBlock;
   private int layerFillBlockMeta;
   private int layerMinimumY;

   public FlatLayerInfo(int par1, int par2) {
      this.layerCount = 1;
      this.layerCount = par1;
      this.layerFillBlock = par2;
   }

   public FlatLayerInfo(int par1, int par2, int par3) {
      this(par1, par2);
      this.layerFillBlockMeta = par3;
   }

   public int getLayerCount() {
      return this.layerCount;
   }

   public int getFillBlock() {
      return this.layerFillBlock;
   }

   public int getFillBlockMeta() {
      return this.layerFillBlockMeta;
   }

   public int getMinY() {
      return this.layerMinimumY;
   }

   public void setMinY(int par1) {
      this.layerMinimumY = par1;
   }

   public String toString() {
      String var1 = Integer.toString(this.layerFillBlock);
      if(this.layerCount > 1) {
         var1 = this.layerCount + "x" + var1;
      }

      if(this.layerFillBlockMeta > 0) {
         var1 = var1 + ":" + this.layerFillBlockMeta;
      }

      return var1;
   }
}
