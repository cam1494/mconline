package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderFallingSand extends Render {
   private final RenderBlocks sandRenderBlocks = new RenderBlocks();

   public RenderFallingSand() {
      this.shadowSize = 0.5F;
   }

   public void doRenderFallingSand(EntityFallingSand par1EntityFallingSand, double par2, double par4, double par6, float par8, float par9) {
      World var10 = par1EntityFallingSand.getWorld();
      Block var11 = Block.blocksList[par1EntityFallingSand.blockID];
      if(var10.getBlockId(MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ)) != par1EntityFallingSand.blockID) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)par2, (float)par4, (float)par6);
         this.bindEntityTexture(par1EntityFallingSand);
         GL11.glDisable(GL11.GL_LIGHTING);
         Tessellator var12;
         if(var11 instanceof BlockAnvil && var11.getRenderType() == 35) {
            this.sandRenderBlocks.blockAccess = var10;
            var12 = Tessellator.instance;
            var12.startDrawingQuads();
            var12.setTranslation((double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posX)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posY)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posZ)) - 0.5F));
            this.sandRenderBlocks.renderBlockAnvilMetadata((BlockAnvil)var11, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ), par1EntityFallingSand.metadata);
            var12.setTranslation(0.0D, 0.0D, 0.0D);
            var12.draw();
         } else if(var11.getRenderType() == 27) {
            this.sandRenderBlocks.blockAccess = var10;
            var12 = Tessellator.instance;
            var12.startDrawingQuads();
            var12.setTranslation((double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posX)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posY)) - 0.5F), (double)((float)(-MathHelper.floor_double(par1EntityFallingSand.posZ)) - 0.5F));
            this.sandRenderBlocks.renderBlockDragonEgg((BlockDragonEgg)var11, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ));
            var12.setTranslation(0.0D, 0.0D, 0.0D);
            var12.draw();
         } else if(var11 != null) {
            this.sandRenderBlocks.setRenderBoundsFromBlock(var11);
            this.sandRenderBlocks.renderBlockSandFalling(var11, var10, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ), par1EntityFallingSand.metadata);
         }

         GL11.glEnable(GL11.GL_LIGHTING);
         GL11.glPopMatrix();
      }
   }

   protected ResourceLocation getFallingSandTextures(EntityFallingSand par1EntityFallingSand) {
      return TextureMap.locationBlocksTexture;
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.getFallingSandTextures((EntityFallingSand)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.doRenderFallingSand((EntityFallingSand)par1Entity, par2, par4, par6, par8, par9);
   }
}
