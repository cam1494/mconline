package net.minecraft.src;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.server.MinecraftServer;

public class TcpConnection implements INetworkManager {
   public static AtomicInteger field_74471_a = new AtomicInteger();
   public static AtomicInteger field_74469_b = new AtomicInteger();
   private final Object sendQueueLock;
   private final ILogAgent tcpConLogAgent;
   private Socket networkSocket;
   private final SocketAddress remoteSocketAddress;
   private volatile DataInputStream socketInputStream;
   private volatile DataOutputStream socketOutputStream;
   private volatile boolean isRunning;
   private volatile boolean isTerminating;
   private Queue readPackets;
   private List dataPackets;
   private List chunkDataPackets;
   private NetHandler theNetHandler;
   private boolean isServerTerminating;
   private Thread writeThread;
   private Thread readThread;
   private String terminationReason;
   private Object[] shutdownDescription;
   private int field_74490_x;
   private int sendQueueByteLength;
   public static int[] field_74470_c = new int[256];
   public static int[] field_74467_d = new int[256];
   public int field_74468_e;
   boolean isInputBeingDecrypted;
   boolean isOutputEncrypted;
   private SecretKey sharedKeyForEncryption;
   private PrivateKey field_74463_A;
   private int chunkDataPacketsDelay;

   public TcpConnection(ILogAgent par1ILogAgent, Socket par2Socket, String par3Str, NetHandler par4NetHandler) throws IOException {
      this(par1ILogAgent, par2Socket, par3Str, par4NetHandler, (PrivateKey)null);
   }

   public TcpConnection(ILogAgent par1ILogAgent, Socket par2Socket, String par3Str, NetHandler par4NetHandler, PrivateKey par5PrivateKey) throws IOException {
      this.sendQueueLock = new Object();
      this.isRunning = true;
      this.readPackets = new ConcurrentLinkedQueue();
      this.dataPackets = Collections.synchronizedList(new ArrayList());
      this.chunkDataPackets = Collections.synchronizedList(new ArrayList());
      this.terminationReason = "";
      this.chunkDataPacketsDelay = 50;
      this.field_74463_A = par5PrivateKey;
      this.networkSocket = par2Socket;
      this.tcpConLogAgent = par1ILogAgent;
      this.remoteSocketAddress = par2Socket.getRemoteSocketAddress();
      this.theNetHandler = par4NetHandler;

      try {
         par2Socket.setSoTimeout(30000);
         par2Socket.setTrafficClass(24);
      } catch (SocketException var7) {
         System.err.println(var7.getMessage());
      }

      this.socketInputStream = new DataInputStream(par2Socket.getInputStream());
      this.socketOutputStream = new DataOutputStream(new BufferedOutputStream(par2Socket.getOutputStream(), 5120));
      this.readThread = new TcpReaderThread(this, par3Str + " read thread");
      this.writeThread = new TcpWriterThread(this, par3Str + " write thread");
      this.readThread.start();
      this.writeThread.start();
   }

   public void closeConnections() {
      this.wakeThreads();
      this.writeThread = null;
      this.readThread = null;
   }

   public void setNetHandler(NetHandler par1NetHandler) {
      this.theNetHandler = par1NetHandler;
   }

   public void addToSendQueue(Packet par1Packet) {
      if(!this.isServerTerminating) {
         Object var2 = this.sendQueueLock;
         synchronized(this.sendQueueLock) {
            this.sendQueueByteLength += par1Packet.getPacketSize() + 1;
            this.dataPackets.add(par1Packet);
         }
      }
   }

   private boolean sendPacket() {
      boolean var1 = false;

      try {
         int[] var10000;
         int var10001;
         Packet var2;
         if(this.field_74468_e == 0 || !this.dataPackets.isEmpty() && MinecraftServer.getSystemTimeMillis() - ((Packet)this.dataPackets.get(0)).creationTimeMillis >= (long)this.field_74468_e) {
            var2 = this.func_74460_a(false);
            if(var2 != null) {
               Packet.writePacket(var2, this.socketOutputStream);
               if(var2 instanceof Packet252SharedKey && !this.isOutputEncrypted) {
                  if(!this.theNetHandler.isServerHandler()) {
                     this.sharedKeyForEncryption = ((Packet252SharedKey)var2).getSharedKey();
                  }

                  this.encryptOuputStream();
               }

               var10000 = field_74467_d;
               var10001 = var2.getPacketId();
               var10000[var10001] += var2.getPacketSize() + 1;
               var1 = true;
            }
         }

         if(this.chunkDataPacketsDelay-- <= 0 && (this.field_74468_e == 0 || !this.chunkDataPackets.isEmpty() && MinecraftServer.getSystemTimeMillis() - ((Packet)this.chunkDataPackets.get(0)).creationTimeMillis >= (long)this.field_74468_e)) {
            var2 = this.func_74460_a(true);
            if(var2 != null) {
               Packet.writePacket(var2, this.socketOutputStream);
               var10000 = field_74467_d;
               var10001 = var2.getPacketId();
               var10000[var10001] += var2.getPacketSize() + 1;
               this.chunkDataPacketsDelay = 0;
               var1 = true;
            }
         }

         return var1;
      } catch (Exception var3) {
         if(!this.isTerminating) {
            this.onNetworkError(var3);
         }

         return false;
      }
   }

   private Packet func_74460_a(boolean par1) {
      Packet var2 = null;
      List var3 = par1?this.chunkDataPackets:this.dataPackets;
      Object var4 = this.sendQueueLock;
      synchronized(this.sendQueueLock) {
         while(!var3.isEmpty() && var2 == null) {
            var2 = (Packet)var3.remove(0);
            this.sendQueueByteLength -= var2.getPacketSize() + 1;
            if(this.func_74454_a(var2, par1)) {
               var2 = null;
            }
         }

         return var2;
      }
   }

   private boolean func_74454_a(Packet par1Packet, boolean par2) {
      if(!par1Packet.isRealPacket()) {
         return false;
      } else {
         List var3 = par2?this.chunkDataPackets:this.dataPackets;
         Iterator var4 = var3.iterator();

         Packet var5;
         do {
            if(!var4.hasNext()) {
               return false;
            }

            var5 = (Packet)var4.next();
         } while(var5.getPacketId() != par1Packet.getPacketId());

         return par1Packet.containsSameEntityIDAs(var5);
      }
   }

   public void wakeThreads() {
      if(this.readThread != null) {
         this.readThread.interrupt();
      }

      if(this.writeThread != null) {
         this.writeThread.interrupt();
      }
   }

   private boolean readPacket() {
      boolean var1 = false;

      try {
         Packet var2 = Packet.readPacket(this.tcpConLogAgent, this.socketInputStream, this.theNetHandler.isServerHandler(), this.networkSocket);
         if(var2 != null) {
            if(var2 instanceof Packet252SharedKey && !this.isInputBeingDecrypted) {
               if(this.theNetHandler.isServerHandler()) {
                  this.sharedKeyForEncryption = ((Packet252SharedKey)var2).getSharedKey(this.field_74463_A);
               }

               this.decryptInputStream();
            }

            int[] var10000 = field_74470_c;
            int var10001 = var2.getPacketId();
            var10000[var10001] += var2.getPacketSize() + 1;
            if(!this.isServerTerminating) {
               if(var2.canProcessAsync() && this.theNetHandler.canProcessPacketsAsync()) {
                  this.field_74490_x = 0;
                  var2.processPacket(this.theNetHandler);
               } else {
                  this.readPackets.add(var2);
               }
            }

            var1 = true;
         } else {
            this.networkShutdown("disconnect.endOfStream", new Object[0]);
         }

         return var1;
      } catch (Exception var3) {
         if(!this.isTerminating) {
            this.onNetworkError(var3);
         }

         return false;
      }
   }

   private void onNetworkError(Exception par1Exception) {
      par1Exception.printStackTrace();
      this.networkShutdown("disconnect.genericReason", new Object[]{"Internal exception: " + par1Exception.toString()});
   }

   public void networkShutdown(String par1Str, Object ... par2ArrayOfObj) {
      if(this.isRunning) {
         this.isTerminating = true;
         this.terminationReason = par1Str;
         this.shutdownDescription = par2ArrayOfObj;
         this.isRunning = false;
         (new TcpMasterThread(this)).start();

         try {
            this.socketInputStream.close();
         } catch (Throwable var6) {
            ;
         }

         try {
            this.socketOutputStream.close();
         } catch (Throwable var5) {
            ;
         }

         try {
            this.networkSocket.close();
         } catch (Throwable var4) {
            ;
         }

         this.socketInputStream = null;
         this.socketOutputStream = null;
         this.networkSocket = null;
      }
   }

   public void processReadPackets() {
      if(this.sendQueueByteLength > 2097152) {
         this.networkShutdown("disconnect.overflow", new Object[0]);
      }

      if(this.readPackets.isEmpty()) {
         if(this.field_74490_x++ == 1200) {
            this.networkShutdown("disconnect.timeout", new Object[0]);
         }
      } else {
         this.field_74490_x = 0;
      }

      int var1 = 1000;

      while(var1-- >= 0) {
         Packet var2 = (Packet)this.readPackets.poll();
         if(var2 != null && !this.theNetHandler.isConnectionClosed()) {
            var2.processPacket(this.theNetHandler);
         }
      }

      this.wakeThreads();
      if(this.isTerminating && this.readPackets.isEmpty()) {
         this.theNetHandler.handleErrorMessage(this.terminationReason, this.shutdownDescription);
      }
   }

   public SocketAddress getSocketAddress() {
      return this.remoteSocketAddress;
   }

   public void serverShutdown() {
      if(!this.isServerTerminating) {
         this.wakeThreads();
         this.isServerTerminating = true;
         this.readThread.interrupt();
         (new TcpMonitorThread(this)).start();
      }
   }

   private void decryptInputStream() throws IOException {
      this.isInputBeingDecrypted = true;
      InputStream var1 = this.networkSocket.getInputStream();
      this.socketInputStream = new DataInputStream(CryptManager.decryptInputStream(this.sharedKeyForEncryption, var1));
   }

   private void encryptOuputStream() throws IOException {
      this.socketOutputStream.flush();
      this.isOutputEncrypted = true;
      BufferedOutputStream var1 = new BufferedOutputStream(CryptManager.encryptOuputStream(this.sharedKeyForEncryption, this.networkSocket.getOutputStream()), 5120);
      this.socketOutputStream = new DataOutputStream(var1);
   }

   public int packetSize() {
      return this.chunkDataPackets.size();
   }

   public Socket getSocket() {
      return this.networkSocket;
   }

   static boolean isRunning(TcpConnection par0TcpConnection) {
      return par0TcpConnection.isRunning;
   }

   static boolean isServerTerminating(TcpConnection par0TcpConnection) {
      return par0TcpConnection.isServerTerminating;
   }

   static boolean readNetworkPacket(TcpConnection par0TcpConnection) {
      return par0TcpConnection.readPacket();
   }

   static boolean sendNetworkPacket(TcpConnection par0TcpConnection) {
      return par0TcpConnection.sendPacket();
   }

   static DataOutputStream getOutputStream(TcpConnection par0TcpConnection) {
      return par0TcpConnection.socketOutputStream;
   }

   static boolean isTerminating(TcpConnection par0TcpConnection) {
      return par0TcpConnection.isTerminating;
   }

   static void sendError(TcpConnection par0TcpConnection, Exception par1Exception) {
      par0TcpConnection.onNetworkError(par1Exception);
   }

   static Thread getReadThread(TcpConnection par0TcpConnection) {
      return par0TcpConnection.readThread;
   }

   static Thread getWriteThread(TcpConnection par0TcpConnection) {
      return par0TcpConnection.writeThread;
   }
}
