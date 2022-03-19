package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet0KeepAlive extends Packet {
   public int randomId;

   public Packet0KeepAlive() {}

   public Packet0KeepAlive(int par1) {
      this.randomId = par1;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleKeepAlive(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.randomId = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.randomId);
   }

   public int getPacketSize() {
      return 4;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }

   public boolean canProcessAsync() {
      return true;
   }
}
