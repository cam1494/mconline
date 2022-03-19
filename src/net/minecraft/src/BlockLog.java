package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockLog extends BlockRotatedPillar {
   public static final String[] woodType = new String[]{"oak", "spruce", "birch", "jungle"};
   private Icon[] field_111052_c;
   private Icon[] tree_top;

   protected BlockLog(int par1) {
      super(par1, Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int quantityDropped(Random par1Random) {
      return 1;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.wood.blockID;
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      byte var7 = 4;
      int var8 = var7 + 1;
      if(par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
         for(int var9 = -var7; var9 <= var7; ++var9) {
            for(int var10 = -var7; var10 <= var7; ++var10) {
               for(int var11 = -var7; var11 <= var7; ++var11) {
                  int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);
                  if(var12 == Block.leaves.blockID) {
                     int var13 = par1World.getBlockMetadata(par2 + var9, par3 + var10, par4 + var11);
                     if((var13 & 8) == 0) {
                        par1World.setBlockMetadataWithNotify(par2 + var9, par3 + var10, par4 + var11, var13 | 8, 4);
                     }
                  }
               }
            }
         }
      }
   }

   protected Icon getSideIcon(int par1) {
      return this.field_111052_c[par1];
   }

   protected Icon getEndIcon(int par1) {
      return this.tree_top[par1];
   }

   public static int limitToValidMetadata(int par0) {
      return par0 & 3;
   }

   public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
      par3List.add(new ItemStack(par1, 1, 2));
      par3List.add(new ItemStack(par1, 1, 3));
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.field_111052_c = new Icon[woodType.length];
      this.tree_top = new Icon[woodType.length];

      for(int var2 = 0; var2 < this.field_111052_c.length; ++var2) {
         this.field_111052_c[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType[var2]);
         this.tree_top[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType[var2] + "_top");
      }
   }
}
