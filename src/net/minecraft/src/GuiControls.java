package net.minecraft.src;

public class GuiControls extends GuiScreen {
   private GuiScreen parentScreen;
   protected String screenTitle = "Controls";
   private GameSettings options;
   private int buttonId = -1;

   public GuiControls(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
      this.parentScreen = par1GuiScreen;
      this.options = par2GameSettings;
   }

   private int getLeftBorder() {
      return this.width / 2 - 155;
   }

   public void initGui() {
      int var1 = this.getLeftBorder();

      for(int var2 = 0; var2 < this.options.keyBindings.length; ++var2) {
         this.buttonList.add(new GuiSmallButton(var2, var1 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), 70, 20, this.options.getOptionDisplayString(var2)));
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.getString("gui.done")));
      this.screenTitle = I18n.getString("controls.title");
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      for(int var2 = 0; var2 < this.options.keyBindings.length; ++var2) {
         ((GuiButton)this.buttonList.get(var2)).displayString = this.options.getOptionDisplayString(var2);
      }

      if(par1GuiButton.id == 200) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else {
         this.buttonId = par1GuiButton.id;
         par1GuiButton.displayString = "> " + this.options.getOptionDisplayString(par1GuiButton.id) + " <";
      }
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      if(this.buttonId >= 0) {
         this.options.setKeyBinding(this.buttonId, -100 + par3);
         ((GuiButton)this.buttonList.get(this.buttonId)).displayString = this.options.getOptionDisplayString(this.buttonId);
         this.buttonId = -1;
         KeyBinding.resetKeyBindingArrayAndHash();
      } else {
         super.mouseClicked(par1, par2, par3);
      }
   }

   protected void keyTyped(char par1, int par2) {
      if(this.buttonId >= 0) {
         this.options.setKeyBinding(this.buttonId, par2);
         ((GuiButton)this.buttonList.get(this.buttonId)).displayString = this.options.getOptionDisplayString(this.buttonId);
         this.buttonId = -1;
         KeyBinding.resetKeyBindingArrayAndHash();
      } else {
         super.keyTyped(par1, par2);
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
      int var4 = this.getLeftBorder();
      int var5 = 0;

      while(var5 < this.options.keyBindings.length) {
         boolean var6 = false;
         int var7 = 0;

         while(true) {
            if(var7 < this.options.keyBindings.length) {
               if(var7 == var5 || this.options.keyBindings[var5].keyCode != this.options.keyBindings[var7].keyCode) {
                  ++var7;
                  continue;
               }

               var6 = true;
            }

            if(this.buttonId == var5) {
               ((GuiButton)this.buttonList.get(var5)).displayString = "" + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
            } else if(var6) {
               ((GuiButton)this.buttonList.get(var5)).displayString = EnumChatFormatting.RED + this.options.getOptionDisplayString(var5);
            } else {
               ((GuiButton)this.buttonList.get(var5)).displayString = this.options.getOptionDisplayString(var5);
            }

            this.drawString(this.fontRenderer, this.options.getKeyBindingDescription(var5), var4 + var5 % 2 * 160 + 70 + 6, this.height / 6 + 24 * (var5 >> 1) + 7, -1);
            ++var5;
            break;
         }
      }

      super.drawScreen(par1, par2, par3);
   }
}
