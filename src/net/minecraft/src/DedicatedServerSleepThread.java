package net.minecraft.src;

class DedicatedServerSleepThread extends Thread {
   final DedicatedServer theDecitatedServer;

   DedicatedServerSleepThread(DedicatedServer par1DedicatedServer) {
      this.theDecitatedServer = par1DedicatedServer;
      this.setDaemon(true);
      this.start();
   }

   public void run() {
      while(true) {
         try {
            while(true) {
               Thread.sleep(2147483647L);
            }
         } catch (InterruptedException var2) {
            ;
         }
      }
   }
}
