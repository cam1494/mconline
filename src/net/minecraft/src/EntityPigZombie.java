package net.minecraft.src;

import java.util.List;
import java.util.UUID;

public class EntityPigZombie extends EntityZombie {
   private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final AttributeModifier field_110190_br = (new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.45D, 0)).setSaved(false);
   private int angerLevel;
   private int randomSoundDelay;
   private Entity field_110191_bu;

   public EntityPigZombie(World par1World) {
      super(par1World);
      this.isImmuneToFire = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(field_110186_bp).setAttribute(0.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.5D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(5.0D);
   }

   protected boolean isAIEnabled() {
      return false;
   }

   public void onUpdate() {
      if(this.field_110191_bu != this.entityToAttack && !this.worldObj.isRemote) {
         AttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
         var1.removeModifier(field_110190_br);
         if(this.entityToAttack != null) {
            var1.applyModifier(field_110190_br);
         }
      }

      this.field_110191_bu = this.entityToAttack;
      if(this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
         this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
      }

      super.onUpdate();
   }

   public boolean getCanSpawnHere() {
      return this.worldObj.difficultySetting > 0 && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setShort("Anger", (short)this.angerLevel);
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.angerLevel = par1NBTTagCompound.getShort("Anger");
   }

   protected Entity findPlayerToAttack() {
      return this.angerLevel == 0?null:super.findPlayerToAttack();
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.isEntityInvulnerable()) {
         return false;
      } else {
         Entity var3 = par1DamageSource.getEntity();
         if(var3 instanceof EntityPlayer) {
            List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));

            for(int var5 = 0; var5 < var4.size(); ++var5) {
               Entity var6 = (Entity)var4.get(var5);
               if(var6 instanceof EntityPigZombie) {
                  EntityPigZombie var7 = (EntityPigZombie)var6;
                  var7.becomeAngryAt(var3);
               }
            }

            this.becomeAngryAt(var3);
         }

         return super.attackEntityFrom(par1DamageSource, par2);
      }
   }

   private void becomeAngryAt(Entity par1Entity) {
      this.entityToAttack = par1Entity;
      this.angerLevel = 400 + this.rand.nextInt(400);
      this.randomSoundDelay = this.rand.nextInt(40);
   }

   protected String getLivingSound() {
      return "mob.zombiepig.zpig";
   }

   protected String getHurtSound() {
      return "mob.zombiepig.zpighurt";
   }

   protected String getDeathSound() {
      return "mob.zombiepig.zpigdeath";
   }

   protected void dropFewItems(boolean par1, int par2) {
      int var3 = this.rand.nextInt(2 + par2);

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Item.rottenFlesh.itemID, 1);
      }

      var3 = this.rand.nextInt(2 + par2);

      for(var4 = 0; var4 < var3; ++var4) {
         this.dropItem(Item.goldNugget.itemID, 1);
      }
   }

   public boolean interact(EntityPlayer par1EntityPlayer) {
      return false;
   }

   protected void dropRareDrop(int par1) {
      this.dropItem(Item.ingotGold.itemID, 1);
   }

   protected int getDropItemId() {
      return Item.rottenFlesh.itemID;
   }

   protected void addRandomArmor() {
      this.setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
   }

   public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData) {
      super.onSpawnWithEgg(par1EntityLivingData);
      this.setVillager(false);
      return par1EntityLivingData;
   }
}
