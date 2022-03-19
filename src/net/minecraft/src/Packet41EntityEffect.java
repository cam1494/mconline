package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet41EntityEffect extends Packet {
   public int entityId;
   public byte effectId;
   public byte effectAmplifier;
   public short duration;

   public Packet41EntityEffect() {}

   public Packet41EntityEffect(int par1, PotionEffect par2PotionEffect) {
      this.entityId = par1;
      this.effectId = (byte)(par2PotionEffect.getPotionID() & 255);
      this.effectAmplifier = (byte)(par2PotionEffect.getAmplifier() & 255);
      if(par2PotionEffect.getDuration() > 32767) {
         this.duration = 32767;
      } else {
         this.duration = (short)par2PotionEffect.getDuration();
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.effectId = par1DataInput.readByte();
      this.effectAmplifier = par1DataInput.readByte();
      this.duration = par1DataInput.readShort();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.effectId);
      par1DataOutput.writeByte(this.effectAmplifier);
      par1DataOutput.writeShort(this.duration);
   }

   public boolean isDurationMax() {
      return this.duration == 32767;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityEffect(this);
   }

   public int getPacketSize() {
      return 8;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet41EntityEffect var2 = (Packet41EntityEffect)par1Packet;
      return var2.entityId == this.entityId && var2.effectId == this.effectId;
   }
}
