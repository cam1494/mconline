package net.minecraft.src;

class StatStringFormatKeyInv implements IStatStringFormat {
   final Minecraft mc;

   StatStringFormatKeyInv(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
   }

   public String formatString(String par1Str) {
      try {
         return String.format(par1Str, new Object[]{GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindInventory.keyCode)});
      } catch (Exception var3) {
         return "Error: " + var3.getLocalizedMessage();
      }
   }
}
