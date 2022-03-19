package net.minecraft.src;

public class TileEntityFurnace extends TileEntity implements ISidedInventory {
   private static final int[] slots_top = new int[]{0};
   private static final int[] slots_bottom = new int[]{2, 1};
   private static final int[] slots_sides = new int[]{1};
   private ItemStack[] furnaceItemStacks = new ItemStack[3];
   public int furnaceBurnTime;
   public int currentItemBurnTime;
   public int furnaceCookTime;
   private String field_94130_e;

   public int getSizeInventory() {
      return this.furnaceItemStacks.length;
   }

   public ItemStack getStackInSlot(int par1) {
      return this.furnaceItemStacks[par1];
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.furnaceItemStacks[par1] != null) {
         ItemStack var3;
         if(this.furnaceItemStacks[par1].stackSize <= par2) {
            var3 = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return var3;
         } else {
            var3 = this.furnaceItemStacks[par1].splitStack(par2);
            if(this.furnaceItemStacks[par1].stackSize == 0) {
               this.furnaceItemStacks[par1] = null;
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int par1) {
      if(this.furnaceItemStacks[par1] != null) {
         ItemStack var2 = this.furnaceItemStacks[par1];
         this.furnaceItemStacks[par1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      this.furnaceItemStacks[par1] = par2ItemStack;
      if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
         par2ItemStack.stackSize = this.getInventoryStackLimit();
      }
   }

   public String getInvName() {
      return this.isInvNameLocalized()?this.field_94130_e:"container.furnace";
   }

   public boolean isInvNameLocalized() {
      return this.field_94130_e != null && this.field_94130_e.length() > 0;
   }

   public void setGuiDisplayName(String par1Str) {
      this.field_94130_e = par1Str;
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
      this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
         byte var5 = var4.getByte("Slot");
         if(var5 >= 0 && var5 < this.furnaceItemStacks.length) {
            this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
         }
      }

      this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
      this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
      this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
      if(par1NBTTagCompound.hasKey("CustomName")) {
         this.field_94130_e = par1NBTTagCompound.getString("CustomName");
      }
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
      par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.furnaceItemStacks.length; ++var3) {
         if(this.furnaceItemStacks[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.furnaceItemStacks[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      par1NBTTagCompound.setTag("Items", var2);
      if(this.isInvNameLocalized()) {
         par1NBTTagCompound.setString("CustomName", this.field_94130_e);
      }
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public int getCookProgressScaled(int par1) {
      return this.furnaceCookTime * par1 / 200;
   }

   public int getBurnTimeRemainingScaled(int par1) {
      if(this.currentItemBurnTime == 0) {
         this.currentItemBurnTime = 200;
      }

      return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
   }

   public boolean isBurning() {
      return this.furnaceBurnTime > 0;
   }

   public void updateEntity() {
      boolean var1 = this.furnaceBurnTime > 0;
      boolean var2 = false;
      if(this.furnaceBurnTime > 0) {
         --this.furnaceBurnTime;
      }

      if(!this.worldObj.isRemote) {
         if(this.furnaceBurnTime == 0 && this.canSmelt()) {
            this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
            if(this.furnaceBurnTime > 0) {
               var2 = true;
               if(this.furnaceItemStacks[1] != null) {
                  --this.furnaceItemStacks[1].stackSize;
                  if(this.furnaceItemStacks[1].stackSize == 0) {
                     Item var3 = this.furnaceItemStacks[1].getItem().getContainerItem();
                     this.furnaceItemStacks[1] = var3 != null?new ItemStack(var3):null;
                  }
               }
            }
         }

         if(this.isBurning() && this.canSmelt()) {
            ++this.furnaceCookTime;
            if(this.furnaceCookTime == 200) {
               this.furnaceCookTime = 0;
               this.smeltItem();
               var2 = true;
            }
         } else {
            this.furnaceCookTime = 0;
         }

         if(var1 != this.furnaceBurnTime > 0) {
            var2 = true;
            BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
         }
      }

      if(var2) {
         this.onInventoryChanged();
      }
   }

   private boolean canSmelt() {
      if(this.furnaceItemStacks[0] == null) {
         return false;
      } else {
         ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0].getItem().itemID);
         return var1 == null?false:(this.furnaceItemStacks[2] == null?true:(!this.furnaceItemStacks[2].isItemEqual(var1)?false:(this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize()?true:this.furnaceItemStacks[2].stackSize < var1.getMaxStackSize())));
      }
   }

   public void smeltItem() {
      if(this.canSmelt()) {
         ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0].getItem().itemID);
         if(this.furnaceItemStacks[2] == null) {
            this.furnaceItemStacks[2] = var1.copy();
         } else if(this.furnaceItemStacks[2].itemID == var1.itemID) {
            ++this.furnaceItemStacks[2].stackSize;
         }

         --this.furnaceItemStacks[0].stackSize;
         if(this.furnaceItemStacks[0].stackSize <= 0) {
            this.furnaceItemStacks[0] = null;
         }
      }
   }

   public static int getItemBurnTime(ItemStack par0ItemStack) {
      if(par0ItemStack == null) {
         return 0;
      } else {
         int var1 = par0ItemStack.getItem().itemID;
         Item var2 = par0ItemStack.getItem();
         if(var1 < 256 && Block.blocksList[var1] != null) {
            Block var3 = Block.blocksList[var1];
            if(var3 == Block.woodSingleSlab) {
               return 150;
            }

            if(var3.blockMaterial == Material.wood) {
               return 300;
            }

            if(var3 == Block.coalBlock) {
               return 16000;
            }
         }

         return var2 instanceof ItemTool && ((ItemTool)var2).getToolMaterialName().equals("WOOD")?200:(var2 instanceof ItemSword && ((ItemSword)var2).getToolMaterialName().equals("WOOD")?200:(var2 instanceof ItemHoe && ((ItemHoe)var2).getMaterialName().equals("WOOD")?200:(var1 == Item.stick.itemID?100:(var1 == Item.coal.itemID?1600:(var1 == Item.bucketLava.itemID?20000:(var1 == Block.sapling.blockID?100:(var1 == Item.blazeRod.itemID?2400:0)))))));
      }
   }

   public static boolean isItemFuel(ItemStack par0ItemStack) {
      return getItemBurnTime(par0ItemStack) > 0;
   }

   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
      return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this?false:par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
   }

   public void openChest() {}

   public void closeChest() {}

   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
      return par1 == 2?false:(par1 == 1?isItemFuel(par2ItemStack):true);
   }

   public int[] getAccessibleSlotsFromSide(int par1) {
      return par1 == 0?slots_bottom:(par1 == 1?slots_top:slots_sides);
   }

   public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
      return this.isItemValidForSlot(par1, par2ItemStack);
   }

   public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
      return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
   }
}
