package net.minecraft.src;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class GuiSelectWorld extends GuiScreen {
   private final DateFormat dateFormatter = new SimpleDateFormat();
   protected GuiScreen parentScreen;
   protected String screenTitle = "Select world";
   private boolean selected;
   private int selectedWorld;
   private List saveList;
   private GuiWorldSlot worldSlotContainer;
   private String localizedWorldText;
   private String localizedMustConvertText;
   private String[] localizedGameModeText = new String[3];
   private boolean deleting;
   private GuiButton buttonDelete;
   private GuiButton buttonSelect;
   private GuiButton buttonRename;
   private GuiButton buttonRecreate;

   public GuiSelectWorld(GuiScreen par1GuiScreen) {
      this.parentScreen = par1GuiScreen;
   }

   public void initGui() {
      this.screenTitle = I18n.getString("selectWorld.title");

      try {
         this.loadSaves();
      } catch (AnvilConverterException var2) {
         var2.printStackTrace();
         this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load words", var2.getMessage()));
         return;
      }

      this.localizedWorldText = I18n.getString("selectWorld.world");
      this.localizedMustConvertText = I18n.getString("selectWorld.conversion");
      this.localizedGameModeText[EnumGameType.SURVIVAL.getID()] = I18n.getString("gameMode.survival");
      this.localizedGameModeText[EnumGameType.CREATIVE.getID()] = I18n.getString("gameMode.creative");
      this.localizedGameModeText[EnumGameType.ADVENTURE.getID()] = I18n.getString("gameMode.adventure");
      this.worldSlotContainer = new GuiWorldSlot(this);
      this.worldSlotContainer.registerScrollButtons(4, 5);
      this.initButtons();
   }

   private void loadSaves() throws AnvilConverterException {
      ISaveFormat var1 = this.mc.getSaveLoader();
      this.saveList = var1.getSaveList();
      Collections.sort(this.saveList);
      this.selectedWorld = -1;
   }

   protected String getSaveFileName(int par1) {
      return ((SaveFormatComparator)this.saveList.get(par1)).getFileName();
   }

   protected String getSaveName(int par1) {
      String var2 = ((SaveFormatComparator)this.saveList.get(par1)).getDisplayName();
      if(var2 == null || MathHelper.stringNullOrLengthZero(var2)) {
         var2 = I18n.getString("selectWorld.world") + " " + (par1 + 1);
      }

      return var2;
   }

   public void initButtons() {
      this.buttonList.add(this.buttonSelect = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.getString("selectWorld.select")));
      this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.getString("selectWorld.create")));
      this.buttonList.add(this.buttonRename = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.getString("selectWorld.rename")));
      this.buttonList.add(this.buttonDelete = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.getString("selectWorld.delete")));
      this.buttonList.add(this.buttonRecreate = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.getString("selectWorld.recreate")));
      this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.getString("gui.cancel")));
      this.buttonSelect.enabled = false;
      this.buttonDelete.enabled = false;
      this.buttonRename.enabled = false;
      this.buttonRecreate.enabled = false;
   }

   protected void actionPerformed(GuiButton par1GuiButton) {
      if(par1GuiButton.enabled) {
         if(par1GuiButton.id == 2) {
            String var2 = this.getSaveName(this.selectedWorld);
            if(var2 != null) {
               this.deleting = true;
               GuiYesNo var3 = getDeleteWorldScreen(this, var2, this.selectedWorld);
               this.mc.displayGuiScreen(var3);
            }
         } else if(par1GuiButton.id == 1) {
            this.selectWorld(this.selectedWorld);
         } else if(par1GuiButton.id == 3) {
            this.mc.displayGuiScreen(new GuiCreateWorld(this));
         } else if(par1GuiButton.id == 6) {
            this.mc.displayGuiScreen(new GuiRenameWorld(this, this.getSaveFileName(this.selectedWorld)));
         } else if(par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if(par1GuiButton.id == 7) {
            GuiCreateWorld var5 = new GuiCreateWorld(this);
            ISaveHandler var6 = this.mc.getSaveLoader().getSaveLoader(this.getSaveFileName(this.selectedWorld), false);
            WorldInfo var4 = var6.loadWorldInfo();
            var6.flush();
            var5.func_82286_a(var4);
            this.mc.displayGuiScreen(var5);
         } else {
            this.worldSlotContainer.actionPerformed(par1GuiButton);
         }
      }
   }

   public void selectWorld(int par1) {
      this.mc.displayGuiScreen((GuiScreen)null);
      if(!this.selected) {
         this.selected = true;
         String var2 = this.getSaveFileName(par1);
         if(var2 == null) {
            var2 = "World" + par1;
         }

         String var3 = this.getSaveName(par1);
         if(var3 == null) {
            var3 = "World" + par1;
         }

         if(this.mc.getSaveLoader().canLoadWorld(var2)) {
            this.mc.launchIntegratedServer(var2, var3, (WorldSettings)null);
            this.mc.statFileWriter.readStat(StatList.loadWorldStat, 1);
         }
      }
   }

   public void confirmClicked(boolean par1, int par2) {
      if(this.deleting) {
         this.deleting = false;
         if(par1) {
            ISaveFormat var3 = this.mc.getSaveLoader();
            var3.flushCache();
            var3.deleteWorldDirectory(this.getSaveFileName(par2));

            try {
               this.loadSaves();
            } catch (AnvilConverterException var5) {
               var5.printStackTrace();
            }
         }

         this.mc.displayGuiScreen(this);
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.worldSlotContainer.drawScreen(par1, par2, par3);
      this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
      super.drawScreen(par1, par2, par3);
   }

   public static GuiYesNo getDeleteWorldScreen(GuiScreen par0GuiScreen, String par1Str, int par2) {
      String var3 = I18n.getString("selectWorld.deleteQuestion");
      String var4 = "\'" + par1Str + "\' " + I18n.getString("selectWorld.deleteWarning");
      String var5 = I18n.getString("selectWorld.deleteButton");
      String var6 = I18n.getString("gui.cancel");
      GuiYesNo var7 = new GuiYesNo(par0GuiScreen, var3, var4, var5, var6, par2);
      return var7;
   }

   static List getSize(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.saveList;
   }

   static int onElementSelected(GuiSelectWorld par0GuiSelectWorld, int par1) {
      return par0GuiSelectWorld.selectedWorld = par1;
   }

   static int getSelectedWorld(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.selectedWorld;
   }

   static GuiButton getSelectButton(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.buttonSelect;
   }

   static GuiButton getRenameButton(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.buttonDelete;
   }

   static GuiButton getDeleteButton(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.buttonRename;
   }

   static GuiButton func_82312_f(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.buttonRecreate;
   }

   static String func_82313_g(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.localizedWorldText;
   }

   static DateFormat func_82315_h(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.dateFormatter;
   }

   static String func_82311_i(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.localizedMustConvertText;
   }

   static String[] func_82314_j(GuiSelectWorld par0GuiSelectWorld) {
      return par0GuiSelectWorld.localizedGameModeText;
   }
}
