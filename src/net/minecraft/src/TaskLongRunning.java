package net.minecraft.src;

public abstract class TaskLongRunning implements Runnable {
   protected GuiScreenLongRunningTask taskGUI;

   public void setGUI(GuiScreenLongRunningTask par1GuiScreenLongRunningTask) {
      this.taskGUI = par1GuiScreenLongRunningTask;
   }

   public void setFailedMessage(String par1Str) {
      this.taskGUI.setFailedMessage(par1Str);
   }

   public void setMessage(String par1Str) {
      this.taskGUI.setMessage(par1Str);
   }

   public Minecraft getMinecraft() {
      return this.taskGUI.func_96208_g();
   }

   public boolean wasScreenClosed() {
      return this.taskGUI.wasScreenClosed();
   }

   public void updateScreen() {}

   public void buttonClicked(GuiButton par1GuiButton) {}

   public void initGUI() {}
}
