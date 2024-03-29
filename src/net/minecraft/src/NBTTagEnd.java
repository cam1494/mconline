package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {
   public NBTTagEnd() {
      super((String)null);
   }

   void load(DataInput par1DataInput, int par2) throws IOException {}

   void write(DataOutput par1DataOutput) throws IOException {}

   public byte getId() {
      return (byte)0;
   }

   public String toString() {
      return "END";
   }

   public NBTBase copy() {
      return new NBTTagEnd();
   }
}
