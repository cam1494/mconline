package net.minecraft.src;

import java.util.Random;

public class BlockLadder extends Block {
   protected BlockLadder(int par1) {
      super(par1, Material.circuits);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      this.updateLadderBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
   }

   public void updateLadderBounds(int par1) {
      float var3 = 0.125F;
      if(par1 == 2) {
         this.setBlockBounds(0.0F, 0.0F, 1.0F - var3, 1.0F, 1.0F, 1.0F);
      }

      if(par1 == 3) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var3);
      }

      if(par1 == 4) {
         this.setBlockBounds(1.0F - var3, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

      if(par1 == 5) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, var3, 1.0F, 1.0F);
      }
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return 8;
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return par1World.isBlockNormalCube(par2 - 1, par3, par4)?true:(par1World.isBlockNormalCube(par2 + 1, par3, par4)?true:(par1World.isBlockNormalCube(par2, par3, par4 - 1)?true:par1World.isBlockNormalCube(par2, par3, par4 + 1)));
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      int var10 = par9;
      if((par9 == 0 || par5 == 2) && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
         var10 = 2;
      }

      if((var10 == 0 || par5 == 3) && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
         var10 = 3;
      }

      if((var10 == 0 || par5 == 4) && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
         var10 = 4;
      }

      if((var10 == 0 || par5 == 5) && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
         var10 = 5;
      }

      return var10;
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      boolean var7 = false;
      if(var6 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
         var7 = true;
      }

      if(var6 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
         var7 = true;
      }

      if(var6 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
         var7 = true;
      }

      if(var6 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
         var7 = true;
      }

      if(!var7) {
         this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
         par1World.setBlockToAir(par2, par3, par4);
      }

      super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
   }

   public int quantityDropped(Random par1Random) {
      return 1;
   }
}
