package net.minecraft.src;

public class GuiScreenClientOutdated extends GuiScreen {
   private final GuiScreen previousScreen;

   public GuiScreenClientOutdated(GuiScreen par1GuiScreen) {
      this.previousScreen = par1GuiScreen;
   }

   public void initGui() {
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      String var4 = I18n.getString("mco.client.outdated.title");
      String var5 = I18n.getString("mco.client.outdated.msg");
      this.drawCenteredString(this.fontRenderer, var4, this.width / 2, this.height / 2 - 50, 16711680);
      this.drawCenteredString(this.fontRenderer, var5, this.width / 2, this.height / 2 - 30, 16777215);
      super.drawScreen(par1, par2, par3);
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.id == 0) {
         this.mc.displayGuiScreen(this.previousScreen);
      }
   }

   protected void keyTyped(char par1, int par2) {
      if(par2 == 28 || par2 == 156) {
         this.mc.displayGuiScreen(this.previousScreen);
      }
   }
}
