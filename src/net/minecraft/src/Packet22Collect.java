package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet22Collect extends Packet {
   public int collectedEntityId;
   public int collectorEntityId;

   public Packet22Collect() {}

   public Packet22Collect(int par1, int par2) {
      this.collectedEntityId = par1;
      this.collectorEntityId = par2;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.collectedEntityId = par1DataInput.readInt();
      this.collectorEntityId = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.collectedEntityId);
      par1DataOutput.writeInt(this.collectorEntityId);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleCollect(this);
   }

   public int getPacketSize() {
      return 8;
   }
}
