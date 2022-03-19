package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen {
   private final GuiScreen guiScreen;
   private final ServerData theServerData;
   private GuiTextField serverTextField;

   public GuiScreenServerList(GuiScreen par1GuiScreen, ServerData par2ServerData) {
      this.guiScreen = par1GuiScreen;
      this.theServerData = par2ServerData;
   }

   public void updateScreen() {
      this.serverTextField.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.getString("selectServer.select")));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.getString("gui.cancel")));
      this.serverTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 116, 200, 20);
      this.serverTextField.setMaxStringLength(128);
      this.serverTextField.setFocused(true);
      this.serverTextField.setText(this.mc.gameSettings.lastServer);
      ((GuiButton)this.buttonList.get(0)).enabled = this.serverTextField.getText().length() > 0 && this.serverTextField.getText().split(":").length > 0;
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.mc.gameSettings.lastServer = this.serverTextField.getText();
      this.mc.gameSettings.saveOptions();
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.enabled) {
         if(par1GuiButton.id == 1) {
            this.guiScreen.confirmClicked(false, 0);
         } else if(par1GuiButton.id == 0) {
            this.theServerData.serverIP = this.serverTextField.getText();
            this.guiScreen.confirmClicked(true, 0);
         }
      }
   }

   protected void keyTyped(char par1, int par2) {
      if(this.serverTextField.textboxKeyTyped(par1, par2)) {
         ((GuiButton)this.buttonList.get(0)).enabled = this.serverTextField.getText().length() > 0 && this.serverTextField.getText().split(":").length > 0;
      } else if(par2 == 28 || par2 == 156) {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.serverTextField.mouseClicked(par1, par2, par3);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, I18n.getString("selectServer.direct"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRenderer, I18n.getString("addServer.enterIp"), this.width / 2 - 100, 100, 10526880);
      this.serverTextField.drawTextBox();
      super.drawScreen(par1, par2, par3);
   }
}
