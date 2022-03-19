package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;

public class GuiScreenLongRunningTask extends GuiScreen {
   private final int field_96213_b = 666;
   private final GuiScreen previousScreen;
   private final Thread taskThread;
   private volatile String message = "";
   private volatile boolean taskFailed;
   private volatile String failedMessage;
   private volatile boolean screenWasClosed;
   private int progressCounter;
   private TaskLongRunning task;
   public static final String[] PROGRESS_TEXT = new String[]{"\u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192", "_ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e", "_ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026", "_ _ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020", "_ _ _ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021", "_ _ _ _ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6", "_ _ _ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021", "_ _ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020", "_ _ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026", "_ \u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e", "\u00e2\u2013\u0192 \u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192", "\u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _", "\u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _", "\u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _ _", "\u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _ _ _", "\u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _ _ _ _", "\u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _ _ _", "\u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _ _", "\u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _ _", "\u00e2\u2013\u201e \u00e2\u2013\u2026 \u00e2\u2013\u2020 \u00e2\u2013\u2021 \u00e2\u2013\u02c6 \u00e2\u2013\u2021 \u00e2\u2013\u2020 \u00e2\u2013\u2026 \u00e2\u2013\u201e \u00e2\u2013\u0192 _"};

   public GuiScreenLongRunningTask(Minecraft par1Minecraft, GuiScreen par2GuiScreen, TaskLongRunning par3TaskLongRunning) {
      super.buttonList = Collections.synchronizedList(new ArrayList());
      this.mc = par1Minecraft;
      this.previousScreen = par2GuiScreen;
      this.task = par3TaskLongRunning;
      par3TaskLongRunning.setGUI(this);
      this.taskThread = new Thread(par3TaskLongRunning);
   }

   public void func_98117_g() {
      this.taskThread.start();
   }

   public void updateScreen() {
      super.updateScreen();
      ++this.progressCounter;
      this.task.updateScreen();
   }

   protected void keyTyped(char par1, int par2) {}

   public void initGui() {
      this.task.initGUI();
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.id == 666) {
         this.screenWasClosed = true;
         this.mc.displayGuiScreen(this.previousScreen);
      }

      this.task.buttonClicked(par1GuiButton);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, this.message, this.width / 2, this.height / 2 - 50, 16777215);
      this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 16777215);
      if(!this.taskFailed) {
         this.drawCenteredString(this.fontRenderer, PROGRESS_TEXT[this.progressCounter % PROGRESS_TEXT.length], this.width / 2, this.height / 2 + 15, 8421504);
      }

      if(this.taskFailed) {
         this.drawCenteredString(this.fontRenderer, this.failedMessage, this.width / 2, this.height / 2 + 15, 16711680);
      }

      super.drawScreen(par1, par2, par3);
   }

   public void setFailedMessage(String par1Str) {
      this.taskFailed = true;
      this.failedMessage = par1Str;
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(666, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
   }

   public Minecraft func_96208_g() {
      return this.mc;
   }

   public void setMessage(String par1Str) {
      this.message = par1Str;
   }

   public boolean wasScreenClosed() {
      return this.screenWasClosed;
   }
}
