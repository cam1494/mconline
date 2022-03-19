package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet43Experience extends Packet {
   public float experience;
   public int experienceTotal;
   public int experienceLevel;

   public Packet43Experience() {}

   public Packet43Experience(float par1, int par2, int par3) {
      this.experience = par1;
      this.experienceTotal = par2;
      this.experienceLevel = par3;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.experience = par1DataInput.readFloat();
      this.experienceLevel = par1DataInput.readShort();
      this.experienceTotal = par1DataInput.readShort();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeFloat(this.experience);
      par1DataOutput.writeShort(this.experienceLevel);
      par1DataOutput.writeShort(this.experienceTotal);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleExperience(this);
   }

   public int getPacketSize() {
      return 4;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
