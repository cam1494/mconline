package net.minecraft.src;

public class TileEntityDropper extends TileEntityDispenser {
   public String getInvName() {
      return this.isInvNameLocalized()?this.customName:"container.dropper";
   }
}
