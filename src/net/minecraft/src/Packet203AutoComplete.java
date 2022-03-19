package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet203AutoComplete extends Packet {
   private String text;

   public Packet203AutoComplete() {}

   public Packet203AutoComplete(String par1Str) {
      this.text = par1Str;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.text = readString(par1DataInput, 32767);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(org.apache.commons.lang3.StringUtils.substring(this.text, 0, 32767), par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleAutoComplete(this);
   }

   public int getPacketSize() {
      return 2 + this.text.length() * 2;
   }

   public String getText() {
      return this.text;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
