package net.minecraft.src;

public interface ICommandSender {
   String getCommandSenderName();

   void sendChatToPlayer(ChatMessageComponent var1);

   boolean canCommandSenderUseCommand(int var1, String var2);

   ChunkCoordinates getPlayerCoordinates();

   World getEntityWorld();
}
