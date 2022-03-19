package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class TileEntityCommandBlock extends TileEntity implements ICommandSender {
   private int succesCount;
   private String command = "";
   private String commandSenderName = "@";

   public void setCommand(String par1Str) {
      this.command = par1Str;
      this.onInventoryChanged();
   }

   public String getCommand() {
      return this.command;
   }

   public int executeCommandOnPowered(World par1World) {
      if(par1World.isRemote) {
         return 0;
      } else {
         MinecraftServer var2 = MinecraftServer.getServer();
         if(var2 != null && var2.isCommandBlockEnabled()) {
            ICommandManager var3 = var2.getCommandManager();
            return var3.executeCommand(this, this.command);
         } else {
            return 0;
         }
      }
   }

   public String getCommandSenderName() {
      return this.commandSenderName;
   }

   public void setCommandSenderName(String par1Str) {
      this.commandSenderName = par1Str;
   }

   public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent) {}

   public boolean canCommandSenderUseCommand(int par1, String par2Str) {
      return par1 <= 2;
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setString("Command", this.command);
      par1NBTTagCompound.setInteger("SuccessCount", this.succesCount);
      par1NBTTagCompound.setString("CustomName", this.commandSenderName);
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.command = par1NBTTagCompound.getString("Command");
      this.succesCount = par1NBTTagCompound.getInteger("SuccessCount");
      if(par1NBTTagCompound.hasKey("CustomName")) {
         this.commandSenderName = par1NBTTagCompound.getString("CustomName");
      }
   }

   public ChunkCoordinates getPlayerCoordinates() {
      return new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord);
   }

   public World getEntityWorld() {
      return this.getWorldObj();
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, var1);
   }

   public int func_96103_d() {
      return this.succesCount;
   }

   public void func_96102_a(int par1) {
      this.succesCount = par1;
   }
}
