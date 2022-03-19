package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class RConConsoleSource implements ICommandSender {
   public static final RConConsoleSource consoleBuffer = new RConConsoleSource();
   private StringBuffer buffer = new StringBuffer();

   public void resetLog() {
      this.buffer.setLength(0);
   }

   public String getChatBuffer() {
      return this.buffer.toString();
   }

   public String getCommandSenderName() {
      return "Rcon";
   }

   public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent) {
      this.buffer.append(par1ChatMessageComponent.toString());
   }

   public boolean canCommandSenderUseCommand(int par1, String par2Str) {
      return true;
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return new ChunkCoordinates(0, 0, 0);
   }

   public World getEntityWorld() {
      return MinecraftServer.getServer().getEntityWorld();
   }
}
