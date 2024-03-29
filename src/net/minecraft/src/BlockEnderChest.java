package net.minecraft.src;

import java.util.Random;

public class BlockEnderChest extends BlockContainer {
   protected BlockEnderChest(int par1) {
      super(par1, Material.rock);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return 22;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Block.obsidian.blockID;
   }

   public int quantityDropped(Random par1Random) {
      return 8;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      byte var7 = 0;
      int var8 = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      if(var8 == 0) {
         var7 = 2;
      }

      if(var8 == 1) {
         var7 = 5;
      }

      if(var8 == 2) {
         var7 = 3;
      }

      if(var8 == 3) {
         var7 = 4;
      }

      par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      InventoryEnderChest var10 = par5EntityPlayer.getInventoryEnderChest();
      TileEntityEnderChest var11 = (TileEntityEnderChest)par1World.getBlockTileEntity(par2, par3, par4);
      if(var10 != null && var11 != null) {
         if(par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
            return true;
         } else if(par1World.isRemote) {
            return true;
         } else {
            var10.setAssociatedChest(var11);
            par5EntityPlayer.displayGUIChest(var10);
            return true;
         }
      } else {
         return true;
      }
   }

   public TileEntity createNewTileEntity(World par1World) {
      return new TileEntityEnderChest();
   }

   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      for(int var6 = 0; var6 < 3; ++var6) {
         double var10000 = (double)((float)par2 + par5Random.nextFloat());
         double var9 = (double)((float)par3 + par5Random.nextFloat());
         var10000 = (double)((float)par4 + par5Random.nextFloat());
         double var13 = 0.0D;
         double var15 = 0.0D;
         double var17 = 0.0D;
         int var19 = par5Random.nextInt(2) * 2 - 1;
         int var20 = par5Random.nextInt(2) * 2 - 1;
         var13 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
         var15 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
         var17 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
         double var11 = (double)par4 + 0.5D + 0.25D * (double)var20;
         var17 = (double)(par5Random.nextFloat() * 1.0F * (float)var20);
         double var7 = (double)par2 + 0.5D + 0.25D * (double)var19;
         var13 = (double)(par5Random.nextFloat() * 1.0F * (float)var19);
         par1World.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
      }
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon("obsidian");
   }
}
