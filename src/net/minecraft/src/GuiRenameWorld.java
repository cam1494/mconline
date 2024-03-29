package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen {
   private GuiScreen parentGuiScreen;
   private GuiTextField theGuiTextField;
   private final String worldName;

   public GuiRenameWorld(GuiScreen par1GuiScreen, String par2Str) {
      this.parentGuiScreen = par1GuiScreen;
      this.worldName = par2Str;
   }

   public void updateScreen() {
      this.theGuiTextField.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.getString("selectWorld.renameButton")));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.getString("gui.cancel")));
      ISaveFormat var1 = this.mc.getSaveLoader();
      WorldInfo var2 = var1.getWorldInfo(this.worldName);
      String var3 = var2.getWorldName();
      this.theGuiTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
      this.theGuiTextField.setFocused(true);
      this.theGuiTextField.setText(var3);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.enabled) {
         if(par1GuiButton.id == 1) {
            this.mc.displayGuiScreen(this.parentGuiScreen);
         } else if(par1GuiButton.id == 0) {
            ISaveFormat var2 = this.mc.getSaveLoader();
            var2.renameWorld(this.worldName, this.theGuiTextField.getText().trim());
            this.mc.displayGuiScreen(this.parentGuiScreen);
         }
      }
   }

   protected void keyTyped(char par1, int par2) {
      this.theGuiTextField.textboxKeyTyped(par1, par2);
      ((GuiButton)this.buttonList.get(0)).enabled = this.theGuiTextField.getText().trim().length() > 0;
      if(par2 == 28 || par2 == 156) {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.theGuiTextField.mouseClicked(par1, par2, par3);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, I18n.getString("selectWorld.renameTitle"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRenderer, I18n.getString("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
      this.theGuiTextField.drawTextBox();
      super.drawScreen(par1, par2, par3);
   }
}
