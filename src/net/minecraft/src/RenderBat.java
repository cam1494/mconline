package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderBat extends RenderLiving {
   private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");
   private int renderedBatSize;

   public RenderBat() {
      super(new ModelBat(), 0.25F);
      this.renderedBatSize = ((ModelBat)this.mainModel).getBatSize();
   }

   public void func_82443_a(EntityBat par1EntityBat, double par2, double par4, double par6, float par8, float par9) {
      int var10 = ((ModelBat)this.mainModel).getBatSize();
      if(var10 != this.renderedBatSize) {
         this.renderedBatSize = var10;
         this.mainModel = new ModelBat();
      }

      super.doRenderLiving(par1EntityBat, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getBatTextures(EntityBat par1EntityBat) {
      return batTextures;
   }

   protected void func_82442_a(EntityBat par1EntityBat, float par2) {
      GL11.glScalef(0.35F, 0.35F, 0.35F);
   }

   protected void func_82445_a(EntityBat par1EntityBat, double par2, double par4, double par6) {
      super.renderLivingAt(par1EntityBat, par2, par4, par6);
   }

   protected void func_82444_a(EntityBat par1EntityBat, float par2, float par3, float par4) {
      if(!par1EntityBat.getIsBatHanging()) {
         GL11.glTranslatef(0.0F, MathHelper.cos(par2 * 0.3F) * 0.1F, 0.0F);
      } else {
         GL11.glTranslatef(0.0F, -0.1F, 0.0F);
      }

      super.rotateCorpse(par1EntityBat, par2, par3, par4);
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.func_82443_a((EntityBat)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.func_82442_a((EntityBat)par1EntityLivingBase, par2);
   }

   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
      this.func_82444_a((EntityBat)par1EntityLivingBase, par2, par3, par4);
   }

   protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
      this.func_82445_a((EntityBat)par1EntityLivingBase, par2, par4, par6);
   }

   public void doRenderLiving(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.func_82443_a((EntityBat)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getBatTextures((EntityBat)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.func_82443_a((EntityBat)par1Entity, par2, par4, par6, par8, par9);
   }
}
