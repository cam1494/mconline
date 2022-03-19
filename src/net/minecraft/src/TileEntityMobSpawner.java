package net.minecraft.src;

public class TileEntityMobSpawner extends TileEntity {
   private final MobSpawnerBaseLogic field_98050_a = new TileEntityMobSpawnerLogic(this);

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.field_98050_a.readFromNBT(par1NBTTagCompound);
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      this.field_98050_a.writeToNBT(par1NBTTagCompound);
   }

   public void updateEntity() {
      this.field_98050_a.updateSpawner();
      super.updateEntity();
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      var1.removeTag("SpawnPotentials");
      return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
   }

   public boolean receiveClientEvent(int par1, int par2) {
      return this.field_98050_a.setDelayToMin(par1)?true:super.receiveClientEvent(par1, par2);
   }

   public MobSpawnerBaseLogic getSpawnerLogic() {
      return this.field_98050_a;
   }
}
