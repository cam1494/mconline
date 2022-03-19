package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block {
   protected BlockSnow(int par1) {
      super(par1, Material.snow);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBoundsForSnowDepth(0);
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon("snow");
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      int var5 = par1World.getBlockMetadata(par2, par3, par4) & 7;
      float var6 = 0.125F;
      return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + (float)var5 * var6), (double)par4 + this.maxZ);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBoundsForSnowDepth(0);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      this.setBlockBoundsForSnowDepth(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
   }

   protected void setBlockBoundsForSnowDepth(int par1) {
      int var2 = par1 & 7;
      float var3 = (float)(2 * (1 + var2)) / 16.0F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      int var5 = par1World.getBlockId(par2, par3 - 1, par4);
      return var5 == 0?false:(var5 == this.blockID && (par1World.getBlockMetadata(par2, par3 - 1, par4) & 7) == 7?true:(var5 != Block.leaves.blockID && !Block.blocksList[var5].isOpaqueCube()?false:par1World.getBlockMaterial(par2, par3 - 1, par4).blocksMovement()));
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      this.canSnowStay(par1World, par2, par3, par4);
   }

   private boolean canSnowStay(World par1World, int par2, int par3, int par4) {
      if(!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
         return false;
      } else {
         return true;
      }
   }

   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
      int var7 = Item.snowball.itemID;
      int var8 = par6 & 7;
      this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(var7, var8 + 1, 0));
      par1World.setBlockToAir(par3, par4, par5);
      par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.snowball.itemID;
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if(par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
      }
   }

   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return par5 == 1?true:super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
   }
}
