package net.minecraft.src;

public class PotionAbsoption extends Potion {
   protected PotionAbsoption(int par1, boolean par2, int par3) {
      super(par1, par2, par3);
   }

   public void removeAttributesModifiersFromEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
      par1EntityLivingBase.setAbsorptionAmount(par1EntityLivingBase.getAbsorptionAmount() - (float)(4 * (par3 + 1)));
      super.removeAttributesModifiersFromEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
   }

   public void applyAttributesModifiersToEntity(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3) {
      par1EntityLivingBase.setAbsorptionAmount(par1EntityLivingBase.getAbsorptionAmount() + (float)(4 * (par3 + 1)));
      super.applyAttributesModifiersToEntity(par1EntityLivingBase, par2BaseAttributeMap, par3);
   }
}
