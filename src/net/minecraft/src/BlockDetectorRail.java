package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockDetectorRail extends BlockRailBase {
   private Icon[] iconArray;

   public BlockDetectorRail(int par1) {
      super(par1, true);
      this.setTickRandomly(true);
   }

   public int tickRate(World par1World) {
      return 20;
   }

   public boolean canProvidePower() {
      return true;
   }

   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      if(!par1World.isRemote) {
         int var6 = par1World.getBlockMetadata(par2, par3, par4);
         if((var6 & 8) == 0) {
            this.setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, var6);
         }
      }
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if(!par1World.isRemote) {
         int var6 = par1World.getBlockMetadata(par2, par3, par4);
         if((var6 & 8) != 0) {
            this.setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, var6);
         }
      }
   }

   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0?15:0;
   }

   public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0?0:(par5 == 1?15:0);
   }

   private void setStateIfMinecartInteractsWithRail(World par1World, int par2, int par3, int par4, int par5) {
      boolean var6 = (par5 & 8) != 0;
      boolean var7 = false;
      float var8 = 0.125F;
      List var9 = par1World.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + var8), (double)par3, (double)((float)par4 + var8), (double)((float)(par2 + 1) - var8), (double)((float)(par3 + 1) - var8), (double)((float)(par4 + 1) - var8)));
      if(!var9.isEmpty()) {
         var7 = true;
      }

      if(var7 && !var6) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 8, 3);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
      }

      if(!var7 && var6) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 & 7, 3);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
      }

      if(var7) {
         par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
      }

      par1World.func_96440_m(par2, par3, par4, this.blockID);
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      super.onBlockAdded(par1World, par2, par3, par4);
      this.setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
      if((par1World.getBlockMetadata(par2, par3, par4) & 8) > 0) {
         float var6 = 0.125F;
         List var7 = par1World.selectEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB((double)((float)par2 + var6), (double)par3, (double)((float)par4 + var6), (double)((float)(par2 + 1) - var6), (double)((float)(par3 + 1) - var6), (double)((float)(par4 + 1) - var6)), IEntitySelector.selectInventories);
         if(var7.size() > 0) {
            return Container.calcRedstoneFromInventory((IInventory)var7.get(0));
         }
      }

      return 0;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[2];
      this.iconArray[0] = par1IconRegister.registerIcon(this.getTextureName());
      this.iconArray[1] = par1IconRegister.registerIcon(this.getTextureName() + "_powered");
   }

   public Icon getIcon(int par1, int par2) {
      return (par2 & 8) != 0?this.iconArray[1]:this.iconArray[0];
   }
}
