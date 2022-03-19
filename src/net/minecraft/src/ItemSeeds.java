package net.minecraft.src;

public class ItemSeeds extends Item {
   private int blockType;
   private int soilBlockID;

   public ItemSeeds(int par1, int par2, int par3) {
      super(par1);
      this.blockType = par2;
      this.soilBlockID = par3;
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par7 != 1) {
         return false;
      } else if(par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
         int var11 = par3World.getBlockId(par4, par5, par6);
         if(var11 == this.soilBlockID && par3World.isAirBlock(par4, par5 + 1, par6)) {
            par3World.setBlock(par4, par5 + 1, par6, this.blockType);
            --par1ItemStack.stackSize;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
