package net.minecraft.src;

public class GuiErrorScreen extends GuiScreen {
   private String message1;
   private String message2;

   public GuiErrorScreen(String par1Str, String par2Str) {
      this.message1 = par1Str;
      this.message2 = par2Str;
   }

   public void initGui() {
      super.initGui();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 140, I18n.getString("gui.cancel")));
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
      this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 90, 16777215);
      this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 110, 16777215);
      super.drawScreen(par1, par2, par3);
   }

   protected void keyTyped(char par1, int par2) {}

   protected void actionPerformed(GuiButton par1GuiButton) {
      this.mc.displayGuiScreen((GuiScreen)null);
   }
}
