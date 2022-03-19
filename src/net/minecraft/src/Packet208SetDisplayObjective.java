package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet208SetDisplayObjective extends Packet {
   public int scoreboardPosition;
   public String scoreName;

   public Packet208SetDisplayObjective() {}

   public Packet208SetDisplayObjective(int par1, ScoreObjective par2ScoreObjective) {
      this.scoreboardPosition = par1;
      if(par2ScoreObjective == null) {
         this.scoreName = "";
      } else {
         this.scoreName = par2ScoreObjective.getName();
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.scoreboardPosition = par1DataInput.readByte();
      this.scoreName = readString(par1DataInput, 16);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.scoreboardPosition);
      writeString(this.scoreName, par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleSetDisplayObjective(this);
   }

   public int getPacketSize() {
      return 3 + this.scoreName.length();
   }
}
