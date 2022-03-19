package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet {
   public String reason;

   public Packet255KickDisconnect() {}

   public Packet255KickDisconnect(String par1Str) {
      this.reason = par1Str;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.reason = readString(par1DataInput, 256);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.reason, par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleKickDisconnect(this);
   }

   public int getPacketSize() {
      return this.reason.length();
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
