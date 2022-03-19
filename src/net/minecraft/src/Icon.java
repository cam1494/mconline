package net.minecraft.src;

public interface Icon {
   int getIconWidth();

   int getIconHeight();

   float getMinU();

   float getMaxU();

   float getInterpolatedU(double var1);

   float getMinV();

   float getMaxV();

   float getInterpolatedV(double var1);

   String getIconName();
}
