package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRecord extends Item {
   private static final Map records = new HashMap();
   public final String recordName;

   protected ItemRecord(int par1, String par2Str) {
      super(par1);
      this.recordName = par2Str;
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabMisc);
      records.put(par2Str, this);
   }

   public Icon getIconFromDamage(int par1) {
      return this.itemIcon;
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par3World.getBlockId(par4, par5, par6) == Block.jukebox.blockID && par3World.getBlockMetadata(par4, par5, par6) == 0) {
         if(par3World.isRemote) {
            return true;
         } else {
            ((BlockJukeBox)Block.jukebox).insertRecord(par3World, par4, par5, par6, par1ItemStack);
            par3World.playAuxSFXAtEntity((EntityPlayer)null, 1005, par4, par5, par6, this.itemID);
            --par1ItemStack.stackSize;
            return true;
         }
      } else {
         return false;
      }
   }

   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      par3List.add(this.getRecordTitle());
   }

   public String getRecordTitle() {
      return "C418 - " + this.recordName;
   }

   public EnumRarity getRarity(ItemStack par1ItemStack) {
      return EnumRarity.rare;
   }

   public static ItemRecord getRecord(String par0Str) {
      return (ItemRecord)records.get(par0Str);
   }
}
