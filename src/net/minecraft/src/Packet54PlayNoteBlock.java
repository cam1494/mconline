package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet54PlayNoteBlock extends Packet {
   public int xLocation;
   public int yLocation;
   public int zLocation;
   public int instrumentType;
   public int pitch;
   public int blockId;

   public Packet54PlayNoteBlock() {}

   public Packet54PlayNoteBlock(int par1, int par2, int par3, int par4, int par5, int par6) {
      this.xLocation = par1;
      this.yLocation = par2;
      this.zLocation = par3;
      this.instrumentType = par5;
      this.pitch = par6;
      this.blockId = par4;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xLocation = par1DataInput.readInt();
      this.yLocation = par1DataInput.readShort();
      this.zLocation = par1DataInput.readInt();
      this.instrumentType = par1DataInput.readUnsignedByte();
      this.pitch = par1DataInput.readUnsignedByte();
      this.blockId = par1DataInput.readShort() & 4095;
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.xLocation);
      par1DataOutput.writeShort(this.yLocation);
      par1DataOutput.writeInt(this.zLocation);
      par1DataOutput.write(this.instrumentType);
      par1DataOutput.write(this.pitch);
      par1DataOutput.writeShort(this.blockId & 4095);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleBlockEvent(this);
   }

   public int getPacketSize() {
      return 14;
   }
}
