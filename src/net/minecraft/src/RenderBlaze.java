package net.minecraft.src;

public class RenderBlaze extends RenderLiving {
   private static final ResourceLocation blazeTextures = new ResourceLocation("textures/entity/blaze.png");
   private int field_77068_a;

   public RenderBlaze() {
      super(new ModelBlaze(), 0.5F);
      this.field_77068_a = ((ModelBlaze)this.mainModel).func_78104_a();
   }

   public void renderBlaze(EntityBlaze par1EntityBlaze, double par2, double par4, double par6, float par8, float par9) {
      int var10 = ((ModelBlaze)this.mainModel).func_78104_a();
      if(var10 != this.field_77068_a) {
         this.field_77068_a = var10;
         this.mainModel = new ModelBlaze();
      }

      super.doRenderLiving(par1EntityBlaze, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getBlazeTextures(EntityBlaze par1EntityBlaze) {
      return blazeTextures;
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderBlaze((EntityBlaze)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderBlaze((EntityBlaze)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getBlazeTextures((EntityBlaze)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderBlaze((EntityBlaze)par1Entity, par2, par4, par6, par8, par9);
   }
}
