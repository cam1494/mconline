package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable {
   final CrashReport theCrashReport;

   CallableMinecraftVersion(CrashReport par1CrashReport) {
      this.theCrashReport = par1CrashReport;
   }

   public String minecraftVersion() {
      return "1.6.4";
   }

   public Object call() {
      return this.minecraftVersion();
   }
}
