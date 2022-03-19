package net.minecraft.src;

public class RenderCow extends RenderLiving {
   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");

   public RenderCow(ModelBase par1ModelBase, float par2) {
      super(par1ModelBase, par2);
   }

   protected ResourceLocation getCowTextures(EntityCow par1EntityCow) {
      return cowTextures;
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getCowTextures((EntityCow)par1Entity);
   }
}
