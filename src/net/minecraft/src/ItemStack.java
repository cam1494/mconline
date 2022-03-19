package net.minecraft.src;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

public final class ItemStack {
   public static final DecimalFormat field_111284_a = new DecimalFormat("#.###");
   public int stackSize;
   public int animationsToGo;
   public int itemID;
   public NBTTagCompound stackTagCompound;
   private int itemDamage;
   private EntityItemFrame itemFrame;

   public ItemStack(Block par1Block) {
      this(par1Block, 1);
   }

   public ItemStack(Block par1Block, int par2) {
      this(par1Block.blockID, par2, 0);
   }

   public ItemStack(Block par1Block, int par2, int par3) {
      this(par1Block.blockID, par2, par3);
   }

   public ItemStack(Item par1Item) {
      this(par1Item.itemID, 1, 0);
   }

   public ItemStack(Item par1Item, int par2) {
      this(par1Item.itemID, par2, 0);
   }

   public ItemStack(Item par1Item, int par2, int par3) {
      this(par1Item.itemID, par2, par3);
   }

   public ItemStack(int par1, int par2, int par3) {
      this.itemID = par1;
      this.stackSize = par2;
      this.itemDamage = par3;
      if(this.itemDamage < 0) {
         this.itemDamage = 0;
      }
   }

   public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound) {
      ItemStack var1 = new ItemStack();
      var1.readFromNBT(par0NBTTagCompound);
      return var1.getItem() != null?var1:null;
   }

   private ItemStack() {}

   public ItemStack splitStack(int par1) {
      ItemStack var2 = new ItemStack(this.itemID, par1, this.itemDamage);
      if(this.stackTagCompound != null) {
         var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      this.stackSize -= par1;
      return var2;
   }

   public Item getItem() {
      return Item.itemsList[this.itemID];
   }

   public Icon getIconIndex() {
      return this.getItem().getIconIndex(this);
   }

   public int getItemSpriteNumber() {
      return this.getItem().getSpriteNumber();
   }

   public boolean tryPlaceItemIntoWorld(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6, float par7, float par8, float par9) {
      boolean var10 = this.getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6, par7, par8, par9);
      if(var10) {
         par1EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
      }

      return var10;
   }

   public float getStrVsBlock(Block par1Block) {
      return this.getItem().getStrVsBlock(this, par1Block);
   }

   public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer) {
      return this.getItem().onItemRightClick(this, par1World, par2EntityPlayer);
   }

   public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer) {
      return this.getItem().onEaten(this, par1World, par2EntityPlayer);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setShort("id", (short)this.itemID);
      par1NBTTagCompound.setByte("Count", (byte)this.stackSize);
      par1NBTTagCompound.setShort("Damage", (short)this.itemDamage);
      if(this.stackTagCompound != null) {
         par1NBTTagCompound.setTag("tag", this.stackTagCompound);
      }

      return par1NBTTagCompound;
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.itemID = par1NBTTagCompound.getShort("id");
      this.stackSize = par1NBTTagCompound.getByte("Count");
      this.itemDamage = par1NBTTagCompound.getShort("Damage");
      if(this.itemDamage < 0) {
         this.itemDamage = 0;
      }

      if(par1NBTTagCompound.hasKey("tag")) {
         this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
      }
   }

   public int getMaxStackSize() {
      return this.getItem().getItemStackLimit();
   }

   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
   }

   public boolean isItemStackDamageable() {
      return Item.itemsList[this.itemID].getMaxDamage() > 0;
   }

   public boolean getHasSubtypes() {
      return Item.itemsList[this.itemID].getHasSubtypes();
   }

   public boolean isItemDamaged() {
      return this.isItemStackDamageable() && this.itemDamage > 0;
   }

   public int getItemDamageForDisplay() {
      return this.itemDamage;
   }

   public int getItemDamage() {
      return this.itemDamage;
   }

   public void setItemDamage(int par1) {
      this.itemDamage = par1;
      if(this.itemDamage < 0) {
         this.itemDamage = 0;
      }
   }

   public int getMaxDamage() {
      return Item.itemsList[this.itemID].getMaxDamage();
   }

   public boolean attemptDamageItem(int par1, Random par2Random) {
      if(!this.isItemStackDamageable()) {
         return false;
      } else {
         if(par1 > 0) {
            int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int var4 = 0;

            for(int var5 = 0; var3 > 0 && var5 < par1; ++var5) {
               if(EnchantmentDurability.negateDamage(this, var3, par2Random)) {
                  ++var4;
               }
            }

            par1 -= var4;
            if(par1 <= 0) {
               return false;
            }
         }

         this.itemDamage += par1;
         return this.itemDamage > this.getMaxDamage();
      }
   }

   public void damageItem(int par1, EntityLivingBase par2EntityLivingBase) {
      if(!(par2EntityLivingBase instanceof EntityPlayer) || !((EntityPlayer)par2EntityLivingBase).capabilities.isCreativeMode) {
         if(this.isItemStackDamageable()) {
            if(this.attemptDamageItem(par1, par2EntityLivingBase.getRNG())) {
               par2EntityLivingBase.renderBrokenItemStack(this);
               --this.stackSize;
               if(par2EntityLivingBase instanceof EntityPlayer) {
                  EntityPlayer var3 = (EntityPlayer)par2EntityLivingBase;
                  var3.addStat(StatList.objectBreakStats[this.itemID], 1);
                  if(this.stackSize == 0 && this.getItem() instanceof ItemBow) {
                     var3.destroyCurrentEquippedItem();
                  }
               }

               if(this.stackSize < 0) {
                  this.stackSize = 0;
               }

               this.itemDamage = 0;
            }
         }
      }
   }

   public void hitEntity(EntityLivingBase par1EntityLivingBase, EntityPlayer par2EntityPlayer) {
      boolean var3 = Item.itemsList[this.itemID].hitEntity(this, par1EntityLivingBase, par2EntityPlayer);
      if(var3) {
         par2EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
      }
   }

   public void onBlockDestroyed(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
      boolean var7 = Item.itemsList[this.itemID].onBlockDestroyed(this, par1World, par2, par3, par4, par5, par6EntityPlayer);
      if(var7) {
         par6EntityPlayer.addStat(StatList.objectUseStats[this.itemID], 1);
      }
   }

   public boolean canHarvestBlock(Block par1Block) {
      return Item.itemsList[this.itemID].canHarvestBlock(par1Block);
   }

   public boolean func_111282_a(EntityPlayer par1EntityPlayer, EntityLivingBase par2EntityLivingBase) {
      return Item.itemsList[this.itemID].itemInteractionForEntity(this, par1EntityPlayer, par2EntityLivingBase);
   }

   public ItemStack copy() {
      ItemStack var1 = new ItemStack(this.itemID, this.stackSize, this.itemDamage);
      if(this.stackTagCompound != null) {
         var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      return var1;
   }

   public static boolean areItemStackTagsEqual(ItemStack par0ItemStack, ItemStack par1ItemStack) {
      return par0ItemStack == null && par1ItemStack == null?true:(par0ItemStack != null && par1ItemStack != null?(par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null?false:par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound)):false);
   }

   public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack) {
      return par0ItemStack == null && par1ItemStack == null?true:(par0ItemStack != null && par1ItemStack != null?par0ItemStack.isItemStackEqual(par1ItemStack):false);
   }

   private boolean isItemStackEqual(ItemStack par1ItemStack) {
      return this.stackSize != par1ItemStack.stackSize?false:(this.itemID != par1ItemStack.itemID?false:(this.itemDamage != par1ItemStack.itemDamage?false:(this.stackTagCompound == null && par1ItemStack.stackTagCompound != null?false:this.stackTagCompound == null || this.stackTagCompound.equals(par1ItemStack.stackTagCompound))));
   }

   public boolean isItemEqual(ItemStack par1ItemStack) {
      return this.itemID == par1ItemStack.itemID && this.itemDamage == par1ItemStack.itemDamage;
   }

   public String getUnlocalizedName() {
      return Item.itemsList[this.itemID].getUnlocalizedName(this);
   }

   public static ItemStack copyItemStack(ItemStack par0ItemStack) {
      return par0ItemStack == null?null:par0ItemStack.copy();
   }

   public String toString() {
      return this.stackSize + "x" + Item.itemsList[this.itemID].getUnlocalizedName() + "@" + this.itemDamage;
   }

   public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4) {
      if(this.animationsToGo > 0) {
         --this.animationsToGo;
      }

      Item.itemsList[this.itemID].onUpdate(this, par1World, par2Entity, par3, par4);
   }

   public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3) {
      par2EntityPlayer.addStat(StatList.objectCraftStats[this.itemID], par3);
      Item.itemsList[this.itemID].onCreated(this, par1World, par2EntityPlayer);
   }

   public int getMaxItemUseDuration() {
      return this.getItem().getMaxItemUseDuration(this);
   }

   public EnumAction getItemUseAction() {
      return this.getItem().getItemUseAction(this);
   }

   public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3) {
      this.getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
   }

   public boolean hasTagCompound() {
      return this.stackTagCompound != null;
   }

   public NBTTagCompound getTagCompound() {
      return this.stackTagCompound;
   }

   public NBTTagList getEnchantmentTagList() {
      return this.stackTagCompound == null?null:(NBTTagList)this.stackTagCompound.getTag("ench");
   }

   public void setTagCompound(NBTTagCompound par1NBTTagCompound) {
      this.stackTagCompound = par1NBTTagCompound;
   }

   public String getDisplayName() {
      String var1 = this.getItem().getItemDisplayName(this);
      if(this.stackTagCompound != null && this.stackTagCompound.hasKey("display")) {
         NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");
         if(var2.hasKey("Name")) {
            var1 = var2.getString("Name");
         }
      }

      return var1;
   }

   public void setItemName(String par1Str) {
      if(this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound("tag");
      }

      if(!this.stackTagCompound.hasKey("display")) {
         this.stackTagCompound.setCompoundTag("display", new NBTTagCompound());
      }

      this.stackTagCompound.getCompoundTag("display").setString("Name", par1Str);
   }

   public void func_135074_t() {
      if(this.stackTagCompound != null) {
         if(this.stackTagCompound.hasKey("display")) {
            NBTTagCompound var1 = this.stackTagCompound.getCompoundTag("display");
            var1.removeTag("Name");
            if(var1.hasNoTags()) {
               this.stackTagCompound.removeTag("display");
               if(this.stackTagCompound.hasNoTags()) {
                  this.setTagCompound((NBTTagCompound)null);
               }
            }
         }
      }
   }

   public boolean hasDisplayName() {
      return this.stackTagCompound == null?false:(!this.stackTagCompound.hasKey("display")?false:this.stackTagCompound.getCompoundTag("display").hasKey("Name"));
   }

   public List getTooltip(EntityPlayer par1EntityPlayer, boolean par2) {
      ArrayList var3 = new ArrayList();
      Item var4 = Item.itemsList[this.itemID];
      String var5 = this.getDisplayName();
      if(this.hasDisplayName()) {
         var5 = EnumChatFormatting.ITALIC + var5 + EnumChatFormatting.RESET;
      }

      if(par2) {
         String var6 = "";
         if(var5.length() > 0) {
            var5 = var5 + " (";
            var6 = ")";
         }

         if(this.getHasSubtypes()) {
            var5 = var5 + String.format("#%04d/%d%s", new Object[]{Integer.valueOf(this.itemID), Integer.valueOf(this.itemDamage), var6});
         } else {
            var5 = var5 + String.format("#%04d%s", new Object[]{Integer.valueOf(this.itemID), var6});
         }
      } else if(!this.hasDisplayName() && this.itemID == Item.map.itemID) {
         var5 = var5 + " #" + this.itemDamage;
      }

      var3.add(var5);
      var4.addInformation(this, par1EntityPlayer, var3, par2);
      if(this.hasTagCompound()) {
         NBTTagList var14 = this.getEnchantmentTagList();
         if(var14 != null) {
            for(int var7 = 0; var7 < var14.tagCount(); ++var7) {
               short var8 = ((NBTTagCompound)var14.tagAt(var7)).getShort("id");
               short var9 = ((NBTTagCompound)var14.tagAt(var7)).getShort("lvl");
               if(Enchantment.enchantmentsList[var8] != null) {
                  var3.add(Enchantment.enchantmentsList[var8].getTranslatedName(var9));
               }
            }
         }

         if(this.stackTagCompound.hasKey("display")) {
            NBTTagCompound var16 = this.stackTagCompound.getCompoundTag("display");
            if(var16.hasKey("color")) {
               if(par2) {
                  var3.add("Color: #" + Integer.toHexString(var16.getInteger("color")).toUpperCase());
               } else {
                  var3.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
               }
            }

            if(var16.hasKey("Lore")) {
               NBTTagList var18 = var16.getTagList("Lore");
               if(var18.tagCount() > 0) {
                  for(int var20 = 0; var20 < var18.tagCount(); ++var20) {
                     var3.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + ((NBTTagString)var18.tagAt(var20)).data);
                  }
               }
            }
         }
      }

      Multimap var15 = this.getAttributeModifiers();
      if(!var15.isEmpty()) {
         var3.add("");
         Iterator var17 = var15.entries().iterator();

         while(var17.hasNext()) {
            Entry var19 = (Entry)var17.next();
            AttributeModifier var21 = (AttributeModifier)var19.getValue();
            double var10 = var21.getAmount();
            double var12;
            if(var21.getOperation() != 1 && var21.getOperation() != 2) {
               var12 = var21.getAmount();
            } else {
               var12 = var21.getAmount() * 100.0D;
            }

            if(var10 > 0.0D) {
               var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + var21.getOperation(), new Object[]{field_111284_a.format(var12), StatCollector.translateToLocal("attribute.name." + (String)var19.getKey())}));
            } else if(var10 < 0.0D) {
               var12 *= -1.0D;
               var3.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + var21.getOperation(), new Object[]{field_111284_a.format(var12), StatCollector.translateToLocal("attribute.name." + (String)var19.getKey())}));
            }
         }
      }

      if(par2 && this.isItemDamaged()) {
         var3.add("Durability: " + (this.getMaxDamage() - this.getItemDamageForDisplay()) + " / " + this.getMaxDamage());
      }

      return var3;
   }

   public boolean hasEffect() {
      return this.getItem().hasEffect(this);
   }

   public EnumRarity getRarity() {
      return this.getItem().getRarity(this);
   }

   public boolean isItemEnchantable() {
      return !this.getItem().isItemTool(this)?false:!this.isItemEnchanted();
   }

   public void addEnchantment(Enchantment par1Enchantment, int par2) {
      if(this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      if(!this.stackTagCompound.hasKey("ench")) {
         this.stackTagCompound.setTag("ench", new NBTTagList("ench"));
      }

      NBTTagList var3 = (NBTTagList)this.stackTagCompound.getTag("ench");
      NBTTagCompound var4 = new NBTTagCompound();
      var4.setShort("id", (short)par1Enchantment.effectId);
      var4.setShort("lvl", (short)((byte)par2));
      var3.appendTag(var4);
   }

   public boolean isItemEnchanted() {
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench");
   }

   public void setTagInfo(String par1Str, NBTBase par2NBTBase) {
      if(this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      this.stackTagCompound.setTag(par1Str, par2NBTBase);
   }

   public boolean canEditBlocks() {
      return this.getItem().canItemEditBlocks();
   }

   public boolean isOnItemFrame() {
      return this.itemFrame != null;
   }

   public void setItemFrame(EntityItemFrame par1EntityItemFrame) {
      this.itemFrame = par1EntityItemFrame;
   }

   public EntityItemFrame getItemFrame() {
      return this.itemFrame;
   }

   public int getRepairCost() {
      return this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost")?this.stackTagCompound.getInteger("RepairCost"):0;
   }

   public void setRepairCost(int par1) {
      if(!this.hasTagCompound()) {
         this.stackTagCompound = new NBTTagCompound("tag");
      }

      this.stackTagCompound.setInteger("RepairCost", par1);
   }

   public Multimap getAttributeModifiers() {
      Object var1;
      if(this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers")) {
         var1 = HashMultimap.create();
         NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers");

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            AttributeModifier var5 = SharedMonsterAttributes.func_111259_a(var4);
            if(var5.getID().getLeastSignificantBits() != 0L && var5.getID().getMostSignificantBits() != 0L) {
               ((Multimap)var1).put(var4.getString("AttributeName"), var5);
            }
         }
      } else {
         var1 = this.getItem().getItemAttributeModifiers();
      }

      return (Multimap)var1;
   }
}
