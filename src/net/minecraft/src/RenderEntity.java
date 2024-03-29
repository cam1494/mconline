package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderEntity extends Render {
   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      GL11.glPushMatrix();
      renderOffsetAABB(par1Entity.boundingBox, par2 - par1Entity.lastTickPosX, par4 - par1Entity.lastTickPosY, par6 - par1Entity.lastTickPosZ);
      GL11.glPopMatrix();
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return null;
   }
}
