package net.minecraft.src;

import com.google.common.collect.Multimap;

public class ItemTool extends Item {
   private Block[] blocksEffectiveAgainst;
   protected float efficiencyOnProperMaterial = 4.0F;
   private float damageVsEntity;
   protected EnumToolMaterial toolMaterial;

   protected ItemTool(int par1, float par2, EnumToolMaterial par3EnumToolMaterial, Block[] par4ArrayOfBlock) {
      super(par1);
      this.toolMaterial = par3EnumToolMaterial;
      this.blocksEffectiveAgainst = par4ArrayOfBlock;
      this.maxStackSize = 1;
      this.setMaxDamage(par3EnumToolMaterial.getMaxUses());
      this.efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
      this.damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
      for(int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3) {
         if(this.blocksEffectiveAgainst[var3] == par2Block) {
            return this.efficiencyOnProperMaterial;
         }
      }

      return 1.0F;
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
      par1ItemStack.damageItem(2, par3EntityLivingBase);
      return true;
   }

   public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
      if((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D) {
         par1ItemStack.damageItem(1, par7EntityLivingBase);
      }

      return true;
   }

   public boolean isFull3D() {
      return true;
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
      var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
      return var1;
   }
}
