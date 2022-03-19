package net.minecraft.src;

import java.util.Random;

public class BlockMushroomCap extends Block {
   private static final String[] field_94429_a = new String[]{"skin_brown", "skin_red"};
   private final int mushroomType;
   private Icon[] iconArray;
   private Icon field_94426_cO;
   private Icon field_94427_cP;

   public BlockMushroomCap(int par1, Material par2Material, int par3) {
      super(par1, par2Material);
      this.mushroomType = par3;
   }

   public Icon getIcon(int par1, int par2) {
      return par2 == 10 && par1 > 1?this.field_94426_cO:(par2 >= 1 && par2 <= 9 && par1 == 1?this.iconArray[this.mushroomType]:(par2 >= 1 && par2 <= 3 && par1 == 2?this.iconArray[this.mushroomType]:(par2 >= 7 && par2 <= 9 && par1 == 3?this.iconArray[this.mushroomType]:((par2 == 1 || par2 == 4 || par2 == 7) && par1 == 4?this.iconArray[this.mushroomType]:((par2 == 3 || par2 == 6 || par2 == 9) && par1 == 5?this.iconArray[this.mushroomType]:(par2 == 14?this.iconArray[this.mushroomType]:(par2 == 15?this.field_94426_cO:this.field_94427_cP)))))));
   }

   public int quantityDropped(Random par1Random) {
      int var2 = par1Random.nextInt(10) - 7;
      if(var2 < 0) {
         var2 = 0;
      }

      return var2;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.mushroomBrown.blockID + this.mushroomType;
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Block.mushroomBrown.blockID + this.mushroomType;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[field_94429_a.length];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_" + field_94429_a[var2]);
      }

      this.field_94427_cP = par1IconRegister.registerIcon(this.getTextureName() + "_" + "inside");
      this.field_94426_cO = par1IconRegister.registerIcon(this.getTextureName() + "_" + "skin_stem");
   }
}
