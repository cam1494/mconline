package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class StatsSyncher {
   private volatile boolean isBusy;
   private volatile Map field_77430_b;
   private volatile Map field_77431_c;
   private StatFileWriter statFileWriter;
   private File unsentDataFile;
   private File dataFile;
   private File unsentTempFile;
   private File tempFile;
   private File unsentOldFile;
   private File oldFile;
   private Session theSession;
   private int field_77433_l;
   private int field_77434_m;

   public StatsSyncher(Session par1Session, StatFileWriter par2StatFileWriter, File par3File) {
      String var4 = par1Session.getUsername();
      String var5 = var4.toLowerCase();
      this.unsentDataFile = new File(par3File, "stats_" + var5 + "_unsent.dat");
      this.dataFile = new File(par3File, "stats_" + var5 + ".dat");
      this.unsentOldFile = new File(par3File, "stats_" + var5 + "_unsent.old");
      this.oldFile = new File(par3File, "stats_" + var5 + ".old");
      this.unsentTempFile = new File(par3File, "stats_" + var5 + "_unsent.tmp");
      this.tempFile = new File(par3File, "stats_" + var5 + ".tmp");
      if(!var5.equals(var4)) {
         this.func_77412_a(par3File, "stats_" + var4 + "_unsent.dat", this.unsentDataFile);
         this.func_77412_a(par3File, "stats_" + var4 + ".dat", this.dataFile);
         this.func_77412_a(par3File, "stats_" + var4 + "_unsent.old", this.unsentOldFile);
         this.func_77412_a(par3File, "stats_" + var4 + ".old", this.oldFile);
         this.func_77412_a(par3File, "stats_" + var4 + "_unsent.tmp", this.unsentTempFile);
         this.func_77412_a(par3File, "stats_" + var4 + ".tmp", this.tempFile);
      }

      this.statFileWriter = par2StatFileWriter;
      this.theSession = par1Session;
      if(this.unsentDataFile.exists()) {
         par2StatFileWriter.writeStats(this.func_77417_a(this.unsentDataFile, this.unsentTempFile, this.unsentOldFile));
      }

      this.beginReceiveStats();
   }

   private void func_77412_a(File par1File, String par2Str, File par3File) {
      File var4 = new File(par1File, par2Str);
      if(var4.exists() && !var4.isDirectory() && !par3File.exists()) {
         var4.renameTo(par3File);
      }
   }

   private Map func_77417_a(File par1File, File par2File, File par3File) {
      return par1File.exists()?this.func_77413_a(par1File):(par3File.exists()?this.func_77413_a(par3File):(par2File.exists()?this.func_77413_a(par2File):null));
   }

   private Map func_77413_a(File par1File) {
      BufferedReader var2 = null;

      try {
         var2 = new BufferedReader(new FileReader(par1File));
         String var3 = "";
         StringBuilder var4 = new StringBuilder();

         while((var3 = var2.readLine()) != null) {
            var4.append(var3);
         }

         Map var5 = StatFileWriter.func_77453_b(var4.toString());
         return var5;
      } catch (Exception var15) {
         var15.printStackTrace();
      } finally {
         if(var2 != null) {
            try {
               var2.close();
            } catch (Exception var14) {
               var14.printStackTrace();
            }
         }
      }

      return null;
   }

   private void func_77421_a(Map par1Map, File par2File, File par3File, File par4File) throws IOException {
      PrintWriter var5 = new PrintWriter(new FileWriter(par3File, false));

      try {
         var5.print(StatFileWriter.func_77441_a(this.theSession.getUsername(), "local", par1Map));
      } finally {
         var5.close();
      }

      if(par4File.exists()) {
         par4File.delete();
      }

      if(par2File.exists()) {
         par2File.renameTo(par4File);
      }

      par3File.renameTo(par2File);
   }

   public void beginReceiveStats() {
      if(this.isBusy) {
         throw new IllegalStateException("Can\'t get stats from server while StatsSyncher is busy!");
      } else {
         this.field_77433_l = 100;
         this.isBusy = true;
         (new ThreadStatSyncherReceive(this)).start();
      }
   }

   public void beginSendStats(Map par1Map) {
      if(this.isBusy) {
         throw new IllegalStateException("Can\'t save stats while StatsSyncher is busy!");
      } else {
         this.field_77433_l = 100;
         this.isBusy = true;
         (new ThreadStatSyncherSend(this, par1Map)).start();
      }
   }

   public void syncStatsFileWithMap(Map par1Map) {
      int var2 = 30;

      while(this.isBusy) {
         --var2;
         if(var2 <= 0) {
            break;
         }

         try {
            Thread.sleep(100L);
         } catch (InterruptedException var10) {
            var10.printStackTrace();
         }
      }

      this.isBusy = true;

      try {
         this.func_77421_a(par1Map, this.unsentDataFile, this.unsentTempFile, this.unsentOldFile);
      } catch (Exception var8) {
         var8.printStackTrace();
      } finally {
         this.isBusy = false;
      }
   }

   public boolean func_77425_c() {
      return this.field_77433_l <= 0 && !this.isBusy && this.field_77431_c == null;
   }

   public void func_77422_e() {
      if(this.field_77433_l > 0) {
         --this.field_77433_l;
      }

      if(this.field_77434_m > 0) {
         --this.field_77434_m;
      }

      if(this.field_77431_c != null) {
         this.statFileWriter.func_77448_c(this.field_77431_c);
         this.field_77431_c = null;
      }

      if(this.field_77430_b != null) {
         this.statFileWriter.func_77452_b(this.field_77430_b);
         this.field_77430_b = null;
      }
   }

   static Map func_77419_a(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.field_77430_b;
   }

   static File func_77408_b(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.dataFile;
   }

   static File func_77407_c(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.tempFile;
   }

   static File func_77411_d(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.oldFile;
   }

   static void func_77414_a(StatsSyncher par0StatsSyncher, Map par1Map, File par2File, File par3File, File par4File) throws IOException {
      par0StatsSyncher.func_77421_a(par1Map, par2File, par3File, par4File);
   }

   static Map func_77416_a(StatsSyncher par0StatsSyncher, Map par1Map) {
      return par0StatsSyncher.field_77430_b = par1Map;
   }

   static Map func_77410_a(StatsSyncher par0StatsSyncher, File par1File, File par2File, File par3File) {
      return par0StatsSyncher.func_77417_a(par1File, par2File, par3File);
   }

   static boolean setBusy(StatsSyncher par0StatsSyncher, boolean par1) {
      return par0StatsSyncher.isBusy = par1;
   }

   static File getUnsentDataFile(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.unsentDataFile;
   }

   static File getUnsentTempFile(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.unsentTempFile;
   }

   static File getUnsentOldFile(StatsSyncher par0StatsSyncher) {
      return par0StatsSyncher.unsentOldFile;
   }
}
