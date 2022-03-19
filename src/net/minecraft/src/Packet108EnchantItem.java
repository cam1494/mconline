package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet108EnchantItem extends Packet {
   public int windowId;
   public int enchantment;

   public Packet108EnchantItem() {}

   public Packet108EnchantItem(int par1, int par2) {
      this.windowId = par1;
      this.enchantment = par2;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEnchantItem(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.windowId = par1DataInput.readByte();
      this.enchantment = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.windowId);
      par1DataOutput.writeByte(this.enchantment);
   }

   public int getPacketSize() {
      return 2;
   }
}
