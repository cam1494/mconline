package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet34EntityTeleport extends Packet {
   public int entityId;
   public int xPosition;
   public int yPosition;
   public int zPosition;
   public byte yaw;
   public byte pitch;

   public Packet34EntityTeleport() {}

   public Packet34EntityTeleport(Entity par1Entity) {
      this.entityId = par1Entity.entityId;
      this.xPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
      this.yPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
      this.zPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
      this.yaw = (byte)((int)(par1Entity.rotationYaw * 256.0F / 360.0F));
      this.pitch = (byte)((int)(par1Entity.rotationPitch * 256.0F / 360.0F));
   }

   public Packet34EntityTeleport(int par1, int par2, int par3, int par4, byte par5, byte par6) {
      this.entityId = par1;
      this.xPosition = par2;
      this.yPosition = par3;
      this.zPosition = par4;
      this.yaw = par5;
      this.pitch = par6;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readInt();
      this.zPosition = par1DataInput.readInt();
      this.yaw = par1DataInput.readByte();
      this.pitch = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.writeInt(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.write(this.yaw);
      par1DataOutput.write(this.pitch);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityTeleport(this);
   }

   public int getPacketSize() {
      return 34;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      Packet34EntityTeleport var2 = (Packet34EntityTeleport)par1Packet;
      return var2.entityId == this.entityId;
   }
}
