package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet4UpdateTime extends Packet {
   public long worldAge;
   public long time;

   public Packet4UpdateTime() {}

   public Packet4UpdateTime(long par1, long par3, boolean par5) {
      this.worldAge = par1;
      this.time = par3;
      if(!par5) {
         this.time = -this.time;
         if(this.time == 0L) {
            this.time = -1L;
         }
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.worldAge = par1DataInput.readLong();
      this.time = par1DataInput.readLong();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeLong(this.worldAge);
      par1DataOutput.writeLong(this.time);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleUpdateTime(this);
   }

   public int getPacketSize() {
      return 16;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }

   public boolean canProcessAsync() {
      return true;
   }
}
