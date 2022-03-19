package net.minecraft.src;

public class LanServer {
   private String lanServerMotd;
   private String lanServerIpPort;
   private long timeLastSeen;

   public LanServer(String par1Str, String par2Str) {
      this.lanServerMotd = par1Str;
      this.lanServerIpPort = par2Str;
      this.timeLastSeen = Minecraft.getSystemTime();
   }

   public String getServerMotd() {
      return this.lanServerMotd;
   }

   public String getServerIpPort() {
      return this.lanServerIpPort;
   }

   public void updateLastSeen() {
      this.timeLastSeen = Minecraft.getSystemTime();
   }
}
