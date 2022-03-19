package net.minecraft.src;

public class EntityClientPlayerMP extends EntityPlayerSP {
   public NetClientHandler sendQueue;
   private double oldPosX;
   private double oldMinY;
   private double oldPosY;
   private double oldPosZ;
   private float oldRotationYaw;
   private float oldRotationPitch;
   private boolean wasOnGround;
   private boolean shouldStopSneaking;
   private boolean wasSneaking;
   private int field_71168_co;
   private boolean hasSetHealth;
   private String field_142022_ce;

   public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) {
      super(par1Minecraft, par2World, par3Session, 0);
      this.sendQueue = par4NetClientHandler;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      return false;
   }

   public void heal(float par1) {}

   public void onUpdate() {
      if(this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) {
         super.onUpdate();
         if(this.isRiding()) {
            this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
            this.sendQueue.addToSendQueue(new Packet27PlayerInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
         } else {
            this.sendMotionUpdates();
         }
      }
   }

   public void sendMotionUpdates() {
      boolean var1 = this.isSprinting();
      if(var1 != this.wasSneaking) {
         if(var1) {
            this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
         } else {
            this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
         }

         this.wasSneaking = var1;
      }

      boolean var2 = this.isSneaking();
      if(var2 != this.shouldStopSneaking) {
         if(var2) {
            this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
         } else {
            this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
         }

         this.shouldStopSneaking = var2;
      }

      double var3 = this.posX - this.oldPosX;
      double var5 = this.boundingBox.minY - this.oldMinY;
      double var7 = this.posZ - this.oldPosZ;
      double var9 = (double)(this.rotationYaw - this.oldRotationYaw);
      double var11 = (double)(this.rotationPitch - this.oldRotationPitch);
      boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
      boolean var14 = var9 != 0.0D || var11 != 0.0D;
      if(this.ridingEntity != null) {
         this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
         var13 = false;
      } else if(var13 && var14) {
         this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
      } else if(var13) {
         this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
      } else if(var14) {
         this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
      } else {
         this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
      }

      ++this.field_71168_co;
      this.wasOnGround = this.onGround;
      if(var13) {
         this.oldPosX = this.posX;
         this.oldMinY = this.boundingBox.minY;
         this.oldPosY = this.posY;
         this.oldPosZ = this.posZ;
         this.field_71168_co = 0;
      }

      if(var14) {
         this.oldRotationYaw = this.rotationYaw;
         this.oldRotationPitch = this.rotationPitch;
      }
   }

   public EntityItem dropOneItem(boolean par1) {
      int var2 = par1?3:4;
      this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
      return null;
   }

   protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}

   public void sendChatMessage(String par1Str) {
      this.sendQueue.addToSendQueue(new Packet3Chat(par1Str));
   }

   public void swingItem() {
      super.swingItem();
      this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
   }

   public void respawnPlayer() {
      this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
   }

   protected void damageEntity(DamageSource par1DamageSource, float par2) {
      if(!this.isEntityInvulnerable()) {
         this.setHealth(this.getHealth() - par2);
      }
   }

   public void closeScreen() {
      this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.openContainer.windowId));
      this.func_92015_f();
   }

   public void func_92015_f() {
      this.inventory.setItemStack((ItemStack)null);
      super.closeScreen();
   }

   public void setPlayerSPHealth(float par1) {
      if(this.hasSetHealth) {
         super.setPlayerSPHealth(par1);
      } else {
         this.setHealth(par1);
         this.hasSetHealth = true;
      }
   }

   public void addStat(StatBase par1StatBase, int par2) {
      if(par1StatBase != null) {
         if(par1StatBase.isIndependent) {
            super.addStat(par1StatBase, par2);
         }
      }
   }

   public void incrementStat(StatBase par1StatBase, int par2) {
      if(par1StatBase != null) {
         if(!par1StatBase.isIndependent) {
            super.addStat(par1StatBase, par2);
         }
      }
   }

   public void sendPlayerAbilities() {
      this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
   }

   protected void func_110318_g() {
      this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 6, (int)(this.getHorseJumpPower() * 100.0F)));
   }

   public void func_110322_i() {
      this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 7));
   }

   public void func_142020_c(String par1Str) {
      this.field_142022_ce = par1Str;
   }

   public String func_142021_k() {
      return this.field_142022_ce;
   }
}
