package net.minecraft.src;

public abstract class BlockRotatedPillar extends Block {
   protected Icon field_111051_a;

   protected BlockRotatedPillar(int par1, Material par2Material) {
      super(par1, par2Material);
   }

   public int getRenderType() {
      return 31;
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      int var10 = par9 & 3;
      byte var11 = 0;
      switch(par5) {
      case 0:
      case 1:
         var11 = 0;
         break;
      case 2:
      case 3:
         var11 = 8;
         break;
      case 4:
      case 5:
         var11 = 4;
      }

      return var10 | var11;
   }

   public Icon getIcon(int par1, int par2) {
      int var3 = par2 & 12;
      int var4 = par2 & 3;
      return var3 == 0 && (par1 == 1 || par1 == 0)?this.getEndIcon(var4):(var3 == 4 && (par1 == 5 || par1 == 4)?this.getEndIcon(var4):(var3 == 8 && (par1 == 2 || par1 == 3)?this.getEndIcon(var4):this.getSideIcon(var4)));
   }

   protected abstract Icon getSideIcon(int var1);

   protected Icon getEndIcon(int par1) {
      return this.field_111051_a;
   }

   public int damageDropped(int par1) {
      return par1 & 3;
   }

   public int func_111050_e(int par1) {
      return par1 & 3;
   }

   protected ItemStack createStackedBlock(int par1) {
      return new ItemStack(this.blockID, 1, this.func_111050_e(par1));
   }
}
