package net.minecraft.src;

import java.util.Random;

public abstract class BlockRailBase extends Block {
   protected final boolean isPowered;

   public static final boolean isRailBlockAt(World par0World, int par1, int par2, int par3) {
      return isRailBlock(par0World.getBlockId(par1, par2, par3));
   }

   public static final boolean isRailBlock(int par0) {
      return par0 == Block.rail.blockID || par0 == Block.railPowered.blockID || par0 == Block.railDetector.blockID || par0 == Block.railActivator.blockID;
   }

   protected BlockRailBase(int par1, boolean par2) {
      super(par1, Material.circuits);
      this.isPowered = par2;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabTransport);
   }

   public boolean isPowered() {
      return this.isPowered;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      if(var5 >= 2 && var5 <= 5) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      }
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return 9;
   }

   public int quantityDropped(Random par1Random) {
      return 1;
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      if(!par1World.isRemote) {
         this.refreshTrackShape(par1World, par2, par3, par4, true);
         if(this.isPowered) {
            this.onNeighborBlockChange(par1World, par2, par3, par4, this.blockID);
         }
      }
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!par1World.isRemote) {
         int var6 = par1World.getBlockMetadata(par2, par3, par4);
         int var7 = var6;
         if(this.isPowered) {
            var7 = var6 & 7;
         }

         boolean var8 = false;
         if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
            var8 = true;
         }

         if(var7 == 2 && !par1World.doesBlockHaveSolidTopSurface(par2 + 1, par3, par4)) {
            var8 = true;
         }

         if(var7 == 3 && !par1World.doesBlockHaveSolidTopSurface(par2 - 1, par3, par4)) {
            var8 = true;
         }

         if(var7 == 4 && !par1World.doesBlockHaveSolidTopSurface(par2, par3, par4 - 1)) {
            var8 = true;
         }

         if(var7 == 5 && !par1World.doesBlockHaveSolidTopSurface(par2, par3, par4 + 1)) {
            var8 = true;
         }

         if(var8) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
         } else {
            this.func_94358_a(par1World, par2, par3, par4, var6, var7, par5);
         }
      }
   }

   protected void func_94358_a(World par1World, int par2, int par3, int par4, int par5, int par6, int par7) {}

   protected void refreshTrackShape(World par1World, int par2, int par3, int par4, boolean par5) {
      if(!par1World.isRemote) {
         (new BlockBaseRailLogic(this, par1World, par2, par3, par4)).func_94511_a(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4), par5);
      }
   }

   public int getMobilityFlag() {
      return 0;
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      int var7 = par6;
      if(this.isPowered) {
         var7 = par6 & 7;
      }

      super.breakBlock(par1World, par2, par3, par4, par5, par6);
      if(var7 == 2 || var7 == 3 || var7 == 4 || var7 == 5) {
         par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, par5);
      }

      if(this.isPowered) {
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par5);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, par5);
      }
   }
}
