package net.minecraft.src;

import java.util.Random;

public abstract class BlockRedstoneLogic extends BlockDirectional {
   protected final boolean isRepeaterPowered;

   protected BlockRedstoneLogic(int par1, boolean par2) {
      super(par1, Material.circuits);
      this.isRepeaterPowered = par2;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
      return !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)?false:super.canPlaceBlockAt(par1World, par2, par3, par4);
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      return !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)?false:super.canBlockStay(par1World, par2, par3, par4);
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      if(!this.func_94476_e(par1World, par2, par3, par4, var6)) {
         boolean var7 = this.isGettingInput(par1World, par2, par3, par4, var6);
         if(this.isRepeaterPowered && !var7) {
            par1World.setBlock(par2, par3, par4, this.func_94484_i().blockID, var6, 2);
         } else if(!this.isRepeaterPowered) {
            par1World.setBlock(par2, par3, par4, this.func_94485_e().blockID, var6, 2);
            if(!var7) {
               par1World.scheduleBlockUpdateWithPriority(par2, par3, par4, this.func_94485_e().blockID, this.func_94486_g(var6), -1);
            }
         }
      }
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 0?(this.isRepeaterPowered?Block.torchRedstoneActive.getBlockTextureFromSide(par1):Block.torchRedstoneIdle.getBlockTextureFromSide(par1)):(par1 == 1?this.blockIcon:Block.stoneDoubleSlab.getBlockTextureFromSide(1));
   }

   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return par5 != 0 && par5 != 1;
   }

   public int getRenderType() {
      return 36;
   }

   protected boolean func_96470_c(int par1) {
      return this.isRepeaterPowered;
   }

   public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5);
   }

   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      if(!this.func_96470_c(var6)) {
         return 0;
      } else {
         int var7 = getDirection(var6);
         return var7 == 0 && par5 == 3?this.func_94480_d(par1IBlockAccess, par2, par3, par4, var6):(var7 == 1 && par5 == 4?this.func_94480_d(par1IBlockAccess, par2, par3, par4, var6):(var7 == 2 && par5 == 2?this.func_94480_d(par1IBlockAccess, par2, par3, par4, var6):(var7 == 3 && par5 == 5?this.func_94480_d(par1IBlockAccess, par2, par3, par4, var6):0)));
      }
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!this.canBlockStay(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlockToAir(par2, par3, par4);
         par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
      } else {
         this.func_94479_f(par1World, par2, par3, par4, par5);
      }
   }

   protected void func_94479_f(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      if(!this.func_94476_e(par1World, par2, par3, par4, var6)) {
         boolean var7 = this.isGettingInput(par1World, par2, par3, par4, var6);
         if((this.isRepeaterPowered && !var7 || !this.isRepeaterPowered && var7) && !par1World.isBlockTickScheduledThisTick(par2, par3, par4, this.blockID)) {
            byte var8 = -1;
            if(this.func_83011_d(par1World, par2, par3, par4, var6)) {
               var8 = -3;
            } else if(this.isRepeaterPowered) {
               var8 = -2;
            }

            par1World.scheduleBlockUpdateWithPriority(par2, par3, par4, this.blockID, this.func_94481_j_(var6), var8);
         }
      }
   }

   public boolean func_94476_e(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return false;
   }

   protected boolean isGettingInput(World par1World, int par2, int par3, int par4, int par5) {
      return this.getInputStrength(par1World, par2, par3, par4, par5) > 0;
   }

   protected int getInputStrength(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = getDirection(par5);
      int var7 = par2 + Direction.offsetX[var6];
      int var8 = par4 + Direction.offsetZ[var6];
      int var9 = par1World.getIndirectPowerLevelTo(var7, par3, var8, Direction.directionToFacing[var6]);
      return var9 >= 15?var9:Math.max(var9, par1World.getBlockId(var7, par3, var8) == Block.redstoneWire.blockID?par1World.getBlockMetadata(var7, par3, var8):0);
   }

   protected int func_94482_f(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      int var6 = getDirection(par5);
      switch(var6) {
      case 0:
      case 2:
         return Math.max(this.func_94488_g(par1IBlockAccess, par2 - 1, par3, par4, 4), this.func_94488_g(par1IBlockAccess, par2 + 1, par3, par4, 5));
      case 1:
      case 3:
         return Math.max(this.func_94488_g(par1IBlockAccess, par2, par3, par4 + 1, 3), this.func_94488_g(par1IBlockAccess, par2, par3, par4 - 1, 2));
      default:
         return 0;
      }
   }

   protected int func_94488_g(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
      return this.func_94477_d(var6)?(var6 == Block.redstoneWire.blockID?par1IBlockAccess.getBlockMetadata(par2, par3, par4):par1IBlockAccess.isBlockProvidingPowerTo(par2, par3, par4, par5)):0;
   }

   public boolean canProvidePower() {
      return true;
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int var7 = ((MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 3);
      boolean var8 = this.isGettingInput(par1World, par2, par3, par4, var7);
      if(var8) {
         par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 1);
      }
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      this.func_94483_i_(par1World, par2, par3, par4);
   }

   protected void func_94483_i_(World par1World, int par2, int par3, int par4) {
      int var5 = getDirection(par1World.getBlockMetadata(par2, par3, par4));
      if(var5 == 1) {
         par1World.notifyBlockOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID, 4);
      }

      if(var5 == 3) {
         par1World.notifyBlockOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID, 5);
      }

      if(var5 == 2) {
         par1World.notifyBlockOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID, 2);
      }

      if(var5 == 0) {
         par1World.notifyBlockOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID, 3);
      }
   }

   public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
      if(this.isRepeaterPowered) {
         par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
         par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
      }

      super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   protected boolean func_94477_d(int par1) {
      Block var2 = Block.blocksList[par1];
      return var2 != null && var2.canProvidePower();
   }

   protected int func_94480_d(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return 15;
   }

   public static boolean isRedstoneRepeaterBlockID(int par0) {
      return Block.redstoneRepeaterIdle.func_94487_f(par0) || Block.redstoneComparatorIdle.func_94487_f(par0);
   }

   public boolean func_94487_f(int par1) {
      return par1 == this.func_94485_e().blockID || par1 == this.func_94484_i().blockID;
   }

   public boolean func_83011_d(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = getDirection(par5);
      if(isRedstoneRepeaterBlockID(par1World.getBlockId(par2 - Direction.offsetX[var6], par3, par4 - Direction.offsetZ[var6]))) {
         int var7 = par1World.getBlockMetadata(par2 - Direction.offsetX[var6], par3, par4 - Direction.offsetZ[var6]);
         int var8 = getDirection(var7);
         return var8 != var6;
      } else {
         return false;
      }
   }

   protected int func_94486_g(int par1) {
      return this.func_94481_j_(par1);
   }

   protected abstract int func_94481_j_(int var1);

   protected abstract BlockRedstoneLogic func_94485_e();

   protected abstract BlockRedstoneLogic func_94484_i();

   public boolean isAssociatedBlockID(int par1) {
      return this.func_94487_f(par1);
   }
}
