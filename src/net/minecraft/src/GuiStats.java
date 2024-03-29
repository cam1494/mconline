package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiStats extends GuiScreen {
   private static RenderItem renderItem = new RenderItem();
   protected GuiScreen parentGui;
   protected String statsTitle = "Select world";
   private GuiSlotStatsGeneral slotGeneral;
   private GuiSlotStatsItem slotItem;
   private GuiSlotStatsBlock slotBlock;
   private StatFileWriter statFileWriter;
   private GuiSlot selectedSlot;

   public GuiStats(GuiScreen par1GuiScreen, StatFileWriter par2StatFileWriter) {
      this.parentGui = par1GuiScreen;
      this.statFileWriter = par2StatFileWriter;
   }

   public void initGui() {
      this.statsTitle = I18n.getString("gui.stats");
      this.slotGeneral = new GuiSlotStatsGeneral(this);
      this.slotGeneral.registerScrollButtons(1, 1);
      this.slotItem = new GuiSlotStatsItem(this);
      this.slotItem.registerScrollButtons(1, 1);
      this.slotBlock = new GuiSlotStatsBlock(this);
      this.slotBlock.registerScrollButtons(1, 1);
      this.selectedSlot = this.slotGeneral;
      this.addHeaderButtons();
   }

   public void addHeaderButtons() {
      this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.getString("gui.done")));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.getString("stat.generalButton")));
      GuiButton var1;
      this.buttonList.add(var1 = new GuiButton(2, this.width / 2 - 46, this.height - 52, 100, 20, I18n.getString("stat.blocksButton")));
      GuiButton var2;
      this.buttonList.add(var2 = new GuiButton(3, this.width / 2 + 62, this.height - 52, 100, 20, I18n.getString("stat.itemsButton")));
      if(this.slotBlock.getSize() == 0) {
         var1.enabled = false;
      }

      if(this.slotItem.getSize() == 0) {
         var2.enabled = false;
      }
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.enabled) {
         if(par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(this.parentGui);
         } else if(par1GuiButton.id == 1) {
            this.selectedSlot = this.slotGeneral;
         } else if(par1GuiButton.id == 3) {
            this.selectedSlot = this.slotItem;
         } else if(par1GuiButton.id == 2) {
            this.selectedSlot = this.slotBlock;
         } else {
            this.selectedSlot.actionPerformed(par1GuiButton);
         }
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.selectedSlot.drawScreen(par1, par2, par3);
      this.drawCenteredString(this.fontRenderer, this.statsTitle, this.width / 2, 20, 16777215);
      super.drawScreen(par1, par2, par3);
   }

   private void drawItemSprite(int par1, int par2, int par3) {
      this.drawButtonBackground(par1 + 1, par2 + 1);
      GL11.glEnable(GL12.GL_RESCALE_NORMAL);
      RenderHelper.enableGUIStandardItemLighting();
      renderItem.renderItemIntoGUI(this.fontRenderer, this.mc.getTextureManager(), new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
      RenderHelper.disableStandardItemLighting();
      GL11.glDisable(GL12.GL_RESCALE_NORMAL);
   }

   private void drawButtonBackground(int par1, int par2) {
      this.drawSprite(par1, par2, 0, 0);
   }

   private void drawSprite(int par1, int par2, int par3, int par4) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(statIcons);
      float var5 = 0.0078125F;
      float var6 = 0.0078125F;
      boolean var7 = true;
      boolean var8 = true;
      Tessellator var9 = Tessellator.instance;
      var9.startDrawingQuads();
      var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 18), (double)this.zLevel, (double)((float)(par3 + 0) * 0.0078125F), (double)((float)(par4 + 18) * 0.0078125F));
      var9.addVertexWithUV((double)(par1 + 18), (double)(par2 + 18), (double)this.zLevel, (double)((float)(par3 + 18) * 0.0078125F), (double)((float)(par4 + 18) * 0.0078125F));
      var9.addVertexWithUV((double)(par1 + 18), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 18) * 0.0078125F), (double)((float)(par4 + 0) * 0.0078125F));
      var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * 0.0078125F), (double)((float)(par4 + 0) * 0.0078125F));
      var9.draw();
   }

   static Minecraft getMinecraft(GuiStats par0GuiStats) {
      return par0GuiStats.mc;
   }

   static FontRenderer getFontRenderer1(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static StatFileWriter getStatsFileWriter(GuiStats par0GuiStats) {
      return par0GuiStats.statFileWriter;
   }

   static FontRenderer getFontRenderer2(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static FontRenderer getFontRenderer3(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static Minecraft getMinecraft1(GuiStats par0GuiStats) {
      return par0GuiStats.mc;
   }

   static void drawSprite(GuiStats par0GuiStats, int par1, int par2, int par3, int par4) {
      par0GuiStats.drawSprite(par1, par2, par3, par4);
   }

   static Minecraft getMinecraft2(GuiStats par0GuiStats) {
      return par0GuiStats.mc;
   }

   static FontRenderer getFontRenderer4(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static FontRenderer getFontRenderer5(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static FontRenderer getFontRenderer6(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static FontRenderer getFontRenderer7(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static FontRenderer getFontRenderer8(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static void drawGradientRect(GuiStats par0GuiStats, int par1, int par2, int par3, int par4, int par5, int par6) {
      par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
   }

   static FontRenderer getFontRenderer9(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static FontRenderer getFontRenderer10(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static void drawGradientRect1(GuiStats par0GuiStats, int par1, int par2, int par3, int par4, int par5, int par6) {
      par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
   }

   static FontRenderer getFontRenderer11(GuiStats par0GuiStats) {
      return par0GuiStats.fontRenderer;
   }

   static void drawItemSprite(GuiStats par0GuiStats, int par1, int par2, int par3) {
      par0GuiStats.drawItemSprite(par1, par2, par3);
   }
}
