package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet206SetObjective extends Packet {
   public String objectiveName;
   public String objectiveDisplayName;
   public int change;

   public Packet206SetObjective() {}

   public Packet206SetObjective(ScoreObjective par1ScoreObjective, int par2) {
      this.objectiveName = par1ScoreObjective.getName();
      this.objectiveDisplayName = par1ScoreObjective.getDisplayName();
      this.change = par2;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.objectiveName = readString(par1DataInput, 16);
      this.objectiveDisplayName = readString(par1DataInput, 32);
      this.change = par1DataInput.readByte();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.objectiveName, par1DataOutput);
      writeString(this.objectiveDisplayName, par1DataOutput);
      par1DataOutput.writeByte(this.change);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleSetObjective(this);
   }

   public int getPacketSize() {
      return 2 + this.objectiveName.length() + 2 + this.objectiveDisplayName.length() + 1;
   }
}
