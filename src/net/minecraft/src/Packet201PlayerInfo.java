package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet201PlayerInfo extends Packet {
   public String playerName;
   public boolean isConnected;
   public int ping;

   public Packet201PlayerInfo() {}

   public Packet201PlayerInfo(String par1Str, boolean par2, int par3) {
      this.playerName = par1Str;
      this.isConnected = par2;
      this.ping = par3;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.playerName = readString(par1DataInput, 16);
      this.isConnected = par1DataInput.readByte() != 0;
      this.ping = par1DataInput.readShort();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.playerName, par1DataOutput);
      par1DataOutput.writeByte(this.isConnected?1:0);
      par1DataOutput.writeShort(this.ping);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handlePlayerInfo(this);
   }

   public int getPacketSize() {
      return this.playerName.length() + 2 + 1 + 2;
   }
}
