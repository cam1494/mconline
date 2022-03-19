package net.minecraft.src;

import java.util.List;

public class BlockCarpet extends Block {
   protected BlockCarpet(int par1) {
      super(par1, Material.materialCarpet);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.func_111047_d(0);
   }

   public Icon getIcon(int par1, int par2) {
      return Block.cloth.getIcon(par1, par2);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      byte var5 = 0;
      float var6 = 0.0625F;
      return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + (float)var5 * var6), (double)par4 + this.maxZ);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public void setBlockBoundsForItemRender() {
      this.func_111047_d(0);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      this.func_111047_d(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
   }

   protected void func_111047_d(int par1) {
      byte var2 = 0;
      float var3 = (float)(1 * (1 + var2)) / 16.0F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      this.func_111046_k(par1World, par2, par3, par4);
   }

   private boolean func_111046_k(World par1World, int par2, int par3, int par4) {
      if(!this.canBlockStay(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
         return false;
      } else {
         return true;
      }
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      return !par1World.isAirBlock(par2, par3 - 1, par4);
   }

   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return par5 == 1?true:super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int var4 = 0; var4 < 16; ++var4) {
         par3List.add(new ItemStack(par1, 1, var4));
      }
   }

   public void registerIcons(IconRegister par1IconRegister) {}
}
