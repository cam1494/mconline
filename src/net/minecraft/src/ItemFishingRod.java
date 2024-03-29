package net.minecraft.src;

public class ItemFishingRod extends Item {
   private Icon theIcon;

   public ItemFishingRod(int par1) {
      super(par1);
      this.setMaxDamage(64);
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   public boolean isFull3D() {
      return true;
   }

   public boolean shouldRotateAroundWhenRendering() {
      return true;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(par3EntityPlayer.fishEntity != null) {
         int var4 = par3EntityPlayer.fishEntity.catchFish();
         par1ItemStack.damageItem(var4, par3EntityPlayer);
         par3EntityPlayer.swingItem();
      } else {
         par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
         if(!par2World.isRemote) {
            par2World.spawnEntityInWorld(new EntityFishHook(par2World, par3EntityPlayer));
         }

         par3EntityPlayer.swingItem();
      }

      return par1ItemStack;
   }

   public void registerIcons(IconRegister par1IconRegister) {
      this.itemIcon = par1IconRegister.registerIcon(this.getIconString() + "_uncast");
      this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_cast");
   }

   public Icon func_94597_g() {
      return this.theIcon;
   }
}
