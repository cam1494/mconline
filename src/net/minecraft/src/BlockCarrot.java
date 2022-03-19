package net.minecraft.src;

public class BlockCarrot extends BlockCrops {
   private Icon[] iconArray;

   public BlockCarrot(int par1) {
      super(par1);
   }

   public Icon getIcon(int par1, int par2) {
      if(par2 < 7) {
         if(par2 == 6) {
            par2 = 5;
         }

         return this.iconArray[par2 >> 1];
      } else {
         return this.iconArray[3];
      }
   }

   protected int getSeedItem() {
      return Item.carrot.itemID;
   }

   protected int getCropItem() {
      return Item.carrot.itemID;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[4];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + var2);
      }
   }
}
