package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet204ClientInfo extends Packet {
   private String language;
   private int renderDistance;
   private int chatVisisble;
   private boolean chatColours;
   private int gameDifficulty;
   private boolean showCape;

   public Packet204ClientInfo() {}

   public Packet204ClientInfo(String par1Str, int par2, int par3, boolean par4, int par5, boolean par6) {
      this.language = par1Str;
      this.renderDistance = par2;
      this.chatVisisble = par3;
      this.chatColours = par4;
      this.gameDifficulty = par5;
      this.showCape = par6;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.language = readString(par1DataInput, 7);
      this.renderDistance = par1DataInput.readByte();
      byte var2 = par1DataInput.readByte();
      this.chatVisisble = var2 & 7;
      this.chatColours = (var2 & 8) == 8;
      this.gameDifficulty = par1DataInput.readByte();
      this.showCape = par1DataInput.readBoolean();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.language, par1DataOutput);
      par1DataOutput.writeByte(this.renderDistance);
      par1DataOutput.writeByte(this.chatVisisble | (this.chatColours?1:0) << 3);
      par1DataOutput.writeByte(this.gameDifficulty);
      par1DataOutput.writeBoolean(this.showCape);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleClientInfo(this);
   }

   public int getPacketSize() {
      return 7;
   }

   public String getLanguage() {
      return this.language;
   }

   public int getRenderDistance() {
      return this.renderDistance;
   }

   public int getChatVisibility() {
      return this.chatVisisble;
   }

   public boolean getChatColours() {
      return this.chatColours;
   }

   public int getDifficulty() {
      return this.gameDifficulty;
   }

   public boolean getShowCape() {
      return this.showCape;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
