package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOff extends CommandBase {
   public String getCommandName() {
      return "save-off";
   }

   public int getRequiredPermissionLevel() {
      return 4;
   }

   public String getCommandUsage(ICommandSender par1ICommandSender) {
      return "commands.save-off.usage";
   }

   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      MinecraftServer var3 = MinecraftServer.getServer();
      boolean var4 = false;

      for(int var5 = 0; var5 < var3.worldServers.length; ++var5) {
         if(var3.worldServers[var5] != null) {
            WorldServer var6 = var3.worldServers[var5];
            if(!var6.canNotSave) {
               var6.canNotSave = true;
               var4 = true;
            }
         }
      }

      if(var4) {
         notifyAdmins(par1ICommandSender, "commands.save.disabled", new Object[0]);
      } else {
         throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
      }
   }
}
