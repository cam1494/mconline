package net.minecraft.src;

import java.io.File;

public class SaveHandlerMP implements ISaveHandler {
   public WorldInfo loadWorldInfo() {
      return null;
   }

   public void checkSessionLock() throws MinecraftException {}

   public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider) {
      return null;
   }

   public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound) {}

   public void saveWorldInfo(WorldInfo par1WorldInfo) {}

   public IPlayerFileData getSaveHandler() {
      return null;
   }

   public void flush() {}

   public File getMapFileFromName(String par1Str) {
      return null;
   }

   public String getWorldDirectoryName() {
      return "none";
   }
}
