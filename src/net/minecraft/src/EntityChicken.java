package net.minecraft.src;

public class EntityChicken extends EntityAnimal {
   public float field_70886_e;
   public float destPos;
   public float field_70884_g;
   public float field_70888_h;
   public float field_70889_i = 1.0F;
   public int timeUntilNextEgg;

   public EntityChicken(World par1World) {
      super(par1World);
      this.setSize(0.3F, 0.7F);
      this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
      this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
      this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Item.seeds.itemID, false));
      this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
      this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
      this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.tasks.addTask(7, new EntityAILookIdle(this));
   }

   public boolean isAIEnabled() {
      return true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(4.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.field_70888_h = this.field_70886_e;
      this.field_70884_g = this.destPos;
      this.destPos = (float)((double)this.destPos + (double)(this.onGround?-1:4) * 0.3D);
      if(this.destPos < 0.0F) {
         this.destPos = 0.0F;
      }

      if(this.destPos > 1.0F) {
         this.destPos = 1.0F;
      }

      if(!this.onGround && this.field_70889_i < 1.0F) {
         this.field_70889_i = 1.0F;
      }

      this.field_70889_i = (float)((double)this.field_70889_i * 0.9D);
      if(!this.onGround && this.motionY < 0.0D) {
         this.motionY *= 0.6D;
      }

      this.field_70886_e += this.field_70889_i * 2.0F;
      if(!this.isChild() && !this.worldObj.isRemote && --this.timeUntilNextEgg <= 0) {
         this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         this.dropItem(Item.egg.itemID, 1);
         this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
      }
   }

   protected void fall(float par1) {}

   protected String getLivingSound() {
      return "mob.chicken.say";
   }

   protected String getHurtSound() {
      return "mob.chicken.hurt";
   }

   protected String getDeathSound() {
      return "mob.chicken.hurt";
   }

   protected void playStepSound(int par1, int par2, int par3, int par4) {
      this.playSound("mob.chicken.step", 0.15F, 1.0F);
   }

   protected int getDropItemId() {
      return Item.feather.itemID;
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Item.feather.itemID, 1);
      }

      if(this.isBurning()) {
         this.dropItem(Item.chickenCooked.itemID, 1);
      } else {
         this.dropItem(Item.chickenRaw.itemID, 1);
      }
   }

   public EntityChicken spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
      return new EntityChicken(this.worldObj);
   }

   public boolean isBreedingItem(ItemStack par1ItemStack) {
      return par1ItemStack != null && par1ItemStack.getItem() instanceof ItemSeeds;
   }

   public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
      return this.spawnBabyAnimal(par1EntityAgeable);
   }
}
