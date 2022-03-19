package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderOcelot extends RenderLiving {
   private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
   private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
   private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
   private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");

   public RenderOcelot(ModelBase par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }

   public void renderLivingOcelot(EntityOcelot par1EntityOcelot, double par2, double par4, double par6, float par8, float par9) {
      super.doRenderLiving(par1EntityOcelot, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation func_110874_a(EntityOcelot par1EntityOcelot) {
      switch(par1EntityOcelot.getTameSkin()) {
      case 0:
      default:
         return ocelotTextures;
      case 1:
         return blackOcelotTextures;
      case 2:
         return redOcelotTextures;
      case 3:
         return siameseOcelotTextures;
      }
   }

   protected void preRenderOcelot(EntityOcelot par1EntityOcelot, float par2) {
      super.preRenderCallback(par1EntityOcelot, par2);
      if(par1EntityOcelot.isTamed()) {
         GL11.glScalef(0.8F, 0.8F, 0.8F);
      }
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderLivingOcelot((EntityOcelot)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.preRenderOcelot((EntityOcelot)par1EntityLivingBase, par2);
   }

   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderLivingOcelot((EntityOcelot)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.func_110874_a((EntityOcelot)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderLivingOcelot((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
   }
}
