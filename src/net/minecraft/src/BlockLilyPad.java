package net.minecraft.src;

import java.util.List;

public class BlockLilyPad extends BlockFlower {
   protected BlockLilyPad(int par1) {
      super(par1);
      float var2 = 0.5F;
      float var3 = 0.015625F;
      this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public int getRenderType() {
      return 23;
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
      if(par7Entity == null || !(par7Entity instanceof EntityBoat)) {
         super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      }
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
   }

   public int getBlockColor() {
      return 2129968;
   }

   public int getRenderColor(int par1) {
      return 2129968;
   }

   public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return 2129968;
   }

   protected boolean canThisPlantGrowOnThisBlockID(int par1) {
      return par1 == Block.waterStill.blockID;
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      return par3 >= 0 && par3 < 256?par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water && par1World.getBlockMetadata(par2, par3 - 1, par4) == 0:false;
   }
}
