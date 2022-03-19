package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class Packet20NamedEntitySpawn extends Packet {
   public int entityId;
   public String name;
   public int xPosition;
   public int yPosition;
   public int zPosition;
   public byte rotation;
   public byte pitch;
   public int currentItem;
   private DataWatcher metadata;
   private List metadataWatchableObjects;

   public Packet20NamedEntitySpawn() {}

   public Packet20NamedEntitySpawn(EntityPlayer par1EntityPlayer) {
      this.entityId = par1EntityPlayer.entityId;
      this.name = par1EntityPlayer.getCommandSenderName();
      this.xPosition = MathHelper.floor_double(par1EntityPlayer.posX * 32.0D);
      this.yPosition = MathHelper.floor_double(par1EntityPlayer.posY * 32.0D);
      this.zPosition = MathHelper.floor_double(par1EntityPlayer.posZ * 32.0D);
      this.rotation = (byte)((int)(par1EntityPlayer.rotationYaw * 256.0F / 360.0F));
      this.pitch = (byte)((int)(par1EntityPlayer.rotationPitch * 256.0F / 360.0F));
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      this.currentItem = var2 == null?0:var2.itemID;
      this.metadata = par1EntityPlayer.getDataWatcher();
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.name = readString(par1DataInput, 16);
      this.xPosition = par1DataInput.readInt();
      this.yPosition = par1DataInput.readInt();
      this.zPosition = par1DataInput.readInt();
      this.rotation = par1DataInput.readByte();
      this.pitch = par1DataInput.readByte();
      this.currentItem = par1DataInput.readShort();
      this.metadataWatchableObjects = DataWatcher.readWatchableObjects(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      writeString(this.name, par1DataOutput);
      par1DataOutput.writeInt(this.xPosition);
      par1DataOutput.writeInt(this.yPosition);
      par1DataOutput.writeInt(this.zPosition);
      par1DataOutput.writeByte(this.rotation);
      par1DataOutput.writeByte(this.pitch);
      par1DataOutput.writeShort(this.currentItem);
      this.metadata.writeWatchableObjects(par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleNamedEntitySpawn(this);
   }

   public int getPacketSize() {
      return 28;
   }

   public List getWatchedMetadata() {
      if(this.metadataWatchableObjects == null) {
         this.metadataWatchableObjects = this.metadata.getAllWatched();
      }

      return this.metadataWatchableObjects;
   }
}
