package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockCauldron extends Block {
   private Icon field_94378_a;
   private Icon cauldronTopIcon;
   private Icon cauldronBottomIcon;

   public BlockCauldron(int par1) {
      super(par1, Material.iron);
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 1?this.cauldronTopIcon:(par1 == 0?this.cauldronBottomIcon:this.blockIcon);
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.field_94378_a = par1IconRegister.registerIcon(this.getTextureName() + "_" + "inner");
      this.cauldronTopIcon = par1IconRegister.registerIcon(this.getTextureName() + "_top");
      this.cauldronBottomIcon = par1IconRegister.registerIcon(this.getTextureName() + "_" + "bottom");
      this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
   }

   public static Icon getCauldronIcon(String par0Str) {
      return par0Str.equals("inner")?Block.cauldron.field_94378_a:(par0Str.equals("bottom")?Block.cauldron.cauldronBottomIcon:null);
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      float var8 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      this.setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      this.setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
      this.setBlockBoundsForItemRender();
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return 24;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         ItemStack var10 = par5EntityPlayer.inventory.getCurrentItem();
         if(var10 == null) {
            return true;
         } else {
            int var11 = par1World.getBlockMetadata(par2, par3, par4);
            int var12 = func_111045_h_(var11);
            if(var10.itemID == Item.bucketWater.itemID) {
               if(var12 < 3) {
                  if(!par5EntityPlayer.capabilities.isCreativeMode) {
                     par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketEmpty));
                  }

                  par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
                  par1World.func_96440_m(par2, par3, par4, this.blockID);
               }

               return true;
            } else {
               if(var10.itemID == Item.glassBottle.itemID) {
                  if(var12 > 0) {
                     ItemStack var13 = new ItemStack(Item.potion, 1, 0);
                     if(!par5EntityPlayer.inventory.addItemStackToInventory(var13)) {
                        par1World.spawnEntityInWorld(new EntityItem(par1World, (double)par2 + 0.5D, (double)par3 + 1.5D, (double)par4 + 0.5D, var13));
                     } else if(par5EntityPlayer instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)par5EntityPlayer).sendContainerToPlayer(par5EntityPlayer.inventoryContainer);
                     }

                     --var10.stackSize;
                     if(var10.stackSize <= 0) {
                        par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, (ItemStack)null);
                     }

                     par1World.setBlockMetadataWithNotify(par2, par3, par4, var12 - 1, 2);
                     par1World.func_96440_m(par2, par3, par4, this.blockID);
                  }
               } else if(var12 > 0 && var10.getItem() instanceof ItemArmor && ((ItemArmor)var10.getItem()).getArmorMaterial() == EnumArmorMaterial.CLOTH) {
                  ItemArmor var14 = (ItemArmor)var10.getItem();
                  var14.removeColor(var10);
                  par1World.setBlockMetadataWithNotify(par2, par3, par4, var12 - 1, 2);
                  par1World.func_96440_m(par2, par3, par4, this.blockID);
                  return true;
               }

               return true;
            }
         }
      }
   }

   public void fillWithRain(World par1World, int par2, int par3, int par4) {
      if(par1World.rand.nextInt(20) == 1) {
         int var5 = par1World.getBlockMetadata(par2, par3, par4);
         if(var5 < 3) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var5 + 1, 2);
         }
      }
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return Item.cauldron.itemID;
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return Item.cauldron.itemID;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
      int var6 = par1World.getBlockMetadata(par2, par3, par4);
      return func_111045_h_(var6);
   }

   public static int func_111045_h_(int par0) {
      return par0;
   }
}
