package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet29DestroyEntity extends Packet {
   public int[] entityId;

   public Packet29DestroyEntity() {}

   public Packet29DestroyEntity(int ... par1ArrayOfInteger) {
      this.entityId = par1ArrayOfInteger;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = new int[par1DataInput.readByte()];

      for(int var2 = 0; var2 < this.entityId.length; ++var2) {
         this.entityId[var2] = par1DataInput.readInt();
      }
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.entityId.length);

      for(int var2 = 0; var2 < this.entityId.length; ++var2) {
         par1DataOutput.writeInt(this.entityId[var2]);
      }
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleDestroyEntity(this);
   }

   public int getPacketSize() {
      return 1 + this.entityId.length * 4;
   }
}
