package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public abstract class NetworkListenThread {
   private final MinecraftServer mcServer;
   private final List connections = Collections.synchronizedList(new ArrayList());
   public volatile boolean isListening;

   public NetworkListenThread(MinecraftServer par1MinecraftServer) throws IOException {
      this.mcServer = par1MinecraftServer;
      this.isListening = true;
   }

   public void addPlayer(NetServerHandler par1NetServerHandler) {
      this.connections.add(par1NetServerHandler);
   }

   public void stopListening() {
      this.isListening = false;
   }

   public void networkTick() {
      for(int var1 = 0; var1 < this.connections.size(); ++var1) {
         NetServerHandler var2 = (NetServerHandler)this.connections.get(var1);

         try {
            var2.networkTick();
         } catch (Exception var6) {
            if(var2.netManager instanceof MemoryConnection) {
               CrashReport var4 = CrashReport.makeCrashReport(var6, "Ticking memory connection");
               CrashReportCategory var5 = var4.makeCategory("Ticking connection");
               var5.addCrashSectionCallable("Connection", new CallableConnectionName(this, var2));
               throw new ReportedException(var4);
            }

            this.mcServer.getLogAgent().logWarningException("Failed to handle packet for " + var2.playerEntity.getEntityName() + "/" + var2.playerEntity.getPlayerIP() + ": " + var6, var6);
            var2.kickPlayerFromServer("Internal server error");
         }

         if(var2.connectionClosed) {
            this.connections.remove(var1--);
         }

         var2.netManager.wakeThreads();
      }
   }

   public MinecraftServer getServer() {
      return this.mcServer;
   }
}
