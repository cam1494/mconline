package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet10Flying extends Packet {
   public double xPosition;
   public double yPosition;
   public double zPosition;
   public double stance;
   public float yaw;
   public float pitch;
   public boolean onGround;
   public boolean moving;
   public boolean rotating;

   public Packet10Flying() {}

   public Packet10Flying(boolean par1) {
      this.onGround = par1;
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleFlying(this);
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.onGround = par1DataInput.readUnsignedByte() != 0;
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.write(this.onGround?1:0);
   }

   public int getPacketSize() {
      return 1;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
