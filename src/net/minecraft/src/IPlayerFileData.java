package net.minecraft.src;

public interface IPlayerFileData {
   void writePlayerData(EntityPlayer var1);

   NBTTagCompound readPlayerData(EntityPlayer var1);

   String[] getAvailablePlayerDat();
}
