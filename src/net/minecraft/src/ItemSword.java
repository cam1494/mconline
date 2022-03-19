package net.minecraft.src;

import com.google.common.collect.Multimap;

public class ItemSword extends Item {
   private float weaponDamage;
   private final EnumToolMaterial toolMaterial;

   public ItemSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
      super(par1);
      this.toolMaterial = par2EnumToolMaterial;
      this.maxStackSize = 1;
      this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
      this.setCreativeTab(CreativeTabs.tabCombat);
      this.weaponDamage = 4.0F + par2EnumToolMaterial.getDamageVsEntity();
   }

   public float func_82803_g() {
      return this.toolMaterial.getDamageVsEntity();
   }

   public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
      if(par2Block.blockID == Block.web.blockID) {
         return 15.0F;
      } else {
         Material var3 = par2Block.blockMaterial;
         return var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.pumpkin?1.0F:1.5F;
      }
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
      par1ItemStack.damageItem(1, par3EntityLivingBase);
      return true;
   }

   public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
      if((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D) {
         par1ItemStack.damageItem(2, par7EntityLivingBase);
      }

      return true;
   }

   public boolean isFull3D() {
      return true;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.block;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   public boolean canHarvestBlock(Block par1Block) {
      return par1Block.blockID == Block.web.blockID;
   }

   public int getItemEnchantability() {
      return this.toolMaterial.getEnchantability();
   }

   public String getToolMaterialName() {
      return this.toolMaterial.toString();
   }

   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
      return this.toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID?true:super.getIsRepairable(par1ItemStack, par2ItemStack);
   }

   public Multimap getItemAttributeModifiers() {
      Multimap var1 = super.getItemAttributeModifiers();
      var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.weaponDamage, 0));
      return var1;
   }
}
