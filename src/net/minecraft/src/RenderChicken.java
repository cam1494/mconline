package net.minecraft.src;

public class RenderChicken extends RenderLiving {
   private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");

   public RenderChicken(ModelBase par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }

   public void renderChicken(EntityChicken par1EntityChicken, double par2, double par4, double par6, float par8, float par9) {
      super.doRenderLiving(par1EntityChicken, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getChickenTextures(EntityChicken par1EntityChicken) {
      return chickenTextures;
   }

   protected float getWingRotation(EntityChicken par1EntityChicken, float par2) {
      float var3 = par1EntityChicken.field_70888_h + (par1EntityChicken.field_70886_e - par1EntityChicken.field_70888_h) * par2;
      float var4 = par1EntityChicken.field_70884_g + (par1EntityChicken.destPos - par1EntityChicken.field_70884_g) * par2;
      return (MathHelper.sin(var3) + 1.0F) * var4;
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderChicken((EntityChicken)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
      return this.getWingRotation((EntityChicken)par1EntityLivingBase, par2);
   }

   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderChicken((EntityChicken)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getChickenTextures((EntityChicken)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderChicken((EntityChicken)par1Entity, par2, par4, par6, par8, par9);
   }
}
