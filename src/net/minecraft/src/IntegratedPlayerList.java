package net.minecraft.src;

import java.net.SocketAddress;
import net.minecraft.server.MinecraftServer;

public class IntegratedPlayerList extends ServerConfigurationManager {
   private NBTTagCompound hostPlayerData;

   public IntegratedPlayerList(IntegratedServer par1IntegratedServer) {
      super(par1IntegratedServer);
      this.viewDistance = 10;
   }

   protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP) {
      if(par1EntityPlayerMP.getCommandSenderName().equals(this.getIntegratedServer().getServerOwner())) {
         this.hostPlayerData = new NBTTagCompound();
         par1EntityPlayerMP.writeToNBT(this.hostPlayerData);
      }

      super.writePlayerData(par1EntityPlayerMP);
   }

   public String allowUserToConnect(SocketAddress par1SocketAddress, String par2Str) {
      return par2Str.equalsIgnoreCase(this.getIntegratedServer().getServerOwner())?"That name is already taken.":super.allowUserToConnect(par1SocketAddress, par2Str);
   }

   public IntegratedServer getIntegratedServer() {
      return (IntegratedServer)super.getServerInstance();
   }

   public NBTTagCompound getHostPlayerData() {
      return this.hostPlayerData;
   }

   public MinecraftServer getServerInstance() {
      return this.getIntegratedServer();
   }
}
