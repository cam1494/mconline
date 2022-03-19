package net.minecraft.src;

public class ItemBucketMilk extends Item {
   public ItemBucketMilk(int par1) {
      super(par1);
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(!par3EntityPlayer.capabilities.isCreativeMode) {
         --par1ItemStack.stackSize;
      }

      if(!par2World.isRemote) {
         par3EntityPlayer.clearActivePotions();
      }

      return par1ItemStack.stackSize <= 0?new ItemStack(Item.bucketEmpty):par1ItemStack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 32;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.drink;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }
}
