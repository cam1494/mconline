package net.minecraft.src;

public class TileEntitySkull extends TileEntity {
   private int skullType;
   private int skullRotation;
   private String extraType = "";

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setByte("SkullType", (byte)(this.skullType & 255));
      par1NBTTagCompound.setByte("Rot", (byte)(this.skullRotation & 255));
      par1NBTTagCompound.setString("ExtraType", this.extraType);
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.skullType = par1NBTTagCompound.getByte("SkullType");
      this.skullRotation = par1NBTTagCompound.getByte("Rot");
      if(par1NBTTagCompound.hasKey("ExtraType")) {
         this.extraType = par1NBTTagCompound.getString("ExtraType");
      }
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 4, var1);
   }

   public void setSkullType(int par1, String par2Str) {
      this.skullType = par1;
      this.extraType = par2Str;
   }

   public int getSkullType() {
      return this.skullType;
   }

   public int func_82119_b() {
      return this.skullRotation;
   }

   public void setSkullRotation(int par1) {
      this.skullRotation = par1;
   }

   public String getExtraType() {
      return this.extraType;
   }
}
