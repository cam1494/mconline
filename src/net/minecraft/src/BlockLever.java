package net.minecraft.src;

public class BlockLever extends Block {
   protected BlockLever(int par1) {
      super(par1, Material.circuits);
      this.setCreativeTab(CreativeTabs.tabRedstone);
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
      return 12;
   }

   public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
      return par5 == 0 && par1World.isBlockNormalCube(par2, par3 + 1, par4)?true:(par5 == 1 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)?true:(par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)?true:(par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)?true:(par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)?true:par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)))));
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return par1World.isBlockNormalCube(par2 - 1, par3, par4)?true:(par1World.isBlockNormalCube(par2 + 1, par3, par4)?true:(par1World.isBlockNormalCube(par2, par3, par4 - 1)?true:(par1World.isBlockNormalCube(par2, par3, par4 + 1)?true:(par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)?true:par1World.isBlockNormalCube(par2, par3 + 1, par4)))));
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      int var11 = par9 & 8;
      int var10 = par9 & 7;
      byte var12 = -1;
      if(par5 == 0 && par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
         var12 = 0;
      }

      if(par5 == 1 && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
         var12 = 5;
      }

      if(par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
         var12 = 4;
      }

      if(par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
         var12 = 3;
      }

      if(par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
         var12 = 2;
      }

      if(par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
         var12 = 1;
      }

      return var12 + var11;
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int var7 = par1World.getBlockMetadata(par2, par3, par4);
      int var8 = var7 & 7;
      int var9 = var7 & 8;
      if(var8 == invertMetadata(1)) {
         if((MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 1) == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5 | var9, 2);
         } else {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 6 | var9, 2);
         }
      } else if(var8 == invertMetadata(0)) {
         if((MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 1) == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 7 | var9, 2);
         } else {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | var9, 2);
         }
      }
   }

   public static int invertMetadata(int par0) {
      switch(par0) {
      case 0:
         return 0;
      case 1:
         return 5;
      case 2:
         return 4;
      case 3:
         return 3;
      case 4:
         return 2;
      case 5:
         return 1;
      default:
         return -1;
      }
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(this.checkIfAttachedToBlock(par1World, par2, par3, par4)) {
         int var6 = par1World.getBlockMetadata(par2, par3, par4) & 7;
         boolean var7 = false;
         if(!par1World.isBlockNormalCube(par2 - 1, par3, par4) && var6 == 1) {
            var7 = true;
         }

         if(!par1World.isBlockNormalCube(par2 + 1, par3, par4) && var6 == 2) {
            var7 = true;
         }

         if(!par1World.isBlockNormalCube(par2, par3, par4 - 1) && var6 == 3) {
            var7 = true;
         }

         if(!par1World.isBlockNormalCube(par2, par3, par4 + 1) && var6 == 4) {
            var7 = true;
         }

         if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && var6 == 5) {
            var7 = true;
         }

         if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && var6 == 6) {
            var7 = true;
         }

         if(!par1World.isBlockNormalCube(par2, par3 + 1, par4) && var6 == 0) {
            var7 = true;
         }

         if(!par1World.isBlockNormalCube(par2, par3 + 1, par4) && var6 == 7) {
            var7 = true;
         }

         if(var7) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
         }
      }
   }

   private boolean checkIfAttachedToBlock(World par1World, int par2, int par3, int par4) {
      if(!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
         return false;
      } else {
         return true;
      }
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
      float var6 = 0.1875F;
      if(var5 == 1) {
         this.setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
      } else if(var5 == 2) {
         this.setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
      } else if(var5 == 3) {
         this.setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
      } else if(var5 == 4) {
         this.setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
      } else if(var5 != 5 && var5 != 6) {
         if(var5 == 0 || var5 == 7) {
            var6 = 0.25F;
            this.setBlockBounds(0.5F - var6, 0.4F, 0.5F - var6, 0.5F + var6, 1.0F, 0.5F + var6);
         }
      } else {
         var6 = 0.25F;
         this.setBlockBounds(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
      }
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         int var10 = par1World.getBlockMetadata(par2, par3, par4);
         int var11 = var10 & 7;
         int var12 = 8 - (var10 & 8);
         par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 + var12, 3);
         par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, var12 > 0?0.6F:0.5F);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
         if(var11 == 1) {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         } else if(var11 == 2) {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         } else if(var11 == 3) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         } else if(var11 == 4) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         } else if(var11 != 5 && var11 != 6) {
            if(var11 == 0 || var11 == 7) {
               par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            }
         } else {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         }

         return true;
      }
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      if((par6 & 8) > 0) {
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
         int var7 = par6 & 7;
         if(var7 == 1) {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         } else if(var7 == 2) {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         } else if(var7 == 3) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         } else if(var7 == 4) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         } else if(var7 != 5 && var7 != 6) {
            if(var7 == 0 || var7 == 7) {
               par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            }
         } else {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         }
      }

      super.breakBlock(par1World, par2, par3, par4, par5, par6);
   }

   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) > 0?15:0;
   }

   public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      if((var6 & 8) == 0) {
         return 0;
      } else {
         int var7 = var6 & 7;
         return var7 == 0 && par5 == 0?15:(var7 == 7 && par5 == 0?15:(var7 == 6 && par5 == 1?15:(var7 == 5 && par5 == 1?15:(var7 == 4 && par5 == 2?15:(var7 == 3 && par5 == 3?15:(var7 == 2 && par5 == 4?15:(var7 == 1 && par5 == 5?15:0)))))));
      }
   }

   public boolean canProvidePower() {
      return true;
   }
}
