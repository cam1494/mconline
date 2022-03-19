package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet133TileEditorOpen extends Packet {
   public int field_142037_a;
   public int field_142035_b;
   public int field_142036_c;
   public int field_142034_d;

   public Packet133TileEditorOpen() {}

   public Packet133TileEditorOpen(int par1, int par2, int par3, int par4) {
      this.field_142037_a = par1;
      this.field_142035_b = par2;
      this.field_142036_c = par3;
      this.field_142034_d = par4;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.func_142031_a(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.field_142037_a = par1DataInput.readByte();
      this.field_142035_b = par1DataInput.readInt();
      this.field_142036_c = par1DataInput.readInt();
      this.field_142034_d = par1DataInput.readInt();
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeByte(this.field_142037_a);
      par1DataOutput.writeInt(this.field_142035_b);
      par1DataOutput.writeInt(this.field_142036_c);
      par1DataOutput.writeInt(this.field_142034_d);
   }

   public int getPacketSize() {
      return 13;
   }
}
