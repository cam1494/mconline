package net.minecraft.src;

class SlotEnchantmentTable extends InventoryBasic {
   final ContainerEnchantment container;

   SlotEnchantmentTable(ContainerEnchantment par1ContainerEnchantment, String par2Str, boolean par3, int par4) {
      super(par2Str, par3, par4);
      this.container = par1ContainerEnchantment;
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public void onInventoryChanged() {
      super.onInventoryChanged();
      this.container.onCraftMatrixChanged(this);
   }

   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
      return true;
   }
}
