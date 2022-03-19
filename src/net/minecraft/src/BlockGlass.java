package net.minecraft.src;

import java.util.Random;

public class BlockGlass extends BlockBreakable {
   public BlockGlass(int par1, Material par2Material, boolean par3) {
      super(par1, "glass", par2Material, par3);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public int getRenderBlockPass() {
      return 0;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   protected boolean canSilkHarvest() {
      return true;
   }
}
