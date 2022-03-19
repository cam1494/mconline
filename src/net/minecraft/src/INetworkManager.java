package net.minecraft.src;

import java.net.SocketAddress;

public interface INetworkManager {
   void setNetHandler(NetHandler var1);

   void addToSendQueue(Packet var1);

   void wakeThreads();

   void processReadPackets();

   SocketAddress getSocketAddress();

   void serverShutdown();

   int packetSize();

   void networkShutdown(String var1, Object ... var2);

   void closeConnections();
}
