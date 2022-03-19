package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet39AttachEntity extends Packet {
   public int attachState;
   public int ridingEntityId;
   public int vehicleEntityId;

   public Packet39AttachEntity() {}

   public Packet39AttachEntity(int par1, Entity par2Entity, Entity par3Entity) {
      this.attachState = par1;
      this.ridingEntityId = par2Entity.entityId;
      this.vehicleEntityId = par3Entity != null?par3Entity.entityId:-1;
   }

   public int getPacketSize() {
      return 8;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.ridingEntityId = par1DataInput.readInt();
      this.vehicleEntityId = par1DataInput.readInt();
      this.attachState = par1DataInput.readUnsignedByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.ridingEntityId);
      par1DataOutput.writeInt(this.vehicleEntityId);
      par1DataOutput.writeByte(this.attachState);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleAttachEntity(this);
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet39AttachEntity var2 = (Packet39AttachEntity)par1Packet;
      return var2.ridingEntityId == this.ridingEntityId;
   }
}
