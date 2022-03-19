package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet5PlayerInventory extends Packet {
   public int entityID;
   public int slot;
   private ItemStack itemSlot;

   public Packet5PlayerInventory() {}

   public Packet5PlayerInventory(int par1, int par2, ItemStack par3ItemStack) {
      this.entityID = par1;
      this.slot = par2;
      this.itemSlot = par3ItemStack == null?null:par3ItemStack.copy();
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityID = par1DataInput.readInt();
      this.slot = par1DataInput.readShort();
      this.itemSlot = readItemStack(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityID);
      par1DataOutput.writeShort(this.slot);
      writeItemStack(this.itemSlot, par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handlePlayerInventory(this);
   }

   public int getPacketSize() {
      return 8;
   }

   public ItemStack getItemSlot() {
      return this.itemSlot;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet5PlayerInventory var2 = (Packet5PlayerInventory)par1Packet;
      return var2.entityID == this.entityID && var2.slot == this.slot;
   }
}
