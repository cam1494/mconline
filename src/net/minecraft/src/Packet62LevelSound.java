package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet62LevelSound extends Packet {
   private String soundName;
   private int effectX;
   private int effectY = Integer.MAX_VALUE;
   private int effectZ;
   private float volume;
   private int pitch;

   public Packet62LevelSound() {}

   public Packet62LevelSound(String par1Str, double par2, double par4, double par6, float par8, float par9) {
      this.soundName = par1Str;
      this.effectX = (int)(par2 * 8.0D);
      this.effectY = (int)(par4 * 8.0D);
      this.effectZ = (int)(par6 * 8.0D);
      this.volume = par8;
      this.pitch = (int)(par9 * 63.0F);
      if(this.pitch < 0) {
         this.pitch = 0;
      }

      if(this.pitch > 255) {
         this.pitch = 255;
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.soundName = readString(par1DataInput, 256);
      this.effectX = par1DataInput.readInt();
      this.effectY = par1DataInput.readInt();
      this.effectZ = par1DataInput.readInt();
      this.volume = par1DataInput.readFloat();
      this.pitch = par1DataInput.readUnsignedByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.soundName, par1DataOutput);
      par1DataOutput.writeInt(this.effectX);
      par1DataOutput.writeInt(this.effectY);
      par1DataOutput.writeInt(this.effectZ);
      par1DataOutput.writeFloat(this.volume);
      par1DataOutput.writeByte(this.pitch);
   }

   public String getSoundName() {
      return this.soundName;
   }

   public double getEffectX() {
      return (double)((float)this.effectX / 8.0F);
   }

   public double getEffectY() {
      return (double)((float)this.effectY / 8.0F);
   }

   public double getEffectZ() {
      return (double)((float)this.effectZ / 8.0F);
   }

   public float getVolume() {
      return this.volume;
   }

   public float getPitch() {
      return (float)this.pitch / 63.0F;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleLevelSound(this);
   }

   public int getPacketSize() {
      return 24;
   }
}
