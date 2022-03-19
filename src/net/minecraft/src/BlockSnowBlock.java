package net.minecraft.src;

import java.util.Random;

public class BlockSnowBlock extends Block {
   protected BlockSnowBlock(int par1) {
      super(par1, Material.craftedSnow);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.snowball.itemID;
   }

   public int quantityDropped(Random par1Random) {
      return 4;
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if(par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
      }
   }
}
