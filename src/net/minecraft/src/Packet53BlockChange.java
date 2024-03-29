package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet53BlockChange extends Packet {
   public int xPosition;
   public int yPosition;
   public int zPosition;
   public int type;
   public int metadata;

   public Packet53BlockChange() {
      this.isChunkDataPacket = true;
   }

   public Packet53BlockChange(int par1, int par2, int par3, World par4World) {
      this.isChunkDataPacket = true;
      this.xPosition = par1;
      this.yPosition = par2;
      this.zPosition = par3;
      this.type = par4World.getBlockId(par1, par2, par3);
      this.metadata = par4World.getBlockMetadata(par1, par2, par3);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readUnsignedByte();
      this.zPosition = par1DataInput.readInt();
      this.type = par1DataInput.readShort();
      this.metadata = par1DataInput.readUnsignedByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.write(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.writeShort(this.type);
      par1DataOutput.write(this.metadata);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleBlockChange(this);
   }

   public int getPacketSize() {
      return 11;
   }
}
