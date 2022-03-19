package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet19EntityAction extends Packet {
   public int entityId;
   public int action;
   public int auxData;

   public Packet19EntityAction() {}

   public Packet19EntityAction(Entity par1Entity, int par2) {
      this(par1Entity, par2, 0);
   }

   public Packet19EntityAction(Entity par1Entity, int par2, int par3) {
      this.entityId = par1Entity.entityId;
      this.action = par2;
      this.auxData = par3;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.action = par1DataInput.readByte();
      this.auxData = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.action);
      par1DataOutput.writeInt(this.auxData);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityAction(this);
   }

   public int getPacketSize() {
      return 9;
   }
}
