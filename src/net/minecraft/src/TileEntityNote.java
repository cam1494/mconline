package net.minecraft.src;

public class TileEntityNote extends TileEntity {
   public byte note;
   public boolean previousRedstoneState;

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setByte("note", this.note);
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.note = par1NBTTagCompound.getByte("note");
      if(this.note < 0) {
         this.note = 0;
      }

      if(this.note > 24) {
         this.note = 24;
      }
   }

   public void changePitch() {
      this.note = (byte)((this.note + 1) % 25);
      this.onInventoryChanged();
   }

   public void triggerNote(World par1World, int par2, int par3, int par4) {
      if(par1World.getBlockMaterial(par2, par3 + 1, par4) == Material.air) {
         Material var5 = par1World.getBlockMaterial(par2, par3 - 1, par4);
         byte var6 = 0;
         if(var5 == Material.rock) {
            var6 = 1;
         }

         if(var5 == Material.sand) {
            var6 = 2;
         }

         if(var5 == Material.glass) {
            var6 = 3;
         }

         if(var5 == Material.wood) {
            var6 = 4;
         }

         par1World.addBlockEvent(par2, par3, par4, Block.music.blockID, var6, this.note);
      }
   }
}
