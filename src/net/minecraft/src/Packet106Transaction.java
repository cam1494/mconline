package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet106Transaction extends Packet {
   public int windowId;
   public short shortWindowId;
   public boolean accepted;

   public Packet106Transaction() {}

   public Packet106Transaction(int par1, short par2, boolean par3) {
      this.windowId = par1;
      this.shortWindowId = par2;
      this.accepted = par3;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleTransaction(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.windowId = par1DataInput.readByte();
      this.shortWindowId = par1DataInput.readShort();
      this.accepted = par1DataInput.readByte() != 0;
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.windowId);
      par1DataOutput.writeShort(this.shortWindowId);
      par1DataOutput.writeByte(this.accepted?1:0);
   }

   public int getPacketSize() {
      return 4;
   }
}
