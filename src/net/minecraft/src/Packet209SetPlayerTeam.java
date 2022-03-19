package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Packet209SetPlayerTeam extends Packet {
   public String teamName = "";
   public String teamDisplayName = "";
   public String teamPrefix = "";
   public String teamSuffix = "";
   public Collection playerNames = new ArrayList();
   public int mode;
   public int friendlyFire;

   public Packet209SetPlayerTeam() {}

   public Packet209SetPlayerTeam(ScorePlayerTeam par1ScorePlayerTeam, int par2) {
      this.teamName = par1ScorePlayerTeam.func_96661_b();
      this.mode = par2;
      if(par2 == 0 || par2 == 2) {
         this.teamDisplayName = par1ScorePlayerTeam.func_96669_c();
         this.teamPrefix = par1ScorePlayerTeam.getColorPrefix();
         this.teamSuffix = par1ScorePlayerTeam.getColorSuffix();
         this.friendlyFire = par1ScorePlayerTeam.func_98299_i();
      }

      if(par2 == 0) {
         this.playerNames.addAll(par1ScorePlayerTeam.getMembershipCollection());
      }
   }

   public Packet209SetPlayerTeam(ScorePlayerTeam par1ScorePlayerTeam, Collection par2Collection, int par3) {
      if(par3 != 3 && par3 != 4) {
         throw new IllegalArgumentException("Method must be join or leave for player constructor");
      } else if(par2Collection != null && !par2Collection.isEmpty()) {
         this.mode = par3;
         this.teamName = par1ScorePlayerTeam.func_96661_b();
         this.playerNames.addAll(par2Collection);
      } else {
         throw new IllegalArgumentException("Players cannot be null/empty");
      }
   }

   public void readPacketData(DataInput par1DataInput) throws IOException {
      this.teamName = readString(par1DataInput, 16);
      this.mode = par1DataInput.readByte();
      if(this.mode == 0 || this.mode == 2) {
         this.teamDisplayName = readString(par1DataInput, 32);
         this.teamPrefix = readString(par1DataInput, 16);
         this.teamSuffix = readString(par1DataInput, 16);
         this.friendlyFire = par1DataInput.readByte();
      }

      if(this.mode == 0 || this.mode == 3 || this.mode == 4) {
         short var2 = par1DataInput.readShort();

         for(int var3 = 0; var3 < var2; ++var3) {
            this.playerNames.add(readString(par1DataInput, 16));
         }
      }
   }

   public void writePacketData(DataOutput par1DataOutput) throws IOException {
      writeString(this.teamName, par1DataOutput);
      par1DataOutput.writeByte(this.mode);
      if(this.mode == 0 || this.mode == 2) {
         writeString(this.teamDisplayName, par1DataOutput);
         writeString(this.teamPrefix, par1DataOutput);
         writeString(this.teamSuffix, par1DataOutput);
         par1DataOutput.writeByte(this.friendlyFire);
      }

      if(this.mode == 0 || this.mode == 3 || this.mode == 4) {
         par1DataOutput.writeShort(this.playerNames.size());
         Iterator var2 = this.playerNames.iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            writeString(var3, par1DataOutput);
         }
      }
   }

   public void processPacket(NetHandler par1NetHandler) {
      par1NetHandler.handleSetPlayerTeam(this);
   }

   public int getPacketSize() {
      return 3 + this.teamName.length();
   }
}
