package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet207SetScore extends Packet {
   public String itemName = "";
   public String scoreName = "";
   public int value;
   public int updateOrRemove;

   public Packet207SetScore() {}

   public Packet207SetScore(Score par1Score, int par2) {
      this.itemName = par1Score.getPlayerName();
      this.scoreName = par1Score.func_96645_d().getName();
      this.value = par1Score.getScorePoints();
      this.updateOrRemove = par2;
   }

   public Packet207SetScore(String par1Str) {
      this.itemName = par1Str;
      this.scoreName = "";
      this.value = 0;
      this.updateOrRemove = 1;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.itemName = readString(par1DataInput, 16);
      this.updateOrRemove = par1DataInput.readByte();
      if(this.updateOrRemove != 1) {
         this.scoreName = readString(par1DataInput, 16);
         this.value = par1DataInput.readInt();
      }
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.itemName, par1DataOutput);
      par1DataOutput.writeByte(this.updateOrRemove);
      if(this.updateOrRemove != 1) {
         writeString(this.scoreName, par1DataOutput);
         par1DataOutput.writeInt(this.value);
      }
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleSetScore(this);
   }

   public int getPacketSize() {
      return 2 + (this.itemName == null?0:this.itemName.length()) + 2 + (this.scoreName == null?0:this.scoreName.length()) + 4 + 1;
   }
}
