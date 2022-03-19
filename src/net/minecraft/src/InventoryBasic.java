package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class InventoryBasic implements IInventory {
   private String inventoryTitle;
   private int slotsCount;
   private ItemStack[] inventoryContents;
   private List field_70480_d;
   private boolean field_94051_e;

   public InventoryBasic(String par1Str, boolean par2, int par3) {
      this.inventoryTitle = par1Str;
      this.field_94051_e = par2;
      this.slotsCount = par3;
      this.inventoryContents = new ItemStack[par3];
   }

   public void func_110134_a(IInvBasic par1IInvBasic) {
      if(this.field_70480_d == null) {
         this.field_70480_d = new ArrayList();
      }

      this.field_70480_d.add(par1IInvBasic);
   }

   public void func_110132_b(IInvBasic par1IInvBasic) {
      this.field_70480_d.remove(par1IInvBasic);
   }

   public ItemStack getStackInSlot(int par1) {
      return this.inventoryContents[par1];
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.inventoryContents[par1] != null) {
         ItemStack var3;
         if(this.inventoryContents[par1].stackSize <= par2) {
            var3 = this.inventoryContents[par1];
            this.inventoryContents[par1] = null;
            this.onInventoryChanged();
            return var3;
         } else {
            var3 = this.inventoryContents[par1].splitStack(par2);
            if(this.inventoryContents[par1].stackSize == 0) {
               this.inventoryContents[par1] = null;
            }

            this.onInventoryChanged();
            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int par1) {
      if(this.inventoryContents[par1] != null) {
         ItemStack var2 = this.inventoryContents[par1];
         this.inventoryContents[par1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      this.inventoryContents[par1] = par2ItemStack;
      if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
         par2ItemStack.stackSize = this.getInventoryStackLimit();
      }

      this.onInventoryChanged();
   }

   public int getSizeInventory() {
      return this.slotsCount;
   }

   public String getInvName() {
      return this.inventoryTitle;
   }

   public boolean isInvNameLocalized() {
      return this.field_94051_e;
   }

   public void func_110133_a(String par1Str) {
      this.field_94051_e = true;
      this.inventoryTitle = par1Str;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void onInventoryChanged() {
      if(this.field_70480_d != null) {
         for(int var1 = 0; var1 < this.field_70480_d.size(); ++var1) {
            ((IInvBasic)this.field_70480_d.get(var1)).onInventoryChanged(this);
         }
      }
   }

   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
      return true;
   }

   public void openChest() {}

   public void closeChest() {}

   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
      return true;
   }
}
