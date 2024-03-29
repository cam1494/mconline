package net.minecraft.src;

public class ModelSkeletonHead extends ModelBase {
   public ModelRenderer skeletonHead;

   public ModelSkeletonHead() {
      this(0, 35, 64, 64);
   }

   public ModelSkeletonHead(int par1, int par2, int par3, int par4) {
      this.textureWidth = par3;
      this.textureHeight = par4;
      this.skeletonHead = new ModelRenderer(this, par1, par2);
      this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      this.skeletonHead.render(par7);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
      super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
      this.skeletonHead.rotateAngleY = par4 / (180F / (float)Math.PI);
      this.skeletonHead.rotateAngleX = par5 / (180F / (float)Math.PI);
   }
}
