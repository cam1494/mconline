package net.minecraft.src;

import java.util.List;

public interface ISaveFormat {
   ISaveHandler getSaveLoader(String var1, boolean var2);

   List getSaveList() throws AnvilConverterException;

   void flushCache();

   WorldInfo getWorldInfo(String var1);

   boolean deleteWorldDirectory(String var1);

   void renameWorld(String var1, String var2);

   boolean isOldMapFormat(String var1);

   boolean convertMapFormat(String var1, IProgressUpdate var2);

   boolean canLoadWorld(String var1);
}
