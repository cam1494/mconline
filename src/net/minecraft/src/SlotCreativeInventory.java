package net.minecraft.src;

class SlotCreativeInventory extends Slot {
   private final Slot theSlot;

   final GuiContainerCreative theCreativeInventory;

   public SlotCreativeInventory(GuiContainerCreative par1GuiContainerCreative, Slot par2Slot, int par3) {
      super(par2Slot.inventory, par3, 0, 0);
      this.theCreativeInventory = par1GuiContainerCreative;
      this.theSlot = par2Slot;
   }

   public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
      this.theSlot.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
   }

   public boolean isItemValid(ItemStack par1ItemStack) {
      return this.theSlot.isItemValid(par1ItemStack);
   }

   public ItemStack getStack() {
      return this.theSlot.getStack();
   }

   public boolean getHasStack() {
      return this.theSlot.getHasStack();
   }

   public void putStack(ItemStack par1ItemStack) {
      this.theSlot.putStack(par1ItemStack);
   }

   public void onSlotChanged() {
      this.theSlot.onSlotChanged();
   }

   public int getSlotStackLimit() {
      return this.theSlot.getSlotStackLimit();
   }

   public Icon getBackgroundIconIndex() {
      return this.theSlot.getBackgroundIconIndex();
   }

   public ItemStack decrStackSize(int par1) {
      return this.theSlot.decrStackSize(par1);
   }

   public boolean isSlotInInventory(IInventory par1IInventory, int par2) {
      return this.theSlot.isSlotInInventory(par1IInventory, par2);
   }

   static Slot func_75240_a(SlotCreativeInventory par0SlotCreativeInventory) {
      return par0SlotCreativeInventory.theSlot;
   }
}
