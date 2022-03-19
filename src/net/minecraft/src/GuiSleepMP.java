package net.minecraft.src;

public class GuiSleepMP extends GuiChat {
   public void initGui() {
      super.initGui();
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.getString("multiplayer.stopSleeping")));
   }

   protected void keyTyped(char par1, int par2) {
      if(par2 == 1) {
         this.wakeEntity();
      } else if(par2 != 28 && par2 != 156) {
         super.keyTyped(par1, par2);
      } else {
         String var3 = this.inputField.getText().trim();
         if(var3.length() > 0) {
            this.mc.thePlayer.sendChatMessage(var3);
         }

         this.inputField.setText("");
         this.mc.ingameGUI.getChatGUI().resetScroll();
      }
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.id == 1) {
         this.wakeEntity();
      } else {
         super.actionPerformed(par1GuiButton);
      }
   }

   private void wakeEntity() {
      NetClientHandler var1 = this.mc.thePlayer.sendQueue;
      var1.addToSendQueue(new Packet19EntityAction(this.mc.thePlayer, 3));
   }
}
