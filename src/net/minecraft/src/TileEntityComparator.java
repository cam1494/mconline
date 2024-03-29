package net.minecraft.src;

public class TileEntityComparator extends TileEntity {
   private int outputSignal;

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("OutputSignal", this.outputSignal);
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.outputSignal = par1NBTTagCompound.getInteger("OutputSignal");
   }

   public int getOutputSignal() {
      return this.outputSignal;
   }

   public void setOutputSignal(int par1) {
      this.outputSignal = par1;
   }
}
