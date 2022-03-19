package net.minecraft.src;

import java.util.List;

public class BlockWood extends Block {
   public static final String[] woodType = new String[]{"oak", "spruce", "birch", "jungle"};
   private Icon[] iconArray;

   public BlockWood(int par1) {
      super(par1, Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public Icon getIcon(int par1, int par2) {
      if(par2 < 0 || par2 >= this.iconArray.length) {
         par2 = 0;
      }

      return this.iconArray[par2];
   }

   public int damageDropped(int par1) {
      return par1;
   }

   public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
      par3List.add(new ItemStack(par1, 1, 3));
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[woodType.length];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType[var2]);
      }
   }
}
