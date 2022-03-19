package net.minecraft.src;

import java.util.Random;

public class BlockCake extends Block {
   private Icon cakeTopIcon;
   private Icon cakeBottomIcon;
   private Icon field_94382_c;

   protected BlockCake(int par1) {
      super(par1, Material.cake);
      this.setTickRandomly(true);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      float var6 = 0.0625F;
      float var7 = (float)(1 + var5 * 2) / 16.0F;
      float var8 = 0.5F;
      this.setBlockBounds(var7, 0.0F, var6, 1.0F - var6, var8, 1.0F - var6);
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.0625F;
      float var2 = 0.5F;
      this.setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      int var5 = par1World.getBlockMetadata(par2, par3, par4);
      float var6 = 0.0625F;
      float var7 = (float)(1 + var5 * 2) / 16.0F;
      float var8 = 0.5F;
      return AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + var7), (double)par3, (double)((float)par4 + var6), (double)((float)(par2 + 1) - var6), (double)((float)par3 + var8 - var6), (double)((float)(par4 + 1) - var6));
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      int var5 = par1World.getBlockMetadata(par2, par3, par4);
      float var6 = 0.0625F;
      float var7 = (float)(1 + var5 * 2) / 16.0F;
      float var8 = 0.5F;
      return AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + var7), (double)par3, (double)((float)par4 + var6), (double)((float)(par2 + 1) - var6), (double)((float)par3 + var8), (double)((float)(par4 + 1) - var6));
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 1?this.cakeTopIcon:(par1 == 0?this.cakeBottomIcon:(par2 > 0 && par1 == 4?this.field_94382_c:this.blockIcon));
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
      this.field_94382_c = par1IconRegister.registerIcon(this.getTextureName() + "_inner");
      this.cakeTopIcon = par1IconRegister.registerIcon(this.getTextureName() + "_top");
      this.cakeBottomIcon = par1IconRegister.registerIcon(this.getTextureName() + "_bottom");
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
      return true;
   }

   public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
      this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
   }

   private void eatCakeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
      if(par5EntityPlayer.canEat(false)) {
         par5EntityPlayer.getFoodStats().addStats(2, 0.1F);
         int var6 = par1World.getBlockMetadata(par2, par3, par4) + 1;
         if(var6 >= 6) {
            par1World.setBlockToAir(par2, par3, par4);
         } else {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
         }
      }
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return !super.canPlaceBlockAt(par1World, par2, par3, par4)?false:this.canBlockStay(par1World, par2, par3, par4);
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!this.canBlockStay(par1World, par2, par3, par4)) {
         par1World.setBlockToAir(par2, par3, par4);
      }
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid();
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return 0;
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Item.cake.itemID;
   }
}
