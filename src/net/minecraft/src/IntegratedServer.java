package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;

public class IntegratedServer extends MinecraftServer {
   private final Minecraft mc;
   private final WorldSettings theWorldSettings;
   private final ILogAgent serverLogAgent;
   private IntegratedServerListenThread theServerListeningThread;
   private boolean isGamePaused;
   private boolean isPublic;
   private ThreadLanServerPing lanServerPing;

   public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings) {
      super(new File(par1Minecraft.mcDataDir, "saves"));
      this.serverLogAgent = new LogAgent("Minecraft-Server", " [SERVER]", (new File(par1Minecraft.mcDataDir, "output-server.log")).getAbsolutePath());
      this.setServerOwner(par1Minecraft.getSession().getUsername());
      this.setFolderName(par2Str);
      this.setWorldName(par3Str);
      this.setDemo(par1Minecraft.isDemo());
      this.canCreateBonusChest(par4WorldSettings.isBonusChestEnabled());
      this.setBuildLimit(256);
      this.setConfigurationManager(new IntegratedPlayerList(this));
      this.mc = par1Minecraft;
      this.serverProxy = par1Minecraft.getProxy();
      this.theWorldSettings = par4WorldSettings;

      try {
         this.theServerListeningThread = new IntegratedServerListenThread(this);
      } catch (IOException var6) {
         throw new Error();
      }
   }

   protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType, String par6Str) {
      this.convertMapIfNeeded(par1Str);
      this.worldServers = new WorldServer[3];
      this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
      ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(par1Str, true);

      for(int var8 = 0; var8 < this.worldServers.length; ++var8) {
         byte var9 = 0;
         if(var8 == 1) {
            var9 = -1;
         }

         if(var8 == 2) {
            var9 = 1;
         }

         if(var8 == 0) {
            if(this.isDemo()) {
               this.worldServers[var8] = new DemoWorldServer(this, var7, par2Str, var9, this.theProfiler, this.getLogAgent());
            } else {
               this.worldServers[var8] = new WorldServer(this, var7, par2Str, var9, this.theWorldSettings, this.theProfiler, this.getLogAgent());
            }
         } else {
            this.worldServers[var8] = new WorldServerMulti(this, var7, par2Str, var9, this.theWorldSettings, this.worldServers[0], this.theProfiler, this.getLogAgent());
         }

         this.worldServers[var8].addWorldAccess(new WorldManager(this, this.worldServers[var8]));
         this.getConfigurationManager().setPlayerManager(this.worldServers);
      }

      this.setDifficultyForAllWorlds(this.getDifficulty());
      this.initialWorldChunkLoad();
   }

   protected boolean startServer() throws IOException {
      this.serverLogAgent.logInfo("Starting integrated minecraft server version 1.6.4");
      this.setOnlineMode(false);
      this.setCanSpawnAnimals(true);
      this.setCanSpawnNPCs(true);
      this.setAllowPvp(true);
      this.setAllowFlight(true);
      this.serverLogAgent.logInfo("Generating keypair");
      this.setKeyPair(CryptManager.createNewKeyPair());
      this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.func_82749_j());
      this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
      return true;
   }

   public void tick() {
      boolean var1 = this.isGamePaused;
      this.isGamePaused = this.theServerListeningThread.isGamePaused();
      if(!var1 && this.isGamePaused) {
         this.serverLogAgent.logInfo("Saving and pausing game...");
         this.getConfigurationManager().saveAllPlayerData();
         this.saveAllWorlds(false);
      }

      if(!this.isGamePaused) {
         super.tick();
      }
   }

   public boolean canStructuresSpawn() {
      return false;
   }

   public EnumGameType getGameType() {
      return this.theWorldSettings.getGameType();
   }

   public int getDifficulty() {
      return this.mc.gameSettings.difficulty;
   }

   public boolean isHardcore() {
      return this.theWorldSettings.getHardcoreEnabled();
   }

   protected File getDataDirectory() {
      return this.mc.mcDataDir;
   }

   public boolean isDedicatedServer() {
      return false;
   }

   public IntegratedServerListenThread getServerListeningThread() {
      return this.theServerListeningThread;
   }

   protected void finalTick(CrashReport par1CrashReport) {
      this.mc.crashed(par1CrashReport);
   }

   public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
      par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
      par1CrashReport.getCategory().addCrashSectionCallable("Type", new CallableType3(this));
      par1CrashReport.getCategory().addCrashSectionCallable("Is Modded", new CallableIsModded(this));
      return par1CrashReport;
   }

   public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
      super.addServerStatsToSnooper(par1PlayerUsageSnooper);
      par1PlayerUsageSnooper.addData("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
   }

   public boolean isSnooperEnabled() {
      return Minecraft.getMinecraft().isSnooperEnabled();
   }

   public String shareToLAN(EnumGameType par1EnumGameType, boolean par2) {
      try {
         String var3 = this.theServerListeningThread.func_71755_c();
         this.getLogAgent().logInfo("Started on " + var3);
         this.isPublic = true;
         this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), var3);
         this.lanServerPing.start();
         this.getConfigurationManager().setGameType(par1EnumGameType);
         this.getConfigurationManager().setCommandsAllowedForAll(par2);
         return var3;
      } catch (IOException var4) {
         return null;
      }
   }

   public ILogAgent getLogAgent() {
      return this.serverLogAgent;
   }

   public void stopServer() {
      super.stopServer();
      if(this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }
   }

   public void initiateShutdown() {
      super.initiateShutdown();
      if(this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }
   }

   public boolean getPublic() {
      return this.isPublic;
   }

   public void setGameType(EnumGameType par1EnumGameType) {
      this.getConfigurationManager().setGameType(par1EnumGameType);
   }

   public boolean isCommandBlockEnabled() {
      return true;
   }

   public int func_110455_j() {
      return 4;
   }

   public NetworkListenThread getNetworkThread() {
      return this.getServerListeningThread();
   }
}
