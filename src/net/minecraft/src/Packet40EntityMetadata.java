package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class Packet40EntityMetadata extends Packet {
   public int entityId;
   private List metadata;

   public Packet40EntityMetadata() {}

   public Packet40EntityMetadata(int par1, DataWatcher par2DataWatcher, boolean par3) {
      this.entityId = par1;
      if(par3) {
         this.metadata = par2DataWatcher.getAllWatched();
      } else {
         this.metadata = par2DataWatcher.unwatchAndReturnAllWatched();
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.entityId = par1DataInput.readInt();
      this.metadata = DataWatcher.readWatchableObjects(par1DataInput);
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      par1DataOutput.writeInt(this.entityId);
      DataWatcher.writeObjectsInListToStream(this.metadata, par1DataOutput);
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleEntityMetadata(this);
   }

   public int getPacketSize() {
      return 5;
   }

   public List getMetadata() {
      return this.metadata;
   }
}
