package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockStairs extends Block {
   private static final int[][] field_72159_a = new int[][]{{2, 6}, {3, 7}, {2, 3}, {6, 7}, {0, 4}, {1, 5}, {0, 1}, {4, 5}};
   private final Block modelBlock;
   private final int modelBlockMetadata;
   private boolean field_72156_cr;
   private int field_72160_cs;

   protected BlockStairs(int par1, Block par2Block, int par3) {
      super(par1, par2Block.blockMaterial);
      this.modelBlock = par2Block;
      this.modelBlockMetadata = par3;
      this.setHardness(par2Block.blockHardness);
      this.setResistance(par2Block.blockResistance / 3.0F);
      this.setStepSound(par2Block.stepSound);
      this.setLightOpacity(255);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      if(this.field_72156_cr) {
         this.setBlockBounds(0.5F * (float)(this.field_72160_cs % 2), 0.5F * (float)(this.field_72160_cs / 2 % 2), 0.5F * (float)(this.field_72160_cs / 4 % 2), 0.5F + 0.5F * (float)(this.field_72160_cs % 2), 0.5F + 0.5F * (float)(this.field_72160_cs / 2 % 2), 0.5F + 0.5F * (float)(this.field_72160_cs / 4 % 2));
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return 10;
   }

   public void func_82541_d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      if((var5 & 4) != 0) {
         this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
   }

   public static boolean isBlockStairsID(int par0) {
      return par0 > 0 && Block.blocksList[par0] instanceof BlockStairs;
   }

   private boolean isBlockStairsDirection(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
      return isBlockStairsID(var6) && par1IBlockAccess.getBlockMetadata(par2, par3, par4) == par5;
   }

   public boolean func_82542_g(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = var5 & 3;
      float var7 = 0.5F;
      float var8 = 1.0F;
      if((var5 & 4) != 0) {
         var7 = 0.0F;
         var8 = 0.5F;
      }

      float var9 = 0.0F;
      float var10 = 1.0F;
      float var11 = 0.0F;
      float var12 = 0.5F;
      boolean var13 = true;
      int var14;
      int var15;
      int var16;
      if(var6 == 0) {
         var9 = 0.5F;
         var12 = 1.0F;
         var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
         var15 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var16 = var15 & 3;
            if(var16 == 3 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
               var12 = 0.5F;
               var13 = false;
            } else if(var16 == 2 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if(var6 == 1) {
         var10 = 0.5F;
         var12 = 1.0F;
         var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
         var15 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var16 = var15 & 3;
            if(var16 == 3 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
               var12 = 0.5F;
               var13 = false;
            } else if(var16 == 2 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if(var6 == 2) {
         var11 = 0.5F;
         var12 = 1.0F;
         var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
         var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var16 = var15 & 3;
            if(var16 == 1 && !this.isBlockStairsDirection(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
               var10 = 0.5F;
               var13 = false;
            } else if(var16 == 0 && !this.isBlockStairsDirection(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      } else if(var6 == 3) {
         var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
         var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var16 = var15 & 3;
            if(var16 == 1 && !this.isBlockStairsDirection(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
               var10 = 0.5F;
               var13 = false;
            } else if(var16 == 0 && !this.isBlockStairsDirection(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      }

      this.setBlockBounds(var9, var7, var11, var10, var8, var12);
      return var13;
   }

   public boolean func_82544_h(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = var5 & 3;
      float var7 = 0.5F;
      float var8 = 1.0F;
      if((var5 & 4) != 0) {
         var7 = 0.0F;
         var8 = 0.5F;
      }

      float var9 = 0.0F;
      float var10 = 0.5F;
      float var11 = 0.5F;
      float var12 = 1.0F;
      boolean var13 = false;
      int var14;
      int var15;
      int var16;
      if(var6 == 0) {
         var14 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
         var15 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var16 = var15 & 3;
            if(var16 == 3 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if(var16 == 2 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if(var6 == 1) {
         var14 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
         var15 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var9 = 0.5F;
            var10 = 1.0F;
            var16 = var15 & 3;
            if(var16 == 3 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 - 1, var5)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if(var16 == 2 && !this.isBlockStairsDirection(par1IBlockAccess, par2, par3, par4 + 1, var5)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if(var6 == 2) {
         var14 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
         var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var11 = 0.0F;
            var12 = 0.5F;
            var16 = var15 & 3;
            if(var16 == 1 && !this.isBlockStairsDirection(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
               var13 = true;
            } else if(var16 == 0 && !this.isBlockStairsDirection(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      } else if(var6 == 3) {
         var14 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
         var15 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);
         if(isBlockStairsID(var14) && (var5 & 4) == (var15 & 4)) {
            var16 = var15 & 3;
            if(var16 == 1 && !this.isBlockStairsDirection(par1IBlockAccess, par2 - 1, par3, par4, var5)) {
               var13 = true;
            } else if(var16 == 0 && !this.isBlockStairsDirection(par1IBlockAccess, par2 + 1, par3, par4, var5)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      }

      if(var13) {
         this.setBlockBounds(var9, var7, var11, var10, var8, var12);
      }

      return var13;
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
      this.func_82541_d(par1World, par2, par3, par4);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      boolean var8 = this.func_82542_g(par1World, par2, par3, par4);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      if(var8 && this.func_82544_h(par1World, par2, par3, par4)) {
         super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      }

      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      this.modelBlock.randomDisplayTick(par1World, par2, par3, par4, par5Random);
   }

   public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
      this.modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
   }

   public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
      this.modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
   }

   public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return this.modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
   }

   public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return this.modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
   }

   public float getExplosionResistance(Entity par1Entity) {
      return this.modelBlock.getExplosionResistance(par1Entity);
   }

   public int getRenderBlockPass() {
      return this.modelBlock.getRenderBlockPass();
   }

   public Icon getIcon(int par1, int par2) {
      return this.modelBlock.getIcon(par1, this.modelBlockMetadata);
   }

   public int tickRate(World par1World) {
      return this.modelBlock.tickRate(par1World);
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return this.modelBlock.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3) {
      this.modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
   }

   public boolean isCollidable() {
      return this.modelBlock.isCollidable();
   }

   public boolean canCollideCheck(int par1, boolean par2) {
      return this.modelBlock.canCollideCheck(par1, par2);
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return this.modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      this.onNeighborBlockChange(par1World, par2, par3, par4, 0);
      this.modelBlock.onBlockAdded(par1World, par2, par3, par4);
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      this.modelBlock.breakBlock(par1World, par2, par3, par4, par5, par6);
   }

   public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      this.modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      this.modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      return this.modelBlock.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
   }

   public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {
      this.modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int var7 = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      int var8 = par1World.getBlockMetadata(par2, par3, par4) & 4;
      if(var7 == 0) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | var8, 2);
      }

      if(var7 == 1) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | var8, 2);
      }

      if(var7 == 2) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | var8, 2);
      }

      if(var7 == 3) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | var8, 2);
      }
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      return par5 != 0 && (par5 == 1 || (double)par7 <= 0.5D)?par9:par9 | 4;
   }

   public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3) {
      MovingObjectPosition[] var7 = new MovingObjectPosition[8];
      int var8 = par1World.getBlockMetadata(par2, par3, par4);
      int var9 = var8 & 3;
      boolean var10 = (var8 & 4) == 4;
      int[] var11 = field_72159_a[var9 + (var10?4:0)];
      this.field_72156_cr = true;

      int var14;
      int var15;
      int var16;
      for(int var12 = 0; var12 < 8; ++var12) {
         this.field_72160_cs = var12;
         int[] var13 = var11;
         var14 = var11.length;

         for(var15 = 0; var15 < var14; ++var15) {
            var16 = var13[var15];
            if(var16 == var12) {
               ;
            }
         }

         var7[var12] = super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
      }

      int[] var21 = var11;
      int var23 = var11.length;

      for(var14 = 0; var14 < var23; ++var14) {
         var15 = var21[var14];
         var7[var15] = null;
      }

      MovingObjectPosition var22 = null;
      double var24 = 0.0D;
      MovingObjectPosition[] var25 = var7;
      var16 = var7.length;

      for(int var17 = 0; var17 < var16; ++var17) {
         MovingObjectPosition var18 = var25[var17];
         if(var18 != null) {
            double var19 = var18.hitVec.squareDistanceTo(par6Vec3);
            if(var19 > var24) {
               var22 = var18;
               var24 = var19;
            }
         }
      }

      return var22;
   }

   public void registerIcons(IconRegister par1IconRegister) {}
}
