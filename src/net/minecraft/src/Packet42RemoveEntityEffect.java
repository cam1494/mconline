package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet42RemoveEntityEffect extends Packet {
   public int entityId;
   public byte effectId;

   public Packet42RemoveEntityEffect() {}

   public Packet42RemoveEntityEffect(int par1, PotionEffect par2PotionEffect) {
      this.entityId = par1;
      this.effectId = (byte)(par2PotionEffect.getPotionID() & 255);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.effectId = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.effectId);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleRemoveEntityEffect(this);
   }

   public int getPacketSize() {
      return 5;
   }
}
