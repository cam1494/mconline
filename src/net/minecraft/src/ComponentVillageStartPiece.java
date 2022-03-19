package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComponentVillageStartPiece extends ComponentVillageWell {
   public WorldChunkManager worldChunkMngr;
   public boolean inDesert;
   public int terrainType;
   public StructureVillagePieceWeight structVillagePieceWeight;
   public List structureVillageWeightedPieceList;
   public List field_74932_i = new ArrayList();
   public List field_74930_j = new ArrayList();

   public ComponentVillageStartPiece() {}

   public ComponentVillageStartPiece(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, List par6List, int par7) {
      super((ComponentVillageStartPiece)null, 0, par3Random, par4, par5);
      this.worldChunkMngr = par1WorldChunkManager;
      this.structureVillageWeightedPieceList = par6List;
      this.terrainType = par7;
      BiomeGenBase var8 = par1WorldChunkManager.getBiomeGenAt(par4, par5);
      this.inDesert = var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills;
   }

   public WorldChunkManager getWorldChunkManager() {
      return this.worldChunkMngr;
   }
}
