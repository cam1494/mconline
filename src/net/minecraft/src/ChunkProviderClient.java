package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderClient implements IChunkProvider {
   private Chunk blankChunk;
   private LongHashMap chunkMapping = new LongHashMap();
   private List chunkListing = new ArrayList();
   private World worldObj;

   public ChunkProviderClient(World par1World) {
      this.blankChunk = new EmptyChunk(par1World, 0, 0);
      this.worldObj = par1World;
   }

   public boolean chunkExists(int par1, int par2) {
      return true;
   }

   public void unloadChunk(int par1, int par2) {
      Chunk var3 = this.provideChunk(par1, par2);
      if(!var3.isEmpty()) {
         var3.onChunkUnload();
      }

      this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
      this.chunkListing.remove(var3);
   }

   public Chunk loadChunk(int par1, int par2) {
      Chunk var3 = new Chunk(this.worldObj, par1, par2);
      this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2), var3);
      var3.isChunkLoaded = true;
      return var3;
   }

   public Chunk provideChunk(int par1, int par2) {
      Chunk var3 = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
      return var3 == null?this.blankChunk:var3;
   }

   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
      return true;
   }

   public void saveExtraData() {}

   public boolean unloadQueuedChunks() {
      return false;
   }

   public boolean canSave() {
      return false;
   }

   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {}

   public String makeString() {
      return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements();
   }

   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
      return null;
   }

   public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5) {
      return null;
   }

   public int getLoadedChunkCount() {
      return this.chunkListing.size();
   }

   public void recreateStructures(int par1, int par2) {}
}
