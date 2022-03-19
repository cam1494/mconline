package net.minecraft.src;

class ContainerRepairINNER1 extends InventoryBasic {
   final ContainerRepair repairContainer;

   ContainerRepairINNER1(ContainerRepair par1ContainerRepair, String par2Str, boolean par3, int par4) {
      super(par2Str, par3, par4);
      this.repairContainer = par1ContainerRepair;
   }

   public void onInventoryChanged() {
      super.onInventoryChanged();
      this.repairContainer.onCraftMatrixChanged(this);
   }

   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
      return true;
   }
}
