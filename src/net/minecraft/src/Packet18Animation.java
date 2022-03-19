package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet18Animation extends Packet {
   public int entityId;
   public int animate;

   public Packet18Animation() {}

   public Packet18Animation(Entity par1Entity, int par2) {
      this.entityId = par1Entity.entityId;
      this.animate = par2;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.animate = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.animate);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleAnimation(this);
   }

   public int getPacketSize() {
      return 5;
   }
}
