package net.minecraft.src;

import java.util.Random;

public class EntitySheep extends EntityAnimal {
   private final InventoryCrafting field_90016_e = new InventoryCrafting(new ContainerSheep(this), 2, 1);
   public static final float[][] fleeceColorTable = new float[][]{{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.4F, 0.5F, 0.2F}, {0.6F, 0.2F, 0.2F}, {0.1F, 0.1F, 0.1F}};
   private int sheepTimer;
   private EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);

   public EntitySheep(World par1World) {
      super(par1World);
      this.setSize(0.9F, 1.3F);
      this.getNavigator().setAvoidsWater(true);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
      this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
      this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Item.wheat.itemID, false));
      this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
      this.tasks.addTask(5, this.aiEatGrass);
      this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.field_90016_e.setInventorySlotContents(0, new ItemStack(Item.dyePowder, 1, 0));
      this.field_90016_e.setInventorySlotContents(1, new ItemStack(Item.dyePowder, 1, 0));
   }

   protected boolean isAIEnabled() {
      return true;
   }

   protected void updateAITasks() {
      this.sheepTimer = this.aiEatGrass.getEatGrassTick();
      super.updateAITasks();
   }

   public void onLivingUpdate() {
      if(this.worldObj.isRemote) {
         this.sheepTimer = Math.max(0, this.sheepTimer - 1);
      }

      super.onLivingUpdate();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(8.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.23000000417232513D);
   }

   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, new Byte((byte)0));
   }

   protected void dropFewItems(boolean par1, int par2) {
      if(!this.getSheared()) {
         this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 0.0F);
      }
   }

   protected int getDropItemId() {
      return Block.cloth.blockID;
   }

   public void handleHealthUpdate(byte par1) {
      if(par1 == 10) {
         this.sheepTimer = 40;
      } else {
         super.handleHealthUpdate(par1);
      }
   }

   public float func_70894_j(float par1) {
      return this.sheepTimer <= 0?0.0F:(this.sheepTimer >= 4 && this.sheepTimer <= 36?1.0F:(this.sheepTimer < 4?((float)this.sheepTimer - par1) / 4.0F:-((float)(this.sheepTimer - 40) - par1) / 4.0F));
   }

   public float func_70890_k(float par1) {
      if(this.sheepTimer > 4 && this.sheepTimer <= 36) {
         float var2 = ((float)(this.sheepTimer - 4) - par1) / 32.0F;
         return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(var2 * 28.7F);
      } else {
         return this.sheepTimer > 0?((float)Math.PI / 5F):this.rotationPitch / (180F / (float)Math.PI);
      }
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
      if(var2 != null && var2.itemID == Item.shears.itemID && !this.getSheared() && !this.isChild()) {
         if(!this.worldObj.isRemote) {
            this.setSheared(true);
            int var3 = 1 + this.rand.nextInt(3);

            for(int var4 = 0; var4 < var3; ++var4) {
               EntityItem var5 = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0F);
               var5.motionY += (double)(this.rand.nextFloat() * 0.05F);
               var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
               var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
            }
         }

         var2.damageItem(1, par1EntityPlayer);
         this.playSound("mob.sheep.shear", 1.0F, 1.0F);
      }

      return super.interact(par1EntityPlayer);
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setBoolean("Sheared", this.getSheared());
      par1NBTTagCompound.setByte("Color", (byte)this.getFleeceColor());
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.setSheared(par1NBTTagCompound.getBoolean("Sheared"));
      this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
   }

   protected String getLivingSound() {
      return "mob.sheep.say";
   }

   protected String getHurtSound() {
      return "mob.sheep.say";
   }

   protected String getDeathSound() {
      return "mob.sheep.say";
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
      this.playSound("mob.sheep.step", 0.15F, 1.0F);
   }

   public int getFleeceColor() {
      return this.dataWatcher.getWatchableObjectByte(16) & 15;
   }

   public void setFleeceColor(int par1) {
      byte var2 = this.dataWatcher.getWatchableObjectByte(16);
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | par1 & 15)));
   }

   public boolean getSheared() {
      return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
   }

   public void setSheared(boolean par1) {
      byte var2 = this.dataWatcher.getWatchableObjectByte(16);
      if(par1) {
         this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 16)));
      } else {
         this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -17)));
      }
   }

   public static int getRandomFleeceColor(Random par0Random) {
      int var1 = par0Random.nextInt(100);
      return var1 < 5?15:(var1 < 10?7:(var1 < 15?8:(var1 < 18?12:(par0Random.nextInt(500) == 0?6:0))));
   }

   public EntitySheep func_90015_b(EntityAgeable par1EntityAgeable) {
      EntitySheep var2 = (EntitySheep)par1EntityAgeable;
      EntitySheep var3 = new EntitySheep(this.worldObj);
      int var4 = this.func_90014_a(this, var2);
      var3.setFleeceColor(15 - var4);
      return var3;
   }

   public void eatGrassBonus() {
      this.setSheared(false);
      if(this.isChild()) {
         this.addGrowth(60);
      }
   }

   public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData) {
      par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
      this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
      return par1EntityLivingData;
   }

   private int func_90014_a(EntityAnimal par1EntityAnimal, EntityAnimal par2EntityAnimal) {
      int var3 = this.func_90013_b(par1EntityAnimal);
      int var4 = this.func_90013_b(par2EntityAnimal);
      this.field_90016_e.getStackInSlot(0).setItemDamage(var3);
      this.field_90016_e.getStackInSlot(1).setItemDamage(var4);
      ItemStack var5 = CraftingManager.getInstance().findMatchingRecipe(this.field_90016_e, ((EntitySheep)par1EntityAnimal).worldObj);
      int var6;
      if(var5 != null && var5.getItem().itemID == Item.dyePowder.itemID) {
         var6 = var5.getItemDamage();
      } else {
         var6 = this.worldObj.rand.nextBoolean()?var3:var4;
      }

      return var6;
   }

   private int func_90013_b(EntityAnimal par1EntityAnimal) {
      return 15 - ((EntitySheep)par1EntityAnimal).getFleeceColor();
   }

   public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
      return this.func_90015_b(par1EntityAgeable);
   }
}
