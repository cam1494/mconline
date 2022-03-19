package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet30Entity extends Packet {
   public int entityId;
   public byte xPosition;
   public byte yPosition;
   public byte zPosition;
   public byte yaw;
   public byte pitch;
   public boolean rotating;

   public Packet30Entity() {}

   public Packet30Entity(int par1) {
      this.entityId = par1;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntity(this);
   }

   public int getPacketSize() {
      return 4;
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet30Entity var2 = (Packet30Entity)par1Packet;
      return var2.entityId == this.entityId;
   }
}
