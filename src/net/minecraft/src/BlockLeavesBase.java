package net.minecraft.src;

public class BlockLeavesBase extends Block {
   protected boolean graphicsLevel;

   protected BlockLeavesBase(int par1, Material par2Material, boolean par3) {
      super(par1, par2Material);
      this.graphicsLevel = par3;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
      return !this.graphicsLevel && var6 == this.blockID?false:super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
   }
}
