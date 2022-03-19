package net.minecraft.src;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block {
   private String pressurePlateIconName;

   protected BlockBasePressurePlate(int par1, String par2Str, Material par3Material) {
      super(par1, par3Material);
      this.pressurePlateIconName = par2Str;
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setTickRandomly(true);
      this.func_94353_c_(this.getMetaFromWeight(15));
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      this.func_94353_c_(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
   }

   protected void func_94353_c_(int par1) {
      boolean var2 = this.getPowerSupply(par1) > 0;
      float var3 = 0.0625F;
      if(var2) {
         this.setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.03125F, 1.0F - var3);
      } else {
         this.setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.0625F, 1.0F - var3);
      }
   }

   public int tickRate(World par1World) {
      return 20;
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

   public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      return true;
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || BlockFence.isIdAFence(par1World.getBlockId(par2, par3 - 1, par4));
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      boolean var6 = false;
      if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !BlockFence.isIdAFence(par1World.getBlockId(par2, par3 - 1, par4))) {
         var6 = true;
      }

      if(var6) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
      }
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if(!par1World.isRemote) {
         int var6 = this.getPowerSupply(par1World.getBlockMetadata(par2, par3, par4));
         if(var6 > 0) {
            this.setStateIfMobInteractsWithPlate(par1World, par2, par3, par4, var6);
         }
      }
   }

   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
      if(!par1World.isRemote) {
         int var6 = this.getPowerSupply(par1World.getBlockMetadata(par2, par3, par4));
         if(var6 == 0) {
            this.setStateIfMobInteractsWithPlate(par1World, par2, par3, par4, var6);
         }
      }
   }

   protected void setStateIfMobInteractsWithPlate(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = this.getPlateState(par1World, par2, par3, par4);
      boolean var7 = par5 > 0;
      boolean var8 = var6 > 0;
      if(par5 != var6) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, this.getMetaFromWeight(var6), 2);
         this.func_94354_b_(par1World, par2, par3, par4);
         par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
      }

      if(!var8 && var7) {
         par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.1D, (double)par4 + 0.5D, "random.click", 0.3F, 0.5F);
      } else if(var8 && !var7) {
         par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.1D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
      }

      if(var8) {
         par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
      }
   }

   protected AxisAlignedBB getSensitiveAABB(int par1, int par2, int par3) {
      float var4 = 0.125F;
      return AxisAlignedBB.getAABBPool().getAABB((double)((float)par1 + var4), (double)par2, (double)((float)par3 + var4), (double)((float)(par1 + 1) - var4), (double)par2 + 0.25D, (double)((float)(par3 + 1) - var4));
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      if(this.getPowerSupply(par6) > 0) {
         this.func_94354_b_(par1World, par2, par3, par4);
      }

      super.breakBlock(par1World, par2, par3, par4, par5, par6);
   }

   protected void func_94354_b_(World par1World, int par2, int par3, int par4) {
      par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
      par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
   }

   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return this.getPowerSupply(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
   }

   public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return par5 == 1?this.getPowerSupply(par1IBlockAccess.getBlockMetadata(par2, par3, par4)):0;
   }

   public boolean canProvidePower() {
      return true;
   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.5F;
      float var2 = 0.125F;
      float var3 = 0.5F;
      this.setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
   }

   public int getMobilityFlag() {
      return 1;
   }

   protected abstract int getPlateState(World var1, int var2, int var3, int var4);

   protected abstract int getPowerSupply(int var1);

   protected abstract int getMetaFromWeight(int var1);

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon(this.pressurePlateIconName);
   }
}
