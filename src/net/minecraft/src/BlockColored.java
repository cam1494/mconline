package net.minecraft.src;

import java.util.List;

public class BlockColored extends Block {
   private Icon[] iconArray;

   public BlockColored(int par1, Material par2Material) {
      super(par1, par2Material);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public Icon getIcon(int par1, int par2) {
      return this.iconArray[par2 % this.iconArray.length];
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public static int getBlockFromDye(int par0) {
      return ~par0 & 15;
   }

   public static int getDyeFromBlock(int par0) {
      return ~par0 & 15;
   }

   public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int var4 = 0; var4 < 16; ++var4) {
         par3List.add(new ItemStack(par1, 1, var4));
      }
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[16];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_" + ItemDye.dyeItemNames[getDyeFromBlock(var2)]);
      }
   }
}
