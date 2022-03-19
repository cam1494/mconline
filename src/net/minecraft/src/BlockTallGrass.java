package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockTallGrass extends BlockFlower {
   private static final String[] grassTypes = new String[]{"deadbush", "tallgrass", "fern"};
   private Icon[] iconArray;

   protected BlockTallGrass(int par1) {
      super(par1, Material.vine);
      float var2 = 0.4F;
      this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.8F, 0.5F + var2);
   }

   public Icon getIcon(int par1, int par2) {
      if(par2 >= this.iconArray.length) {
         par2 = 0;
      }

      return this.iconArray[par2];
   }

   public int getBlockColor() {
      double var1 = 0.5D;
      double var3 = 1.0D;
      return ColorizerGrass.getGrassColor(var1, var3);
   }

   public int getRenderColor(int par1) {
      return par1 == 0?16777215:ColorizerFoliage.getFoliageColorBasic();
   }

   public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      return var5 == 0?16777215:par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return par2Random.nextInt(8) == 0?Item.seeds.itemID:-1;
   }

   public int quantityDroppedWithBonus(int par1, Random par2Random) {
      return 1 + par2Random.nextInt(par1 * 2 + 1);
   }

   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
      if(!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID) {
         par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
         this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.tallGrass, 1, par6));
      } else {
         super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
      }
   }

   public int getDamageValue(World par1World, int par2, int par3, int par4) {
      return par1World.getBlockMetadata(par2, par3, par4);
   }

   public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int var4 = 1; var4 < 3; ++var4) {
         par3List.add(new ItemStack(par1, 1, var4));
      }
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[grassTypes.length];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(grassTypes[var2]);
      }
   }
}
