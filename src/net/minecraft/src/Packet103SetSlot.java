package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet103SetSlot extends Packet {
   public int windowId;
   public int itemSlot;
   public ItemStack myItemStack;

   public Packet103SetSlot() {}

   public Packet103SetSlot(int par1, int par2, ItemStack par3ItemStack) {
      this.windowId = par1;
      this.itemSlot = par2;
      this.myItemStack = par3ItemStack == null?par3ItemStack:par3ItemStack.copy();
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleSetSlot(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.windowId = par1DataInput.readByte();
      this.itemSlot = par1DataInput.readShort();
      this.myItemStack = readItemStack(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.windowId);
      par1DataOutput.writeShort(this.itemSlot);
      writeItemStack(this.myItemStack, par1DataOutput);
   }

   public int getPacketSize() {
      return 8;
   }
}
