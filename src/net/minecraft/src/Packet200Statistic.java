package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet200Statistic extends Packet {
   public int statisticId;
   public int amount;

   public Packet200Statistic() {}

   public Packet200Statistic(int par1, int par2) {
      this.statisticId = par1;
      this.amount = par2;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleStatistic(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.statisticId = par1DataInput.readInt();
      this.amount = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.statisticId);
      par1DataOutput.writeInt(this.amount);
   }

   public int getPacketSize() {
      return 6;
   }

   public boolean canProcessAsync() {
      return true;
   }
}
