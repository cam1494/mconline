package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet11PlayerPosition extends Packet10Flying {
   public Packet11PlayerPosition() {
      this.moving = true;
   }

   public Packet11PlayerPosition(double par1, double par3, double par5, double par7, boolean par9) {
      this.xPosition = par1;
      this.yPosition = par3;
      this.stance = par5;
      this.zPosition = par7;
      this.onGround = par9;
      this.moving = true;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xPosition = par1DataInput.readDouble();
      this.yPosition = par1DataInput.readDouble();
      this.stance = par1DataInput.readDouble();
      this.zPosition = par1DataInput.readDouble();
      super.readPacketData(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeDouble(this.xPosition);
      par1DataOutput.writeDouble(this.yPosition);
      par1DataOutput.writeDouble(this.stance);
      par1DataOutput.writeDouble(this.zPosition);
      super.writePacketData(par1DataOutput);
   }

   public int getPacketSize() {
      return 33;
   }
}
