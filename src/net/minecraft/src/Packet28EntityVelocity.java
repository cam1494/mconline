package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet28EntityVelocity extends Packet {
   public int entityId;
   public int motionX;
   public int motionY;
   public int motionZ;

   public Packet28EntityVelocity() {}

   public Packet28EntityVelocity(Entity par1Entity) {
      this(par1Entity.entityId, par1Entity.motionX, par1Entity.motionY, par1Entity.motionZ);
   }

   public Packet28EntityVelocity(int par1, double par2, double par4, double par6) {
      this.entityId = par1;
      double var8 = 3.9D;
      if(par2 < -var8) {
         par2 = -var8;
      }

      if(par4 < -var8) {
         par4 = -var8;
      }

      if(par6 < -var8) {
         par6 = -var8;
      }

      if(par2 > var8) {
         par2 = var8;
      }

      if(par4 > var8) {
         par4 = var8;
      }

      if(par6 > var8) {
         par6 = var8;
      }

      this.motionX = (int)(par2 * 8000.0D);
      this.motionY = (int)(par4 * 8000.0D);
      this.motionZ = (int)(par6 * 8000.0D);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.motionX = par1DataInput.readShort();
      this.motionY = par1DataInput.readShort();
      this.motionZ = par1DataInput.readShort();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeShort(this.motionX);
      par1DataOutput.writeShort(this.motionY);
      par1DataOutput.writeShort(this.motionZ);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityVelocity(this);
   }

   public int getPacketSize() {
      return 10;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet28EntityVelocity var2 = (Packet28EntityVelocity)par1Packet;
      return var2.entityId == this.entityId;
   }
}
