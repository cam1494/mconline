package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet105UpdateProgressbar extends Packet {
   public int windowId;
   public int progressBar;
   public int progressBarValue;

   public Packet105UpdateProgressbar() {}

   public Packet105UpdateProgressbar(int par1, int par2, int par3) {
      this.windowId = par1;
      this.progressBar = par2;
      this.progressBarValue = par3;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleUpdateProgressbar(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.windowId = par1DataInput.readByte();
      this.progressBar = par1DataInput.readShort();
      this.progressBarValue = par1DataInput.readShort();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.windowId);
      par1DataOutput.writeShort(this.progressBar);
      par1DataOutput.writeShort(this.progressBarValue);
   }

   public int getPacketSize() {
      return 5;
   }
}
