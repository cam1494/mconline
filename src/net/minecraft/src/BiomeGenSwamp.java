package net.minecraft.src;

import java.util.Random;

public class BiomeGenSwamp extends BiomeGenBase {
   protected BiomeGenSwamp(int par1) {
      super(par1);
      this.theBiomeDecorator.treesPerChunk = 2;
      this.theBiomeDecorator.flowersPerChunk = -999;
      this.theBiomeDecorator.deadBushPerChunk = 1;
      this.theBiomeDecorator.mushroomsPerChunk = 8;
      this.theBiomeDecorator.reedsPerChunk = 10;
      this.theBiomeDecorator.clayPerChunk = 1;
      this.theBiomeDecorator.waterlilyPerChunk = 4;
      this.waterColorMultiplier = 14745518;
      this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
   }

   public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
      return this.worldGeneratorSwamp;
   }

   public int getBiomeGrassColor() {
      double var1 = (double)this.getFloatTemperature();
      double var3 = (double)this.getFloatRainfall();
      return ((ColorizerGrass.getGrassColor(var1, var3) & 16711422) + 5115470) / 2;
   }

   public int getBiomeFoliageColor() {
      double var1 = (double)this.getFloatTemperature();
      double var3 = (double)this.getFloatRainfall();
      return ((ColorizerFoliage.getFoliageColor(var1, var3) & 16711422) + 5115470) / 2;
   }
}
