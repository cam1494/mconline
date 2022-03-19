package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet202PlayerAbilities extends Packet {
   private boolean disableDamage;
   private boolean isFlying;
   private boolean allowFlying;
   private boolean isCreativeMode;
   private float flySpeed;
   private float walkSpeed;

   public Packet202PlayerAbilities() {}

   public Packet202PlayerAbilities(PlayerCapabilities par1PlayerCapabilities) {
      this.setDisableDamage(par1PlayerCapabilities.disableDamage);
      this.setFlying(par1PlayerCapabilities.isFlying);
      this.setAllowFlying(par1PlayerCapabilities.allowFlying);
      this.setCreativeMode(par1PlayerCapabilities.isCreativeMode);
      this.setFlySpeed(par1PlayerCapabilities.getFlySpeed());
      this.setWalkSpeed(par1PlayerCapabilities.getWalkSpeed());
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      byte var2 = par1DataInput.readByte();
      this.setDisableDamage((var2 & 1) > 0);
      this.setFlying((var2 & 2) > 0);
      this.setAllowFlying((var2 & 4) > 0);
      this.setCreativeMode((var2 & 8) > 0);
      this.setFlySpeed(par1DataInput.readFloat());
      this.setWalkSpeed(par1DataInput.readFloat());
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      byte var2 = 0;
      if(this.getDisableDamage()) {
         var2 = (byte)(var2 | 1);
      }

      if(this.getFlying()) {
         var2 = (byte)(var2 | 2);
      }

      if(this.getAllowFlying()) {
         var2 = (byte)(var2 | 4);
      }

      if(this.isCreativeMode()) {
         var2 = (byte)(var2 | 8);
      }

      par1DataOutput.writeByte(var2);
      par1DataOutput.writeFloat(this.flySpeed);
      par1DataOutput.writeFloat(this.walkSpeed);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handlePlayerAbilities(this);
   }

   public int getPacketSize() {
      return 2;
   }

   public boolean getDisableDamage() {
      return this.disableDamage;
   }

   public void setDisableDamage(boolean par1) {
      this.disableDamage = par1;
   }

   public boolean getFlying() {
      return this.isFlying;
   }

   public void setFlying(boolean par1) {
      this.isFlying = par1;
   }

   public boolean getAllowFlying() {
      return this.allowFlying;
   }

   public void setAllowFlying(boolean par1) {
      this.allowFlying = par1;
   }

   public boolean isCreativeMode() {
      return this.isCreativeMode;
   }

   public void setCreativeMode(boolean par1) {
      this.isCreativeMode = par1;
   }

   public float getFlySpeed() {
      return this.flySpeed;
   }

   public void setFlySpeed(float par1) {
      this.flySpeed = par1;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   public void setWalkSpeed(float par1) {
      this.walkSpeed = par1;
   }

   public boolean isRealPacket() {
      return true;
   }

   public boolean containsSameEntityIDAs(Packet par1Packet) {
      return true;
   }
}
