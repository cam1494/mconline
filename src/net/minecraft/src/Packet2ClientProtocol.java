package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet2ClientProtocol extends Packet {
   private int protocolVersion;
   private String username;
   private String serverHost;
   private int serverPort;

   public Packet2ClientProtocol() {}

   public Packet2ClientProtocol(int par1, String par2Str, String par3Str, int par4) {
      this.protocolVersion = par1;
      this.username = par2Str;
      this.serverHost = par3Str;
      this.serverPort = par4;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.protocolVersion = par1DataInput.readByte();
      this.username = readString(par1DataInput, 16);
      this.serverHost = readString(par1DataInput, 255);
      this.serverPort = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.protocolVersion);
      writeString(this.username, par1DataOutput);
      writeString(this.serverHost, par1DataOutput);
      par1DataOutput.writeInt(this.serverPort);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleClientProtocol(this);
   }

   public int getPacketSize() {
      return 3 + 2 * this.username.length();
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public String getUsername() {
      return this.username;
   }
}
