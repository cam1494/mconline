package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class WorldClient extends World {
   private NetClientHandler sendQueue;
   private ChunkProviderClient clientChunkProvider;
   private IntHashMap entityHashSet = new IntHashMap();
   private Set entityList = new HashSet();
   private Set entitySpawnQueue = new HashSet();
   private final Minecraft mc = Minecraft.getMinecraft();
   private final Set previousActiveChunkSet = new HashSet();

   public WorldClient(NetClientHandler par1NetClientHandler, WorldSettings par2WorldSettings, int par3, int par4, Profiler par5Profiler, ILogAgent par6ILogAgent) {
      super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(par3), par2WorldSettings, par5Profiler, par6ILogAgent);
      this.sendQueue = par1NetClientHandler;
      this.difficultySetting = par4;
      this.setSpawnLocation(8, 64, 8);
      this.mapStorage = par1NetClientHandler.mapStorage;
   }

   public void tick() {
      super.tick();
      this.func_82738_a(this.getTotalWorldTime() + 1L);
      if(this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
         this.setWorldTime(this.getWorldTime() + 1L);
      }

      this.theProfiler.startSection("reEntryProcessing");

      for(int var1 = 0; var1 < 10 && !this.entitySpawnQueue.isEmpty(); ++var1) {
         Entity var2 = (Entity)this.entitySpawnQueue.iterator().next();
         this.entitySpawnQueue.remove(var2);
         if(!this.loadedEntityList.contains(var2)) {
            this.spawnEntityInWorld(var2);
         }
      }

      this.theProfiler.endStartSection("connection");
      this.sendQueue.processReadPackets();
      this.theProfiler.endStartSection("chunkCache");
      this.clientChunkProvider.unloadQueuedChunks();
      this.theProfiler.endStartSection("tiles");
      this.tickBlocksAndAmbiance();
      this.theProfiler.endSection();
   }

   public void invalidateBlockReceiveRegion(int par1, int par2, int par3, int par4, int par5, int par6) {}

   protected IChunkProvider createChunkProvider() {
      this.clientChunkProvider = new ChunkProviderClient(this);
      return this.clientChunkProvider;
   }

   protected void tickBlocksAndAmbiance() {
      super.tickBlocksAndAmbiance();
      this.previousActiveChunkSet.retainAll(this.activeChunkSet);
      if(this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
         this.previousActiveChunkSet.clear();
      }

      int var1 = 0;
      Iterator var2 = this.activeChunkSet.iterator();

      while(var2.hasNext()) {
         ChunkCoordIntPair var3 = (ChunkCoordIntPair)var2.next();
         if(!this.previousActiveChunkSet.contains(var3)) {
            int var4 = var3.chunkXPos * 16;
            int var5 = var3.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk var6 = this.getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
            this.moodSoundAndLightCheck(var4, var5, var6);
            this.theProfiler.endSection();
            this.previousActiveChunkSet.add(var3);
            ++var1;
            if(var1 >= 10) {
               return;
            }
         }
      }
   }

   public void doPreChunk(int par1, int par2, boolean par3) {
      if(par3) {
         this.clientChunkProvider.loadChunk(par1, par2);
      } else {
         this.clientChunkProvider.unloadChunk(par1, par2);
      }

      if(!par3) {
         this.markBlockRangeForRenderUpdate(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
      }
   }

   public boolean spawnEntityInWorld(Entity par1Entity) {
      boolean var2 = super.spawnEntityInWorld(par1Entity);
      this.entityList.add(par1Entity);
      if(!var2) {
         this.entitySpawnQueue.add(par1Entity);
      }

      return var2;
   }

   public void removeEntity(Entity par1Entity) {
      super.removeEntity(par1Entity);
      this.entityList.remove(par1Entity);
   }

   protected void onEntityAdded(Entity par1Entity) {
      super.onEntityAdded(par1Entity);
      if(this.entitySpawnQueue.contains(par1Entity)) {
         this.entitySpawnQueue.remove(par1Entity);
      }
   }

   protected void onEntityRemoved(Entity par1Entity) {
      super.onEntityRemoved(par1Entity);
      if(this.entityList.contains(par1Entity)) {
         if(par1Entity.isEntityAlive()) {
            this.entitySpawnQueue.add(par1Entity);
         } else {
            this.entityList.remove(par1Entity);
         }
      }
   }

   public void addEntityToWorld(int par1, Entity par2Entity) {
      Entity var3 = this.getEntityByID(par1);
      if(var3 != null) {
         this.removeEntity(var3);
      }

      this.entityList.add(par2Entity);
      par2Entity.entityId = par1;
      if(!this.spawnEntityInWorld(par2Entity)) {
         this.entitySpawnQueue.add(par2Entity);
      }

      this.entityHashSet.addKey(par1, par2Entity);
   }

   public Entity getEntityByID(int par1) {
      return (Entity)(par1 == this.mc.thePlayer.entityId?this.mc.thePlayer:(Entity)this.entityHashSet.lookup(par1));
   }

   public Entity removeEntityFromWorld(int par1) {
      Entity var2 = (Entity)this.entityHashSet.removeObject(par1);
      if(var2 != null) {
         this.entityList.remove(var2);
         this.removeEntity(var2);
      }

      return var2;
   }

   public boolean setBlockAndMetadataAndInvalidate(int par1, int par2, int par3, int par4, int par5) {
      this.invalidateBlockReceiveRegion(par1, par2, par3, par1, par2, par3);
      return super.setBlock(par1, par2, par3, par4, par5, 3);
   }

   public void sendQuittingDisconnectingPacket() {
      this.sendQueue.quitWithPacket(new Packet255KickDisconnect("Quitting"));
   }

   public IUpdatePlayerListBox getMinecartSoundUpdater(EntityMinecart par1EntityMinecart) {
      return new SoundUpdaterMinecart(this.mc.sndManager, par1EntityMinecart, this.mc.thePlayer);
   }

   protected void updateWeather() {
      if(!this.provider.hasNoSky) {
         this.prevRainingStrength = this.rainingStrength;
         if(this.worldInfo.isRaining()) {
            this.rainingStrength = (float)((double)this.rainingStrength + 0.01D);
         } else {
            this.rainingStrength = (float)((double)this.rainingStrength - 0.01D);
         }

         if(this.rainingStrength < 0.0F) {
            this.rainingStrength = 0.0F;
         }

         if(this.rainingStrength > 1.0F) {
            this.rainingStrength = 1.0F;
         }

         this.prevThunderingStrength = this.thunderingStrength;
         if(this.worldInfo.isThundering()) {
            this.thunderingStrength = (float)((double)this.thunderingStrength + 0.01D);
         } else {
            this.thunderingStrength = (float)((double)this.thunderingStrength - 0.01D);
         }

         if(this.thunderingStrength < 0.0F) {
            this.thunderingStrength = 0.0F;
         }

         if(this.thunderingStrength > 1.0F) {
            this.thunderingStrength = 1.0F;
         }
      }
   }

   public void doVoidFogParticles(int par1, int par2, int par3) {
      byte var4 = 16;
      Random var5 = new Random();

      for(int var6 = 0; var6 < 1000; ++var6) {
         int var7 = par1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var8 = par2 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var9 = par3 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
         int var10 = this.getBlockId(var7, var8, var9);
         if(var10 == 0 && this.rand.nextInt(8) > var8 && this.provider.getWorldHasVoidParticles()) {
            this.spawnParticle("depthsuspend", (double)((float)var7 + this.rand.nextFloat()), (double)((float)var8 + this.rand.nextFloat()), (double)((float)var9 + this.rand.nextFloat()), 0.0D, 0.0D, 0.0D);
         } else if(var10 > 0) {
            Block.blocksList[var10].randomDisplayTick(this, var7, var8, var9, var5);
         }
      }
   }

   public void removeAllEntities() {
      this.loadedEntityList.removeAll(this.unloadedEntityList);

      int var1;
      Entity var2;
      int var3;
      int var4;
      for(var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
         var2 = (Entity)this.unloadedEntityList.get(var1);
         var3 = var2.chunkCoordX;
         var4 = var2.chunkCoordZ;
         if(var2.addedToChunk && this.chunkExists(var3, var4)) {
            this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
         }
      }

      for(var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
         this.onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
      }

      this.unloadedEntityList.clear();

      for(var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
         var2 = (Entity)this.loadedEntityList.get(var1);
         if(var2.ridingEntity != null) {
            if(!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
               continue;
            }

            var2.ridingEntity.riddenByEntity = null;
            var2.ridingEntity = null;
         }

         if(var2.isDead) {
            var3 = var2.chunkCoordX;
            var4 = var2.chunkCoordZ;
            if(var2.addedToChunk && this.chunkExists(var3, var4)) {
               this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
            }

            this.loadedEntityList.remove(var1--);
            this.onEntityRemoved(var2);
         }
      }
   }

   public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport) {
      CrashReportCategory var2 = super.addWorldInfoToCrashReport(par1CrashReport);
      var2.addCrashSectionCallable("Forced entities", new CallableMPL1(this));
      var2.addCrashSectionCallable("Retry entities", new CallableMPL2(this));
      var2.addCrashSectionCallable("Server brand", new WorldClientINNER3(this));
      var2.addCrashSectionCallable("Server type", new WorldClientINNER4(this));
      return var2;
   }

   public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {
      float var11 = 16.0F;
      if(par8 > 1.0F) {
         var11 *= par8;
      }

      double var12 = this.mc.renderViewEntity.getDistanceSq(par1, par3, par5);
      if(var12 < (double)(var11 * var11)) {
         if(par10 && var12 > 100.0D) {
            double var14 = Math.sqrt(var12) / 40.0D;
            this.mc.sndManager.func_92070_a(par7Str, (float)par1, (float)par3, (float)par5, par8, par9, (int)Math.round(var14 * 20.0D));
         } else {
            this.mc.sndManager.playSound(par7Str, (float)par1, (float)par3, (float)par5, par8, par9);
         }
      }
   }

   public void func_92088_a(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound) {
      this.mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, par1, par3, par5, par7, par9, par11, this.mc.effectRenderer, par13NBTTagCompound));
   }

   public void func_96443_a(Scoreboard par1Scoreboard) {
      this.worldScoreboard = par1Scoreboard;
   }

   public void setWorldTime(long par1) {
      if(par1 < 0L) {
         par1 = -par1;
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
      } else {
         this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
      }

      super.setWorldTime(par1);
   }

   static Set getEntityList(WorldClient par0WorldClient) {
      return par0WorldClient.entityList;
   }

   static Set getEntitySpawnQueue(WorldClient par0WorldClient) {
      return par0WorldClient.entitySpawnQueue;
   }

   static Minecraft func_142030_c(WorldClient par0WorldClient) {
      return par0WorldClient.mc;
   }
}
