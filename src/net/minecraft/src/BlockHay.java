package net.minecraft.src;

public class BlockHay extends BlockRotatedPillar {
   public BlockHay(int par1) {
      super(par1, Material.grass);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int getRenderType() {
      return 31;
   }

   protected Icon getSideIcon(int par1) {
      return this.blockIcon;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.field_111051_a = par1IconRegister.registerIcon(this.getTextureName() + "_top");
      this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
   }
}
