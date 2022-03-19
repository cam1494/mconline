package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet23VehicleSpawn extends Packet {
   public int entityId;
   public int xPosition;
   public int yPosition;
   public int zPosition;
   public int speedX;
   public int speedY;
   public int speedZ;
   public int pitch;
   public int yaw;
   public int type;
   public int throwerEntityId;

   public Packet23VehicleSpawn() {}

   public Packet23VehicleSpawn(Entity par1Entity, int par2) {
      this(par1Entity, par2, 0);
   }

   public Packet23VehicleSpawn(Entity par1Entity, int par2, int par3) {
      this.entityId = par1Entity.entityId;
      this.xPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
      this.yPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
      this.zPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
      this.pitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0F / 360.0F);
      this.yaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0F / 360.0F);
      this.type = par2;
      this.throwerEntityId = par3;
      if(par3 > 0) {
         double var4 = par1Entity.motionX;
         double var6 = par1Entity.motionY;
         double var8 = par1Entity.motionZ;
         double var10 = 3.9D;
         if(var4 < -var10) {
            var4 = -var10;
         }

         if(var6 < -var10) {
            var6 = -var10;
         }

         if(var8 < -var10) {
            var8 = -var10;
         }

         if(var4 > var10) {
            var4 = var10;
         }

         if(var6 > var10) {
            var6 = var10;
         }

         if(var8 > var10) {
            var8 = var10;
         }

         this.speedX = (int)(var4 * 8000.0D);
         this.speedY = (int)(var6 * 8000.0D);
         this.speedZ = (int)(var8 * 8000.0D);
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.type = par1DataInput.readByte();
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readInt();
      this.zPosition = par1DataInput.readInt();
      this.pitch = par1DataInput.readByte();
      this.yaw = par1DataInput.readByte();
      this.throwerEntityId = par1DataInput.readInt();
      if(this.throwerEntityId > 0) {
         this.speedX = par1DataInput.readShort();
         this.speedY = par1DataInput.readShort();
         this.speedZ = par1DataInput.readShort();
      }
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      par1DataOutput.writeByte(this.type);
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.writeInt(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.writeByte(this.pitch);
      par1DataOutput.writeByte(this.yaw);
      par1DataOutput.writeInt(this.throwerEntityId);
      if(this.throwerEntityId > 0) {
         par1DataOutput.writeShort(this.speedX);
         par1DataOutput.writeShort(this.speedY);
         par1DataOutput.writeShort(this.speedZ);
      }
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleVehicleSpawn(this);
   }

   public int getPacketSize() {
      return 21 + this.throwerEntityId > 0?6:0;
   }
}
