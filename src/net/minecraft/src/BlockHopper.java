package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockHopper extends BlockContainer {
   private final Random field_94457_a = new Random();
   private Icon hopperIcon;
   private Icon hopperTopIcon;
   private Icon hopperInsideIcon;

   public BlockHopper(int par1) {
      super(par1, Material.iron);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
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
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
      int var10 = Facing.oppositeSide[par5];
      if(var10 == 1) {
         var10 = 0;
      }

      return var10;
   }

   public TileEntity createNewTileEntity(World par1World) {
      return new TileEntityHopper();
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
      if(par6ItemStack.hasDisplayName()) {
         TileEntityHopper var7 = getHopperTile(par1World, par2, par3, par4);
         var7.setInventoryName(par6ItemStack.getDisplayName());
      }
   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      super.onBlockAdded(par1World, par2, par3, par4);
      this.updateMetadata(par1World, par2, par3, par4);
   }

   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      if(par1World.isRemote) {
         return true;
      } else {
         TileEntityHopper var10 = getHopperTile(par1World, par2, par3, par4);
         if(var10 != null) {
            par5EntityPlayer.displayGUIHopper(var10);
         }

         return true;
      }
   }

   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
      this.updateMetadata(par1World, par2, par3, par4);
   }

   private void updateMetadata(World par1World, int par2, int par3, int par4) {
      int var5 = par1World.getBlockMetadata(par2, par3, par4);
      int var6 = getDirectionFromMetadata(var5);
      boolean var7 = !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
      boolean var8 = getIsBlockNotPoweredFromMetadata(var5);
      if(var7 != var8) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | (var7?0:8), 4);
      }
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
      TileEntityHopper var7 = (TileEntityHopper)par1World.getBlockTileEntity(par2, par3, par4);
      if(var7 != null) {
         for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
            ItemStack var9 = var7.getStackInSlot(var8);
            if(var9 != null) {
               float var10 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;
               float var11 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;
               float var12 = this.field_94457_a.nextFloat() * 0.8F + 0.1F;

               while(var9.stackSize > 0) {
                  int var13 = this.field_94457_a.nextInt(21) + 10;
                  if(var13 > var9.stackSize) {
                     var13 = var9.stackSize;
                  }

                  var9.stackSize -= var13;
                  EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                  if(var9.hasTagCompound()) {
                     var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                  }

                  float var15 = 0.05F;
                  var14.motionX = (double)((float)this.field_94457_a.nextGaussian() * var15);
                  var14.motionY = (double)((float)this.field_94457_a.nextGaussian() * var15 + 0.2F);
                  var14.motionZ = (double)((float)this.field_94457_a.nextGaussian() * var15);
                  par1World.spawnEntityInWorld(var14);
               }
            }
         }

         par1World.func_96440_m(par2, par3, par4, par5);
      }

      super.breakBlock(par1World, par2, par3, par4, par5, par6);
   }

   public int getRenderType() {
      return 38;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      return true;
   }

   public Icon getIcon(int par1, int par2) {
      return par1 == 1?this.hopperTopIcon:this.hopperIcon;
   }

   public static int getDirectionFromMetadata(int par0) {
      return par0 & 7;
   }

   public static boolean getIsBlockNotPoweredFromMetadata(int par0) {
      return (par0 & 8) != 8;
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
      return Container.calcRedstoneFromInventory(getHopperTile(par1World, par2, par3, par4));
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.hopperIcon = par1IconRegister.registerIcon("hopper_outside");
      this.hopperTopIcon = par1IconRegister.registerIcon("hopper_top");
      this.hopperInsideIcon = par1IconRegister.registerIcon("hopper_inside");
   }

   public static Icon getHopperIcon(String par0Str) {
      return par0Str.equals("hopper_outside")?Block.hopperBlock.hopperIcon:(par0Str.equals("hopper_inside")?Block.hopperBlock.hopperInsideIcon:null);
   }

   public String getItemIconName() {
      return "hopper";
   }

   public static TileEntityHopper getHopperTile(IBlockAccess par0IBlockAccess, int par1, int par2, int par3) {
      return (TileEntityHopper)par0IBlockAccess.getBlockTileEntity(par1, par2, par3);
   }
}
