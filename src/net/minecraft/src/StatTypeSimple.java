package net.minecraft.src;

final class StatTypeSimple implements IStatType {
   public String format(int par1) {
      return StatBase.getNumberFormat().format((long)par1);
   }
}
