package net.minecraft.src;

public class BlockPotato extends BlockCrops {
   private Icon[] iconArray;

   public BlockPotato(int par1) {
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
      return Item.potato.itemID;
   }

   protected int getCropItem() {
      return Item.potato.itemID;
   }

   public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
      super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
      if(!par1World.isRemote) {
         if(par5 >= 7 && par1World.rand.nextInt(50) == 0) {
            this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.poisonousPotato));
         }
      }
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[4];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + var2);
      }
   }
}
