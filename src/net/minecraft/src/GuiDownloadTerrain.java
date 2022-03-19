package net.minecraft.src;

public class GuiDownloadTerrain extends GuiScreen {
   private NetClientHandler netHandler;
   private int updateCounter;

   public GuiDownloadTerrain(NetClientHandler par1NetClientHandler) {
      this.netHandler = par1NetClientHandler;
   }

   protected void keyTyped(char par1, int par2) {}

   public void initGui() {
      this.buttonList.clear();
   }

   public void updateScreen() {
      ++this.updateCounter;
      if(this.updateCounter % 20 == 0) {
         this.netHandler.addToSendQueue(new Packet0KeepAlive());
      }

      if(this.netHandler != null) {
         this.netHandler.processReadPackets();
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(0);
      this.drawCenteredString(this.fontRenderer, I18n.getString("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
      super.drawScreen(par1, par2, par3);
   }
}
