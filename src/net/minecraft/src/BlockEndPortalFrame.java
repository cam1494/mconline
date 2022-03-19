package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockEndPortalFrame extends Block {
   private Icon field_94400_a;
   private Icon field_94399_b;

   public BlockEndPortalFrame(int par1) {
      super(par1, Material.rock);
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 1?this.field_94400_a:(par1 == 0?Block.whiteStone.getBlockTextureFromSide(par1):this.blockIcon);
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
      this.field_94400_a = par1IconRegister.registerIcon(this.getTextureName() + "_top");
      this.field_94399_b = par1IconRegister.registerIcon(this.getTextureName() + "_eye");
   }

   public Icon func_94398_p() {
      return this.field_94399_b;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return 26;
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      int var8 = par1World.getBlockMetadata(par2, par3, par4);
      if(isEnderEyeInserted(var8)) {
         this.setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
         super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      }

      this.setBlockBoundsForItemRender();
   }

   public static boolean isEnderEyeInserted(int par0) {
      return (par0 & 4) != 0;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return 0;
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int var7 = ((MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
      par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      return isEnderEyeInserted(var6)?15:0;
   }
}
