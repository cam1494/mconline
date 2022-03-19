package net.minecraft.src;

import java.util.Random;

public class BlockCocoa extends BlockDirectional {
   private Icon[] iconArray;

   public BlockCocoa(int par1) {
      super(par1, Material.plants);
      this.setTickRandomly(true);
   }

   public Icon getIcon(int par1, int par2) {
      return this.iconArray[2];
   }

   public Icon getCocoaIcon(int par1) {
      if(par1 < 0 || par1 >= this.iconArray.length) {
         par1 = this.iconArray.length - 1;
      }

      return this.iconArray[par1];
   }

   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if(!this.canBlockStay(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlock(par2, par3, par4, 0, 0, 2);
      } else if(par1World.rand.nextInt(5) == 0) {
         int var6 = par1World.getBlockMetadata(par2, par3, par4);
         int var7 = func_72219_c(var6);
         if(var7 < 2) {
            ++var7;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 << 2 | getDirection(var6), 2);
         }
      }
   }

   public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
      int var5 = getDirection(par1World.getBlockMetadata(par2, par3, par4));
      par2 += Direction.offsetX[var5];
      par4 += Direction.offsetZ[var5];
      int var6 = par1World.getBlockId(par2, par3, par4);
      return var6 == Block.wood.blockID && BlockLog.limitToValidMetadata(par1World.getBlockMetadata(par2, par3, par4)) == 3;
   }

   public int getRenderType() {
      return 28;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
      return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      int var6 = getDirection(var5);
      int var7 = func_72219_c(var5);
      int var8 = 4 + var7 * 2;
      int var9 = 5 + var7 * 2;
      float var10 = (float)var8 / 2.0F;
      switch(var6) {
      case 0:
         this.setBlockBounds((8.0F - var10) / 16.0F, (12.0F - (float)var9) / 16.0F, (15.0F - (float)var8) / 16.0F, (8.0F + var10) / 16.0F, 0.75F, 0.9375F);
         break;
      case 1:
         this.setBlockBounds(0.0625F, (12.0F - (float)var9) / 16.0F, (8.0F - var10) / 16.0F, (1.0F + (float)var8) / 16.0F, 0.75F, (8.0F + var10) / 16.0F);
         break;
      case 2:
         this.setBlockBounds((8.0F - var10) / 16.0F, (12.0F - (float)var9) / 16.0F, 0.0625F, (8.0F + var10) / 16.0F, 0.75F, (1.0F + (float)var8) / 16.0F);
         break;
      case 3:
         this.setBlockBounds((15.0F - (float)var8) / 16.0F, (12.0F - (float)var9) / 16.0F, (8.0F - var10) / 16.0F, 0.9375F, 0.75F, (8.0F + var10) / 16.0F);
      }
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int var7 = ((MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 0) % 4;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      if(par5 == 1 || par5 == 0) {
         par5 = 2;
      }

      return Direction.rotateOpposite[Direction.facingToDirection[par5]];
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      if(!this.canBlockStay(par1World, par2, par3, par4)) {
         this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
         par1World.setBlock(par2, par3, par4, 0, 0, 2);
      }
   }

   public static int func_72219_c(int par0) {
      return (par0 & 12) >> 2;
   }

   public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
      int var8 = func_72219_c(par5);
      byte var9 = 1;
      if(var8 >= 2) {
         var9 = 3;
      }

      for(int var10 = 0; var10 < var9; ++var10) {
         this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.dyePowder, 1, 3));
      }
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Item.dyePowder.itemID;
   }

   public int getDamageValue(World par1World, int par2, int par3, int par4) {
      return 3;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[3];

      for(int var2 = 0; var2 < this.iconArray.length; ++var2) {
         this.iconArray[var2] = par1IconRegister.registerIcon(this.getTextureName() + "_stage_" + var2);
      }
   }
}
