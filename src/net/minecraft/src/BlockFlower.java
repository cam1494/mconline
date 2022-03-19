package net.minecraft.src;

import java.util.Random;

public class BlockFlower extends Block {
   protected BlockFlower(int par1, Material par2Material) {
      super(par1, par2Material);
      this.setTickRandomly(true);
      float var3 = 0.2F;
      this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   protected BlockFlower(int par1) {
      this(par1, Material.plants);
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
   }

   protected boolean canThisPlantGrowOnThisBlockID(int par1) {
      return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID;
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
      this.checkFlowerChange(par1World, par2, par3, par4);
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      this.checkFlowerChange(par1World, par2, par3, par4);
   }

   protected final void checkFlowerChange(World par1World, int par2, int par3, int par4) {
      if(!this.canBlockStay(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlock(par2, par3, par4, 0, 0, 2);
      }
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return 1;
   }
}
