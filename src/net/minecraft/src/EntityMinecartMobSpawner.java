package net.minecraft.src;

public class EntityMinecartMobSpawner extends EntityMinecart {
   private final MobSpawnerBaseLogic mobSpawnerLogic = new EntityMinecartMobSpawnerLogic(this);

   public EntityMinecartMobSpawner(World par1World) {
      super(par1World);
   }

   public EntityMinecartMobSpawner(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
   }

   public int getMinecartType() {
      return 4;
   }

   public Block getDefaultDisplayTile() {
      return Block.mobSpawner;
   }

   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
   }

   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      this.mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
   }

   public void handleHealthUpdate(byte par1) {
      this.mobSpawnerLogic.setDelayToMin(par1);
   }

   public void onUpdate() {
      super.onUpdate();
      this.mobSpawnerLogic.updateSpawner();
   }

   public MobSpawnerBaseLogic func_98039_d() {
      return this.mobSpawnerLogic;
   }
}
