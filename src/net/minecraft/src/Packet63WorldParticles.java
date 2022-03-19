package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet63WorldParticles extends Packet {
   private String particleName;
   private float posX;
   private float posY;
   private float posZ;
   private float offsetX;
   private float offsetY;
   private float offsetZ;
   private float speed;
   private int quantity;

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.particleName = readString(par1DataInput, 64);
      this.posX = par1DataInput.readFloat();
      this.posY = par1DataInput.readFloat();
      this.posZ = par1DataInput.readFloat();
      this.offsetX = par1DataInput.readFloat();
      this.offsetY = par1DataInput.readFloat();
      this.offsetZ = par1DataInput.readFloat();
      this.speed = par1DataInput.readFloat();
      this.quantity = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.particleName, par1DataOutput);
      par1DataOutput.writeFloat(this.posX);
      par1DataOutput.writeFloat(this.posY);
      par1DataOutput.writeFloat(this.posZ);
      par1DataOutput.writeFloat(this.offsetX);
      par1DataOutput.writeFloat(this.offsetY);
      par1DataOutput.writeFloat(this.offsetZ);
      par1DataOutput.writeFloat(this.speed);
      par1DataOutput.writeInt(this.quantity);
   }

   public String getParticleName() {
      return this.particleName;
   }

   public double getPositionX() {
      return (double)this.posX;
   }

   public double getPositionY() {
      return (double)this.posY;
   }

   public double getPositionZ() {
      return (double)this.posZ;
   }

   public float getOffsetX() {
      return this.offsetX;
   }

   public float getOffsetY() {
      return this.offsetY;
   }

   public float getOffsetZ() {
      return this.offsetZ;
   }

   public float getSpeed() {
      return this.speed;
   }

   public int getQuantity() {
      return this.quantity;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleWorldParticles(this);
   }

   public int getPacketSize() {
      return 64;
   }
}
