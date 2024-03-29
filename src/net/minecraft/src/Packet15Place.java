package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet15Place extends Packet {
   private int xPosition;
   private int yPosition;
   private int zPosition;
   private int direction;
   private ItemStack itemStack;
   private float xOffset;
   private float yOffset;
   private float zOffset;

   public Packet15Place() {}

   public Packet15Place(int par1, int par2, int par3, int par4, ItemStack par5ItemStack, float par6, float par7, float par8) {
      this.xPosition = par1;
      this.yPosition = par2;
      this.zPosition = par3;
      this.direction = par4;
      this.itemStack = par5ItemStack != null?par5ItemStack.copy():null;
      this.xOffset = par6;
      this.yOffset = par7;
      this.zOffset = par8;
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readUnsignedByte();
      this.zPosition = par1DataInput.readInt();
      this.direction = par1DataInput.readUnsignedByte();
      this.itemStack = readItemStack(par1DataInput);
      this.xOffset = (float)par1DataInput.readUnsignedByte() / 16.0F;
      this.yOffset = (float)par1DataInput.readUnsignedByte() / 16.0F;
      this.zOffset = (float)par1DataInput.readUnsignedByte() / 16.0F;
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.write(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.write(this.direction);
      writeItemStack(this.itemStack, par1DataOutput);
      par1DataOutput.write((int)(this.xOffset * 16.0F));
      par1DataOutput.write((int)(this.yOffset * 16.0F));
      par1DataOutput.write((int)(this.zOffset * 16.0F));
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handlePlace(this);
   }

   public int getPacketSize() {
      return 19;
   }

   public int getXPosition() {
      return this.xPosition;
   }

   public int getYPosition() {
      return this.yPosition;
   }

   public int getZPosition() {
      return this.zPosition;
   }

   public int getDirection() {
      return this.direction;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public float getXOffset() {
      return this.xOffset;
   }

   public float getYOffset() {
      return this.yOffset;
   }

   public float getZOffset() {
      return this.zOffset;
   }
}
