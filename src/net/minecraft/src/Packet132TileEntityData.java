package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet132TileEntityData extends Packet {
   public int xPosition;
   public int yPosition;
   public int zPosition;
   public int actionType;
   public NBTTagCompound data;

   public Packet132TileEntityData() {
      this.isChunkDataPacket = true;
   }

   public Packet132TileEntityData(int par1, int par2, int par3, int par4, NBTTagCompound par5NBTTagCompound) {
      this.isChunkDataPacket = true;
      this.xPosition = par1;
      this.yPosition = par2;
      this.zPosition = par3;
      this.actionType = par4;
      this.data = par5NBTTagCompound;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readShort();
      this.zPosition = par1DataInput.readInt();
      this.actionType = par1DataInput.readByte();
      this.data = readNBTTagCompound(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.writeShort(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.writeByte((byte)this.actionType);
      writeNBTTagCompound(this.data, par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleTileEntityData(this);
   }

   public int getPacketSize() {
      return 25;
   }
}
