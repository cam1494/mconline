package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet6SpawnPosition extends Packet {
   public int xPosition;
   public int yPosition;
   public int zPosition;

   public Packet6SpawnPosition() {}

   public Packet6SpawnPosition(int par1, int par2, int par3) {
      this.xPosition = par1;
      this.yPosition = par2;
      this.zPosition = par3;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readInt();
      this.zPosition = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.writeInt(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleSpawnPosition(this);
   }

   public int getPacketSize() {
      return 12;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }

   public boolean canProcessAsync() {
      return false;
   }
}
