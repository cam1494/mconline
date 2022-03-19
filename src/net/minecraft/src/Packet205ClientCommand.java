package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet205ClientCommand extends Packet {
   public int forceRespawn;

   public Packet205ClientCommand() {}

   public Packet205ClientCommand(int par1) {
      this.forceRespawn = par1;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.forceRespawn = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.forceRespawn & 255);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleClientCommand(this);
   }

   public int getPacketSize() {
      return 1;
   }
}
