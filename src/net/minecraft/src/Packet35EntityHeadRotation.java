package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet35EntityHeadRotation extends Packet {
   public int entityId;
   public byte headRotationYaw;

   public Packet35EntityHeadRotation() {}

   public Packet35EntityHeadRotation(int par1, byte par2) {
      this.entityId = par1;
      this.headRotationYaw = par2;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.headRotationYaw = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.headRotationYaw);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityHeadRotation(this);
   }

   public int getPacketSize() {
      return 5;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet35EntityHeadRotation var2 = (Packet35EntityHeadRotation)par1Packet;
      return var2.entityId == this.entityId;
   }

   public boolean canProcessAsync() {
      return true;
   }
}
