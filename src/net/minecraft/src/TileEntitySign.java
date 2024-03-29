package net.minecraft.src;

public class TileEntitySign extends TileEntity {
   public String[] signText = new String[]{"", "", "", ""};
   public int lineBeingEdited = -1;
   private boolean isEditable = true;
   private EntityPlayer field_142011_d;

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setString("Text1", this.signText[0]);
      par1NBTTagCompound.setString("Text2", this.signText[1]);
      par1NBTTagCompound.setString("Text3", this.signText[2]);
      par1NBTTagCompound.setString("Text4", this.signText[3]);
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.isEditable = false;
      super.readFromNBT(par1NBTTagCompound);

      for(int var2 = 0; var2 < 4; ++var2) {
         this.signText[var2] = par1NBTTagCompound.getString("Text" + (var2 + 1));
         if(this.signText[var2].length() > 15) {
            this.signText[var2] = this.signText[var2].substring(0, 15);
         }
      }
   }

   public Packet getDescriptionPacket() {
      String[] var1 = new String[4];
      System.arraycopy(this.signText, 0, var1, 0, 4);
      return new Packet130UpdateSign(this.xCoord, this.yCoord, this.zCoord, var1);
   }

   public boolean isEditable() {
      return this.isEditable;
   }

   public void setEditable(boolean par1) {
      this.isEditable = par1;
      if(!par1) {
         this.field_142011_d = null;
      }
   }

   public void func_142010_a(EntityPlayer par1EntityPlayer) {
      this.field_142011_d = par1EntityPlayer;
   }

   public EntityPlayer func_142009_b() {
      return this.field_142011_d;
   }
}
