package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderVillager extends RenderLiving {
   private static final ResourceLocation villagerTextures = new ResourceLocation("textures/entity/villager/villager.png");
   private static final ResourceLocation farmerVillagerTextures = new ResourceLocation("textures/entity/villager/farmer.png");
   private static final ResourceLocation librarianVillagerTextures = new ResourceLocation("textures/entity/villager/librarian.png");
   private static final ResourceLocation priestVillagerTextures = new ResourceLocation("textures/entity/villager/priest.png");
   private static final ResourceLocation smithVillagerTextures = new ResourceLocation("textures/entity/villager/smith.png");
   private static final ResourceLocation butcherVillagerTextures = new ResourceLocation("textures/entity/villager/butcher.png");
   protected ModelVillager villagerModel;

   public RenderVillager() {
      super(new ModelVillager(0.0F), 0.5F);
      this.villagerModel = (ModelVillager)this.mainModel;
   }

   protected int shouldVillagerRenderPass(EntityVillager par1EntityVillager, int par2, float par3) {
      return -1;
   }

   public void renderVillager(EntityVillager par1EntityVillager, double par2, double par4, double par6, float par8, float par9) {
      super.doRenderLiving(par1EntityVillager, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation func_110902_a(EntityVillager par1EntityVillager) {
      switch(par1EntityVillager.getProfession()) {
      case 0:
         return farmerVillagerTextures;
      case 1:
         return librarianVillagerTextures;
      case 2:
         return priestVillagerTextures;
      case 3:
         return smithVillagerTextures;
      case 4:
         return butcherVillagerTextures;
      default:
         return villagerTextures;
      }
   }

   protected void renderVillagerEquipedItems(EntityVillager par1EntityVillager, float par2) {
      super.renderEquippedItems(par1EntityVillager, par2);
   }

   protected void preRenderVillager(EntityVillager par1EntityVillager, float par2) {
      float var3 = 0.9375F;
      if(par1EntityVillager.getGrowingAge() < 0) {
         var3 = (float)((double)var3 * 0.5D);
         this.shadowSize = 0.25F;
      } else {
         this.shadowSize = 0.5F;
      }

      GL11.glScalef(var3, var3, var3);
   }

   public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.renderVillager((EntityVillager)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
      this.preRenderVillager((EntityVillager)par1EntityLivingBase, par2);
   }

   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
      return this.shouldVillagerRenderPass((EntityVillager)par1EntityLivingBase, par2, par3);
   }

   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2) {
      this.renderVillagerEquipedItems((EntityVillager)par1EntityLivingBase, par2);
   }

   public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9) {
      this.renderVillager((EntityVillager)par1EntityLivingBase, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.func_110902_a((EntityVillager)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderVillager((EntityVillager)par1Entity, par2, par4, par6, par8, par9);
   }
}
