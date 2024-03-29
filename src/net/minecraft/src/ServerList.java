package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServerList {
   private final Minecraft mc;
   private final List servers = new ArrayList();

   public ServerList(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
      this.loadServerList();
   }

   public void loadServerList() {
      try {
         this.servers.clear();
         NBTTagCompound var1 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
         if(var1 == null) {
            return;
         }

         NBTTagList var2 = var1.getTagList("servers");

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            this.servers.add(ServerData.getServerDataFromNBTCompound((NBTTagCompound)var2.tagAt(var3)));
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public void saveServerList() {
      try {
         NBTTagList var1 = new NBTTagList();
         Iterator var2 = this.servers.iterator();

         while(var2.hasNext()) {
            ServerData var3 = (ServerData)var2.next();
            var1.appendTag(var3.getNBTCompound());
         }

         NBTTagCompound var5 = new NBTTagCompound();
         var5.setTag("servers", var1);
         CompressedStreamTools.safeWrite(var5, new File(this.mc.mcDataDir, "servers.dat"));
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   public ServerData getServerData(int par1) {
      return (ServerData)this.servers.get(par1);
   }

   public void removeServerData(int par1) {
      this.servers.remove(par1);
   }

   public void addServerData(ServerData par1ServerData) {
      this.servers.add(par1ServerData);
   }

   public int countServers() {
      return this.servers.size();
   }

   public void swapServers(int par1, int par2) {
      ServerData var3 = this.getServerData(par1);
      this.servers.set(par1, this.getServerData(par2));
      this.servers.set(par2, var3);
      this.saveServerList();
   }
}
