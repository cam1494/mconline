package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet107CreativeSetSlot extends Packet {
   public int slot;
   public ItemStack itemStack;

   public Packet107CreativeSetSlot() {}

   public Packet107CreativeSetSlot(int par1, ItemStack par2ItemStack) {
      this.slot = par1;
      this.itemStack = par2ItemStack != null?par2ItemStack.copy():null;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleCreativeSetSlot(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.slot = par1DataInput.readShort();
      this.itemStack = readItemStack(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeShort(this.slot);
      writeItemStack(this.itemStack, par1DataOutput);
   }

   public int getPacketSize() {
      return 8;
   }
}
