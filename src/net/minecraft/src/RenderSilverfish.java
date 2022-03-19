package net.minecraft.src;

public class RenderSilverfish extends RenderLiving {
   private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");

   public RenderSilverfish() {
      super(new ModelSilverfish(), 0.3F);
   }

   protected float getSilverfishDeathRotation(EntitySilverfish par1EntitySilverfish) {
      return 180.0F;
   }

   public void renderSilverfish(EntitySilverfish par1EntitySilverfish, double par2, double par4, double par6, float par8, float par9) {
      super.doRenderLiving(par1EntitySilverfish, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getSilverfishTextures(EntitySilverfish par1EntitySilverfish) {
      return silverfishTextures;
   }

   protected int shouldSilverfishRenderPass(EntitySilverfish par1EntitySilverfish, int par2, float par3) {
      return -1;
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderSilverfish((EntitySilverfish)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase) {
      return this.getSilverfishDeathRotation((EntitySilverfish)par1EntityLivingBase);
   }

   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.shouldSilverfishRenderPass((EntitySilverfish)par1EntityLivingBase, par2, par3);
   }

   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderSilverfish((EntitySilverfish)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getSilverfishTextures((EntitySilverfish)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderSilverfish((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
   }
}
