package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet {
   public float healthMP;
   public int food;
   public float foodSaturation;

   public Packet8UpdateHealth() {}

   public Packet8UpdateHealth(float par1, int par2, float par3) {
      this.healthMP = par1;
      this.food = par2;
      this.foodSaturation = par3;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.healthMP = par1DataInput.readFloat();
      this.food = par1DataInput.readShort();
      this.foodSaturation = par1DataInput.readFloat();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeFloat(this.healthMP);
      par1DataOutput.writeShort(this.food);
      par1DataOutput.writeFloat(this.foodSaturation);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleUpdateHealth(this);
   }

   public int getPacketSize() {
      return 8;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
