package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntity implements IInventory {
   private ItemStack[] chestContents = new ItemStack[36];
   public boolean adjacentChestChecked;
   public TileEntityChest adjacentChestZNeg;
   public TileEntityChest adjacentChestXPos;
   public TileEntityChest adjacentChestXNeg;
   public TileEntityChest adjacentChestZPosition;
   public float lidAngle;
   public float prevLidAngle;
   public int numUsingPlayers;
   private int ticksSinceSync;
   private int cachedChestType;
   private String customName;

   public TileEntityChest() {
      this.cachedChestType = -1;
   }

   public TileEntityChest(int par1) {
      this.cachedChestType = par1;
   }

   public int getSizeInventory() {
      return 27;
   }

   public ItemStack getStackInSlot(int par1) {
      return this.chestContents[par1];
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.chestContents[par1] != null) {
         ItemStack var3;
         if(this.chestContents[par1].stackSize <= par2) {
            var3 = this.chestContents[par1];
            this.chestContents[par1] = null;
            this.onInventoryChanged();
            return var3;
         } else {
            var3 = this.chestContents[par1].splitStack(par2);
            if(this.chestContents[par1].stackSize == 0) {
               this.chestContents[par1] = null;
            }

            this.onInventoryChanged();
            return var3;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int par1) {
      if(this.chestContents[par1] != null) {
         ItemStack var2 = this.chestContents[par1];
         this.chestContents[par1] = null;
         return var2;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      this.chestContents[par1] = par2ItemStack;
      if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
         par2ItemStack.stackSize = this.getInventoryStackLimit();
      }

      this.onInventoryChanged();
   }

   public String getInvName() {
      return this.isInvNameLocalized()?this.customName:"container.chest";
   }

   public boolean isInvNameLocalized() {
      return this.customName != null && this.customName.length() > 0;
   }

   public void setChestGuiName(String par1Str) {
      this.customName = par1Str;
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
      this.chestContents = new ItemStack[this.getSizeInventory()];
      if(par1NBTTagCompound.hasKey("CustomName")) {
         this.customName = par1NBTTagCompound.getString("CustomName");
      }

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
         int var5 = var4.getByte("Slot") & 255;
         if(var5 >= 0 && var5 < this.chestContents.length) {
            this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
         }
      }
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.chestContents.length; ++var3) {
         if(this.chestContents[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            this.chestContents[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      par1NBTTagCompound.setTag("Items", var2);
      if(this.isInvNameLocalized()) {
         par1NBTTagCompound.setString("CustomName", this.customName);
      }
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
      return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this?false:par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
   }

   public void updateContainingBlockInfo() {
      super.updateContainingBlockInfo();
      this.adjacentChestChecked = false;
   }

   private void func_90009_a(TileEntityChest par1TileEntityChest, int par2) {
      if(par1TileEntityChest.isInvalid()) {
         this.adjacentChestChecked = false;
      } else if(this.adjacentChestChecked) {
         switch(par2) {
         case 0:
            if(this.adjacentChestZPosition != par1TileEntityChest) {
               this.adjacentChestChecked = false;
            }
            break;
         case 1:
            if(this.adjacentChestXNeg != par1TileEntityChest) {
               this.adjacentChestChecked = false;
            }
            break;
         case 2:
            if(this.adjacentChestZNeg != par1TileEntityChest) {
               this.adjacentChestChecked = false;
            }
            break;
         case 3:
            if(this.adjacentChestXPos != par1TileEntityChest) {
               this.adjacentChestChecked = false;
            }
         }
      }
   }

   public void checkForAdjacentChests() {
      if(!this.adjacentChestChecked) {
         this.adjacentChestChecked = true;
         this.adjacentChestZNeg = null;
         this.adjacentChestXPos = null;
         this.adjacentChestXNeg = null;
         this.adjacentChestZPosition = null;
         if(this.func_94044_a(this.xCoord - 1, this.yCoord, this.zCoord)) {
            this.adjacentChestXNeg = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
         }

         if(this.func_94044_a(this.xCoord + 1, this.yCoord, this.zCoord)) {
            this.adjacentChestXPos = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
         }

         if(this.func_94044_a(this.xCoord, this.yCoord, this.zCoord - 1)) {
            this.adjacentChestZNeg = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
         }

         if(this.func_94044_a(this.xCoord, this.yCoord, this.zCoord + 1)) {
            this.adjacentChestZPosition = (TileEntityChest)this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
         }

         if(this.adjacentChestZNeg != null) {
            this.adjacentChestZNeg.func_90009_a(this, 0);
         }

         if(this.adjacentChestZPosition != null) {
            this.adjacentChestZPosition.func_90009_a(this, 2);
         }

         if(this.adjacentChestXPos != null) {
            this.adjacentChestXPos.func_90009_a(this, 1);
         }

         if(this.adjacentChestXNeg != null) {
            this.adjacentChestXNeg.func_90009_a(this, 3);
         }
      }
   }

   private boolean func_94044_a(int par1, int par2, int par3) {
      Block var4 = Block.blocksList[this.worldObj.getBlockId(par1, par2, par3)];
      return var4 != null && var4 instanceof BlockChest?((BlockChest)var4).chestType == this.getChestType():false;
   }

   public void updateEntity() {
      super.updateEntity();
      this.checkForAdjacentChests();
      ++this.ticksSinceSync;
      float var1;
      if(!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
         this.numUsingPlayers = 0;
         var1 = 5.0F;
         List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double)((float)this.xCoord - var1), (double)((float)this.yCoord - var1), (double)((float)this.zCoord - var1), (double)((float)(this.xCoord + 1) + var1), (double)((float)(this.yCoord + 1) + var1), (double)((float)(this.zCoord + 1) + var1)));
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            EntityPlayer var4 = (EntityPlayer)var3.next();
            if(var4.openContainer instanceof ContainerChest) {
               IInventory var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory();
               if(var5 == this || var5 instanceof InventoryLargeChest && ((InventoryLargeChest)var5).isPartOfLargeChest(this)) {
                  ++this.numUsingPlayers;
               }
            }
         }
      }

      this.prevLidAngle = this.lidAngle;
      var1 = 0.1F;
      double var11;
      if(this.numUsingPlayers > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
         double var8 = (double)this.xCoord + 0.5D;
         var11 = (double)this.zCoord + 0.5D;
         if(this.adjacentChestZPosition != null) {
            var11 += 0.5D;
         }

         if(this.adjacentChestXPos != null) {
            var8 += 0.5D;
         }

         this.worldObj.playSoundEffect(var8, (double)this.yCoord + 0.5D, var11, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if(this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F) {
         float var9 = this.lidAngle;
         if(this.numUsingPlayers > 0) {
            this.lidAngle += var1;
         } else {
            this.lidAngle -= var1;
         }

         if(this.lidAngle > 1.0F) {
            this.lidAngle = 1.0F;
         }

         float var10 = 0.5F;
         if(this.lidAngle < var10 && var9 >= var10 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            var11 = (double)this.xCoord + 0.5D;
            double var6 = (double)this.zCoord + 0.5D;
            if(this.adjacentChestZPosition != null) {
               var6 += 0.5D;
            }

            if(this.adjacentChestXPos != null) {
               var11 += 0.5D;
            }

            this.worldObj.playSoundEffect(var11, (double)this.yCoord + 0.5D, var6, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
         }

         if(this.lidAngle < 0.0F) {
            this.lidAngle = 0.0F;
         }
      }
   }

   public boolean receiveClientEvent(int par1, int par2) {
      if(par1 == 1) {
         this.numUsingPlayers = par2;
         return true;
      } else {
         return super.receiveClientEvent(par1, par2);
      }
   }

   public void openChest() {
      if(this.numUsingPlayers < 0) {
         this.numUsingPlayers = 0;
      }

      ++this.numUsingPlayers;
      this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
      this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
      this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
   }

   public void closeChest() {
      if(this.getBlockType() != null && this.getBlockType() instanceof BlockChest) {
         --this.numUsingPlayers;
         this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
         this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
         this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
      }
   }

   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
      return true;
   }

   public void invalidate() {
      super.invalidate();
      this.updateContainingBlockInfo();
      this.checkForAdjacentChests();
   }

   public int getChestType() {
      if(this.cachedChestType == -1) {
         if(this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
            return 0;
         }

         this.cachedChestType = ((BlockChest)this.getBlockType()).chestType;
      }

      return this.cachedChestType;
   }
}
