package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderEnderman extends RenderLiving {
   private static final ResourceLocation endermanEyesTexture = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
   private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
   private ModelEnderman endermanModel;
   private Random rnd = new Random();

   public RenderEnderman() {
      super(new ModelEnderman(), 0.5F);
      this.endermanModel = (ModelEnderman)super.mainModel;
      this.setRenderPassModel(this.endermanModel);
   }

   public void renderEnderman(EntityEnderman par1EntityEnderman, double par2, double par4, double par6, float par8, float par9) {
      this.endermanModel.isCarrying = par1EntityEnderman.getCarried() > 0;
      this.endermanModel.isAttacking = par1EntityEnderman.isScreaming();
      if(par1EntityEnderman.isScreaming()) {
         double var10 = 0.02D;
         par2 += this.rnd.nextGaussian() * var10;
         par6 += this.rnd.nextGaussian() * var10;
      }

      super.doRenderLiving(par1EntityEnderman, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEndermanTextures(EntityEnderman par1EntityEnderman) {
      return endermanTextures;
   }

   protected void renderCarrying(EntityEnderman par1EntityEnderman, float par2) {
      super.renderEquippedItems(par1EntityEnderman, par2);
      if(par1EntityEnderman.getCarried() > 0) {
         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
         GL11.glPushMatrix();
         float var3 = 0.5F;
         GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
         var3 *= 1.0F;
         GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(-var3, -var3, var3);
         int var4 = par1EntityEnderman.getBrightnessForRender(par2);
         int var5 = var4 % 65536;
         int var6 = var4 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var5 / 1.0F, (float)var6 / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.bindTexture(TextureMap.locationBlocksTexture);
         this.renderBlocks.renderBlockAsItem(Block.blocksList[par1EntityEnderman.getCarried()], par1EntityEnderman.getCarryingData(), 1.0F);
         GL11.glPopMatrix();
         GL11.glDisable(GL12.GL_RESCALE_NORMAL);
      }
   }

   protected int renderEyes(EntityEnderman par1EntityEnderman, int par2, float par3) {
      if(par2 != 0) {
         return -1;
      } else {
         this.bindTexture(endermanEyesTexture);
         float var4 = 1.0F;
         GL11.glEnable(GL11.GL_BLEND);
         GL11.glDisable(GL11.GL_ALPHA_TEST);
         GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
         GL11.glDisable(GL11.GL_LIGHTING);
         if(par1EntityEnderman.isInvisible()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         char var5 = 61680;
         int var6 = var5 % 65536;
         int var7 = var5 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(GL11.GL_LIGHTING);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
         return 1;
      }
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderEnderman((EntityEnderman)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.renderEyes((EntityEnderman)par1EntityLivingBase, par2, par3);
   }

   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
      this.renderCarrying((EntityEnderman)par1EntityLivingBase, par2);
   }

   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderEnderman((EntityEnderman)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getEndermanTextures((EntityEnderman)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderEnderman((EntityEnderman)par1Entity, par2, par4, par6, par8, par9);
   }
}
