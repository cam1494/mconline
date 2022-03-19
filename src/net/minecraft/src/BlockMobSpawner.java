package net.minecraft.src;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer {
   protected BlockMobSpawner(int par1) {
      super(par1, Material.rock);
   }

   public TileEntity createNewTileEntity(World par1World) {
      return new TileEntityMobSpawner();
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return 0;
   }

   public int quantityDropped(Random par1Random) {
      return 0;
   }

   public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
      super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
      int var8 = 15 + par1World.rand.nextInt(15) + par1World.rand.nextInt(15);
      this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return 0;
   }
}
