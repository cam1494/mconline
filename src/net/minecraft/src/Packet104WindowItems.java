package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class Packet104WindowItems extends Packet {
   public int windowId;
   public ItemStack[] itemStack;

   public Packet104WindowItems() {}

   public Packet104WindowItems(int par1, List par2List) {
      this.windowId = par1;
      this.itemStack = new ItemStack[par2List.size()];

      for(int var3 = 0; var3 < this.itemStack.length; ++var3) {
         ItemStack var4 = (ItemStack)par2List.get(var3);
         this.itemStack[var3] = var4 == null?null:var4.copy();
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.windowId = par1DataInput.readByte();
      short var2 = par1DataInput.readShort();
      this.itemStack = new ItemStack[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         this.itemStack[var3] = readItemStack(par1DataInput);
      }
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.windowId);
      par1DataOutput.writeShort(this.itemStack.length);

      for(int var2 = 0; var2 < this.itemStack.length; ++var2) {
         writeItemStack(this.itemStack[var2], par1DataOutput);
      }
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleWindowItems(this);
   }

   public int getPacketSize() {
      return 3 + this.itemStack.length * 5;
   }
}
