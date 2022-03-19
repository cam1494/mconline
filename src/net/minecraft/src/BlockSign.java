package net.minecraft.src;

import java.util.Random;

public class BlockSign extends BlockContainer {
   private Class signEntityClass;
   private boolean isFreestanding;

   protected BlockSign(int par1, Class par2Class, boolean par3) {
      super(par1, Material.wood);
      this.isFreestanding = par3;
      this.signEntityClass = par2Class;
      float var4 = 0.25F;
      float var5 = 1.0F;
      this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var5, 0.5F + var4);
   }

   public Icon getIcon(int par1, int par2) {
      return Block.planks.getBlockTextureFromSide(par1);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      if(!this.isFreestanding) {
         int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
         float var6 = 0.28125F;
         float var7 = 0.78125F;
         float var8 = 0.0F;
         float var9 = 1.0F;
         float var10 = 0.125F;
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         if(var5 == 2) {
            this.setBlockBounds(var8, var6, 1.0F - var10, var9, var7, 1.0F);
         }

         if(var5 == 3) {
            this.setBlockBounds(var8, var6, 0.0F, var9, var7, var10);
         }

         if(var5 == 4) {
            this.setBlockBounds(1.0F - var10, var6, var8, 1.0F, var7, var9);
         }

         if(var5 == 5) {
            this.setBlockBounds(0.0F, var6, var8, var10, var7, var9);
         }
      }
   }

   public int getRenderType() {
      return -1;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return true;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public TileEntity createNewTileEntity(World par1World) {
      try {
         return (TileEntity)this.signEntityClass.newInstance();
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.sign.itemID;
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      boolean var6 = false;
      if(this.isFreestanding) {
         if(!par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid()) {
            var6 = true;
         }
      } else {
         int var7 = par1World.getBlockMetadata(par2, par3, par4);
         var6 = true;
         if(var7 == 2 && par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid()) {
            var6 = false;
         }

         if(var7 == 3 && par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid()) {
            var6 = false;
         }

         if(var7 == 4 && par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid()) {
            var6 = false;
         }

         if(var7 == 5 && par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid()) {
            var6 = false;
         }
      }

      if(var6) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
      }

      super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Item.sign.itemID;
   }

   public void registerIcons(IconRegister par1IconRegister) {}
}
