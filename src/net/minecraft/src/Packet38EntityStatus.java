package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet38EntityStatus extends Packet {
   public int entityId;
   public byte entityStatus;

   public Packet38EntityStatus() {}

   public Packet38EntityStatus(int par1, byte par2) {
      this.entityId = par1;
      this.entityStatus = par2;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.entityStatus = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.entityStatus);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityStatus(this);
   }

   public int getPacketSize() {
      return 5;
   }
}
