package net.minecraft.src;

import java.util.Random;

public class BlockFarmland extends Block {
   private Icon field_94441_a;
   private Icon field_94440_b;

   protected BlockFarmland(int par1) {
      super(par1, Material.ground);
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.setLightOpacity(255);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return AxisAlignedBB.getAABBPool().getAABB((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 0), (double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 1?(par2 > 0?this.field_94441_a:this.field_94440_b):Block.dirt.getBlockTextureFromSide(par1);
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if(!this.isWaterNearby(par1World, par2, par3, par4) && !par1World.canLightningStrikeAt(par2, par3 + 1, par4)) {
         int var6 = par1World.getBlockMetadata(par2, par3, par4);
         if(var6 > 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 - 1, 2);
         } else if(!this.isCropsNearby(par1World, par2, par3, par4)) {
            par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
         }
      } else {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 7, 2);
      }
   }

   public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6) {
      if(!par1World.isRemote && par1World.rand.nextFloat() < par6 - 0.5F) {
         if(!(par5Entity instanceof EntityPlayer) && !par1World.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            return;
         }

         par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
      }
   }

   private boolean isCropsNearby(World par1World, int par2, int par3, int par4) {
      byte var5 = 0;

      for(int var6 = par2 - var5; var6 <= par2 + var5; ++var6) {
         for(int var7 = par4 - var5; var7 <= par4 + var5; ++var7) {
            int var8 = par1World.getBlockId(var6, par3 + 1, var7);
            if(var8 == Block.crops.blockID || var8 == Block.melonStem.blockID || var8 == Block.pumpkinStem.blockID || var8 == Block.potato.blockID || var8 == Block.carrot.blockID) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isWaterNearby(World par1World, int par2, int par3, int par4) {
      for(int var5 = par2 - 4; var5 <= par2 + 4; ++var5) {
         for(int var6 = par3; var6 <= par3 + 1; ++var6) {
            for(int var7 = par4 - 4; var7 <= par4 + 4; ++var7) {
               if(par1World.getBlockMaterial(var5, var6, var7) == Material.water) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
      Material var6 = par1World.getBlockMaterial(par2, par3 + 1, par4);
      if(var6.isSolid()) {
         par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
      }
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.dirt.idDropped(0, par2Random, par3);
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Block.dirt.blockID;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.field_94441_a = par1IconRegister.registerIcon(this.getTextureName() + "_wet");
      this.field_94440_b = par1IconRegister.registerIcon(this.getTextureName() + "_dry");
   }
}
