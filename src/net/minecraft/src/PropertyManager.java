package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
   private final Properties properties = new Properties();
   private final ILogAgent logger;
   private final File associatedFile;

   public PropertyManager(File par1File, ILogAgent par2ILogAgent) {
      this.associatedFile = par1File;
      this.logger = par2ILogAgent;
      if(par1File.exists()) {
         FileInputStream var3 = null;

         try {
            var3 = new FileInputStream(par1File);
            this.properties.load(var3);
         } catch (Exception var13) {
            par2ILogAgent.logWarningException("Failed to load " + par1File, var13);
            this.logMessageAndSave();
         } finally {
            if(var3 != null) {
               try {
                  var3.close();
               } catch (IOException var12) {
                  ;
               }
            }
         }
      } else {
         par2ILogAgent.logWarning(par1File + " does not exist");
         this.logMessageAndSave();
      }
   }

   public void logMessageAndSave() {
      this.logger.logInfo("Generating new properties file");
      this.saveProperties();
   }

   public void saveProperties() {
      FileOutputStream var1 = null;

      try {
         var1 = new FileOutputStream(this.associatedFile);
         this.properties.store(var1, "Minecraft server properties");
      } catch (Exception var11) {
         this.logger.logWarningException("Failed to save " + this.associatedFile, var11);
         this.logMessageAndSave();
      } finally {
         if(var1 != null) {
            try {
               var1.close();
            } catch (IOException var10) {
               ;
            }
         }
      }
   }

   public File getPropertiesFile() {
      return this.associatedFile;
   }

   public String getProperty(String par1Str, String par2Str) {
      if(!this.properties.containsKey(par1Str)) {
         this.properties.setProperty(par1Str, par2Str);
         this.saveProperties();
      }

      return this.properties.getProperty(par1Str, par2Str);
   }

   public int getIntProperty(String par1Str, int par2) {
      try {
         return Integer.parseInt(this.getProperty(par1Str, "" + par2));
      } catch (Exception var4) {
         this.properties.setProperty(par1Str, "" + par2);
         return par2;
      }
   }

   public boolean getBooleanProperty(String par1Str, boolean par2) {
      try {
         return Boolean.parseBoolean(this.getProperty(par1Str, "" + par2));
      } catch (Exception var4) {
         this.properties.setProperty(par1Str, "" + par2);
         return par2;
      }
   }

   public void setProperty(String par1Str, Object par2Obj) {
      this.properties.setProperty(par1Str, "" + par2Obj);
   }
}
