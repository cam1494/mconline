package net.minecraft.src;

public class BlockButtonStone extends BlockButton {
   protected BlockButtonStone(int par1) {
      super(par1, false);
   }

   public Icon getIcon(int par1, int par2) {
      return Block.stone.getBlockTextureFromSide(1);
   }
}
