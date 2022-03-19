package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet101CloseWindow extends Packet {
   public int windowId;

   public Packet101CloseWindow() {}

   public Packet101CloseWindow(int par1) {
      this.windowId = par1;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleCloseWindow(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.windowId = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.windowId);
   }

   public int getPacketSize() {
      return 1;
   }
}
