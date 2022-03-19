package net.minecraft.src;

import java.util.Random;

public class BlockNetherStalk extends BlockFlower {
   private Icon[] iconArray;

   protected BlockNetherStalk(int par1) {
      super(par1);
      this.setTickRandomly(true);
      float var2 = 0.5F;
      this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
      this.setCreativeTab((CreativeTabs)null);
   }

   protected boolean canThisPlantGrowOnThisBlockID(int par1) {
      return par1 == Block.slowSand.blockID;
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      return this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      if(var6 < 3 && par5Random.nextInt(10) == 0) {
         ++var6;
         par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
      }

      super.updateTick(par1World, par2, par3, par4, par5Random);
   }

   public Icon getIcon(int par1, int par2) {
      return par2 >= 3?this.iconArray[2]:(par2 > 0?this.iconArray[1]:this.iconArray[0]);
   }

   public int getRenderType() {
      return 6;
   }

   public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
      if(!par1World.isRemote) {
         int var8 = 1;
         if(par5 >= 3) {
            var8 = 2 + par1World.rand.nextInt(3);
            if(par7 > 0) {
               var8 += par1World.rand.nextInt(par7 + 1);
            }
         }

         for(int var9 = 0; var9 < var8; ++var9) {
            this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.netherStalkSeeds));
         }
      }
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return 0;
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Item.netherStalkSeeds.itemID;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[3];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + var2);
      }
   }
}
