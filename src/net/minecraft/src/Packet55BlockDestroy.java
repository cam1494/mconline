package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet55BlockDestroy extends Packet {
   private int entityId;
   private int posX;
   private int posY;
   private int posZ;
   private int destroyedStage;

   public Packet55BlockDestroy() {}

   public Packet55BlockDestroy(int par1, int par2, int par3, int par4, int par5) {
      this.entityId = par1;
      this.posX = par2;
      this.posY = par3;
      this.posZ = par4;
      this.destroyedStage = par5;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.posX = par1DataInput.readInt();
      this.posY = par1DataInput.readInt();
      this.posZ = par1DataInput.readInt();
      this.destroyedStage = par1DataInput.readUnsignedByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeInt(this.posX);
      par1DataOutput.writeInt(this.posY);
      par1DataOutput.writeInt(this.posZ);
      par1DataOutput.write(this.destroyedStage);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleBlockDestroy(this);
   }

   public int getPacketSize() {
      return 13;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int getPosX() {
      return this.posX;
   }

   public int getPosY() {
      return this.posY;
   }

   public int getPosZ() {
      return this.posZ;
   }

   public int getDestroyedStage() {
      return this.destroyedStage;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet55BlockDestroy var2 = (Packet55BlockDestroy)par1Packet;
      return var2.entityId == this.entityId;
   }
}
