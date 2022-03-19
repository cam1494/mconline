package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends Block {
   private boolean wiresProvidePower = true;
   private Set blocksNeedingUpdate = new HashSet();
   private Icon field_94413_c;
   private Icon field_94410_cO;
   private Icon field_94411_cP;
   private Icon field_94412_cQ;

   public BlockRedstoneWire(int par1) {
      super(par1, Material.circuits);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
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
      return 5;
   }

   public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return 8388608;
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.glowStone.blockID;
   }

   private void updateAndPropagateCurrentStrength(World par1World, int par2, int par3, int par4) {
      this.calculateCurrentChanges(par1World, par2, par3, par4, par2, par3, par4);
      ArrayList var5 = new ArrayList(this.blocksNeedingUpdate);
      this.blocksNeedingUpdate.clear();

      for(int var6 = 0; var6 < var5.size(); ++var6) {
         ChunkPosition var7 = (ChunkPosition)var5.get(var6);
         par1World.notifyBlocksOfNeighborChange(var7.x, var7.y, var7.z, this.blockID);
      }
   }

   private void calculateCurrentChanges(World par1World, int par2, int par3, int par4, int par5, int par6, int par7) {
      int var8 = par1World.getBlockMetadata(par2, par3, par4);
      byte var9 = 0;
      int var15 = this.getMaxCurrentStrength(par1World, par5, par6, par7, var9);
      this.wiresProvidePower = false;
      int var10 = par1World.getStrongestIndirectPower(par2, par3, par4);
      this.wiresProvidePower = true;
      if(var10 > 0 && var10 > var15 - 1) {
         var15 = var10;
      }

      int var11 = 0;

      for(int var12 = 0; var12 < 4; ++var12) {
         int var13 = par2;
         int var14 = par4;
         if(var12 == 0) {
            var13 = par2 - 1;
         }

         if(var12 == 1) {
            ++var13;
         }

         if(var12 == 2) {
            var14 = par4 - 1;
         }

         if(var12 == 3) {
            ++var14;
         }

         if(var13 != par5 || var14 != par7) {
            var11 = this.getMaxCurrentStrength(par1World, var13, par3, var14, var11);
         }

         if(par1World.isBlockNormalCube(var13, par3, var14) && !par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
            if((var13 != par5 || var14 != par7) && par3 >= par6) {
               var11 = this.getMaxCurrentStrength(par1World, var13, par3 + 1, var14, var11);
            }
         } else if(!par1World.isBlockNormalCube(var13, par3, var14) && (var13 != par5 || var14 != par7) && par3 <= par6) {
            var11 = this.getMaxCurrentStrength(par1World, var13, par3 - 1, var14, var11);
         }
      }

      if(var11 > var15) {
         var15 = var11 - 1;
      } else if(var15 > 0) {
         --var15;
      } else {
         var15 = 0;
      }

      if(var10 > var15 - 1) {
         var15 = var10;
      }

      if(var8 != var15) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, var15, 2);
         this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4));
         this.blocksNeedingUpdate.add(new ChunkPosition(par2 - 1, par3, par4));
         this.blocksNeedingUpdate.add(new ChunkPosition(par2 + 1, par3, par4));
         this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3 - 1, par4));
         this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3 + 1, par4));
         this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 - 1));
         this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 + 1));
      }
   }

   private void notifyWireNeighborsOfNeighborChange(World par1World, int par2, int par3, int par4) {
      if(par1World.getBlockId(par2, par3, par4) == this.blockID) {
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
      }
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      super.onBlockAdded(par1World, par2, par3, par4);
      if(!par1World.isRemote) {
         this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
         par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);
         if(par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
         }

         if(par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
         }

         if(par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
         }

         if(par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
         }
      }
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      super.breakBlock(par1World, par2, par3, par4, par5, par6);
      if(!par1World.isRemote) {
         par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
         this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);
         if(par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
         }

         if(par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
         }

         if(par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
         }

         if(par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
         } else {
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
         }
      }
   }

   private int getMaxCurrentStrength(World par1World, int par2, int par3, int par4, int par5) {
      if(par1World.getBlockId(par2, par3, par4) != this.blockID) {
         return par5;
      } else {
         int var6 = par1World.getBlockMetadata(par2, par3, par4);
         return var6 > par5?var6:par5;
      }
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!par1World.isRemote) {
         boolean var6 = this.canPlaceBlockAt(par1World, par2, par3, par4);
         if(var6) {
            this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
         } else {
            this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
            par1World.setBlockToAir(par2, par3, par4);
         }

         super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
      }
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.redstone.itemID;
   }

   public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return !this.wiresProvidePower?0:this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5);
   }

   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      if(!this.wiresProvidePower) {
         return 0;
      } else {
         int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
         if(var6 == 0) {
            return 0;
         } else if(par5 == 1) {
            return var6;
         } else {
            boolean var7 = isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3, par4, 1) || !par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1);
            boolean var8 = isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3, par4, 3) || !par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1);
            boolean var9 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 - 1, 2) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1);
            boolean var10 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 + 1, 0) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1);
            if(!par1IBlockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
               if(par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1)) {
                  var7 = true;
               }

               if(par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1)) {
                  var8 = true;
               }

               if(par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1)) {
                  var9 = true;
               }

               if(par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1)) {
                  var10 = true;
               }
            }

            return !var9 && !var8 && !var7 && !var10 && par5 >= 2 && par5 <= 5?var6:(par5 == 2 && var9 && !var7 && !var8?var6:(par5 == 3 && var10 && !var7 && !var8?var6:(par5 == 4 && var7 && !var9 && !var10?var6:(par5 == 5 && var8 && !var9 && !var10?var6:0))));
         }
      }
   }

   public boolean canProvidePower() {
      return this.wiresProvidePower;
   }

   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      if(var6 > 0) {
         double var7 = (double)par2 + 0.5D + ((double)par5Random.nextFloat() - 0.5D) * 0.2D;
         double var9 = (double)((float)par3 + 0.0625F);
         double var11 = (double)par4 + 0.5D + ((double)par5Random.nextFloat() - 0.5D) * 0.2D;
         float var13 = (float)var6 / 15.0F;
         float var14 = var13 * 0.6F + 0.4F;
         if(var6 == 0) {
            var14 = 0.0F;
         }

         float var15 = var13 * var13 * 0.7F - 0.5F;
         float var16 = var13 * var13 * 0.6F - 0.7F;
         if(var15 < 0.0F) {
            var15 = 0.0F;
         }

         if(var16 < 0.0F) {
            var16 = 0.0F;
         }

         par1World.spawnParticle("reddust", var7, var9, var11, (double)var14, (double)var15, (double)var16);
      }
   }

   public static boolean isPowerProviderOrWire(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4) {
      int var5 = par0IBlockAccess.getBlockId(par1, par2, par3);
      if(var5 == Block.redstoneWire.blockID) {
         return true;
      } else if(var5 == 0) {
         return false;
      } else if(!Block.redstoneRepeaterIdle.func_94487_f(var5)) {
         return Block.blocksList[var5].canProvidePower() && par4 != -1;
      } else {
         int var6 = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
         return par4 == (var6 & 3) || par4 == Direction.rotateOpposite[var6 & 3];
      }
   }

   public static boolean isPoweredOrRepeater(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4) {
      if(isPowerProviderOrWire(par0IBlockAccess, par1, par2, par3, par4)) {
         return true;
      } else {
         int var5 = par0IBlockAccess.getBlockId(par1, par2, par3);
         if(var5 == Block.redstoneRepeaterActive.blockID) {
            int var6 = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
            return par4 == (var6 & 3);
         } else {
            return false;
         }
      }
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Item.redstone.itemID;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.field_94413_c = par1IconRegister.registerIcon(this.getTextureName() + "_" + "cross");
      this.field_94410_cO = par1IconRegister.registerIcon(this.getTextureName() + "_" + "line");
      this.field_94411_cP = par1IconRegister.registerIcon(this.getTextureName() + "_" + "cross_overlay");
      this.field_94412_cQ = par1IconRegister.registerIcon(this.getTextureName() + "_" + "line_overlay");
      this.blockIcon = this.field_94413_c;
   }

   public static Icon getRedstoneWireIcon(String par0Str) {
      return par0Str.equals("cross")?Block.redstoneWire.field_94413_c:(par0Str.equals("line")?Block.redstoneWire.field_94410_cO:(par0Str.equals("cross_overlay")?Block.redstoneWire.field_94411_cP:(par0Str.equals("line_overlay")?Block.redstoneWire.field_94412_cQ:null)));
   }
}
