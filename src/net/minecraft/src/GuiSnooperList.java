package net.minecraft.src;

class GuiSnooperList extends GuiSlot {
   final GuiSnooper snooperGui;

   public GuiSnooperList(GuiSnooper par1GuiSnooper) {
      super(par1GuiSnooper.mc, par1GuiSnooper.width, par1GuiSnooper.height, 80, par1GuiSnooper.height - 40, par1GuiSnooper.fontRenderer.FONT_HEIGHT + 1);
      this.snooperGui = par1GuiSnooper;
   }

   protected int getSize() {
      return GuiSnooper.func_74095_a(this.snooperGui).size();
   }

   protected void elementClicked(int par1, boolean par2) {}

   protected boolean isSelected(int par1) {
      return false;
   }

   protected void drawBackground() {}

   protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator) {
      this.snooperGui.fontRenderer.drawString((String)GuiSnooper.func_74095_a(this.snooperGui).get(par1), 10, par3, 16777215);
      this.snooperGui.fontRenderer.drawString((String)GuiSnooper.func_74094_b(this.snooperGui).get(par1), 230, par3, 16777215);
   }

   protected int getScrollBarX() {
      return this.snooperGui.width - 10;
   }
}
