package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet16BlockItemSwitch extends Packet {
   public int id;

   public Packet16BlockItemSwitch() {}

   public Packet16BlockItemSwitch(int par1) {
      this.id = par1;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.id = par1DataInput.readShort();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeShort(this.id);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleBlockItemSwitch(this);
   }

   public int getPacketSize() {
      return 2;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
