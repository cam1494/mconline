package net.minecraft.src;

import java.util.Random;

public class BlockEnchantmentTable extends BlockContainer {
   private Icon field_94461_a;
   private Icon field_94460_b;

   protected BlockEnchantmentTable(int par1) {
      super(par1, Material.rock);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
      this.setLightOpacity(0);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

      for(int var6 = par2 - 2; var6 <= par2 + 2; ++var6) {
         for(int var7 = par4 - 2; var7 <= par4 + 2; ++var7) {
            if(var6 > par2 - 2 && var6 < par2 + 2 && var7 == par4 - 1) {
               var7 = par4 + 2;
            }

            if(par5Random.nextInt(16) == 0) {
               for(int var8 = par3; var8 <= par3 + 1; ++var8) {
                  if(par1World.getBlockId(var6, var8, var7) == Block.bookShelf.blockID) {
                     if(!par1World.isAirBlock((var6 - par2) / 2 + par2, var8, (var7 - par4) / 2 + par4)) {
                        break;
                     }

                     par1World.spawnParticle("enchantmenttable", (double)par2 + 0.5D, (double)par3 + 2.0D, (double)par4 + 0.5D, (double)((float)(var6 - par2) + par5Random.nextFloat()) - 0.5D, (double)((float)(var8 - par3) - par5Random.nextFloat() - 1.0F), (double)((float)(var7 - par4) + par5Random.nextFloat()) - 0.5D);
                  }
               }
            }
         }
      }
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 0?this.field_94460_b:(par1 == 1?this.field_94461_a:this.blockIcon);
   }

   public TileEntity createNewTileEntity(World par1World) {
      return new TileEntityEnchantmentTable();
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         TileEntityEnchantmentTable var10 = (TileEntityEnchantmentTable)par1World.getBlockTileEntity(par2, par3, par4);
         par5EntityPlayer.displayGUIEnchantment(par2, par3, par4, var10.func_94135_b()?var10.func_94133_a():null);
         return true;
      }
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
      if(par6ItemStack.hasDisplayName()) {
         ((TileEntityEnchantmentTable)par1World.getBlockTileEntity(par2, par3, par4)).func_94134_a(par6ItemStack.getDisplayName());
      }
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_" + "side");
      this.field_94461_a = par1IconRegister.registerIcon(this.getTextureName() + "_" + "top");
      this.field_94460_b = par1IconRegister.registerIcon(this.getTextureName() + "_" + "bottom");
   }
}
