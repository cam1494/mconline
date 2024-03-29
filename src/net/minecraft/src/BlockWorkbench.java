package net.minecraft.src;

public class BlockWorkbench extends Block {
   private Icon workbenchIconTop;
   private Icon workbenchIconFront;

   protected BlockWorkbench(int par1) {
      super(par1, Material.wood);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 1?this.workbenchIconTop:(par1 == 0?Block.planks.getBlockTextureFromSide(par1):(par1 != 2 && par1 != 4?this.blockIcon:this.workbenchIconFront));
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
      this.workbenchIconTop = par1IconRegister.registerIcon(this.getTextureName() + "_top");
      this.workbenchIconFront = par1IconRegister.registerIcon(this.getTextureName() + "_front");
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         par5EntityPlayer.displayGUIWorkbench(par2, par3, par4);
         return true;
      }
   }
}
