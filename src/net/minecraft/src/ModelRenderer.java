package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class ModelRenderer {
   public float textureWidth;
   public float textureHeight;
   private int textureOffsetX;
   private int textureOffsetY;
   public float rotationPointX;
   public float rotationPointY;
   public float rotationPointZ;
   public float rotateAngleX;
   public float rotateAngleY;
   public float rotateAngleZ;
   private boolean compiled;
   private int displayList;
   public boolean mirror;
   public boolean showModel;
   public boolean isHidden;
   public List cubeList;
   public List childModels;
   public final String boxName;
   private ModelBase baseModel;
   public float offsetX;
   public float offsetY;
   public float offsetZ;

   public ModelRenderer(ModelBase par1ModelBase, String par2Str) {
      this.textureWidth = 64.0F;
      this.textureHeight = 32.0F;
      this.showModel = true;
      this.cubeList = new ArrayList();
      this.baseModel = par1ModelBase;
      par1ModelBase.boxList.add(this);
      this.boxName = par2Str;
      this.setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
   }

   public ModelRenderer(ModelBase par1ModelBase) {
      this(par1ModelBase, (String)null);
   }

   public ModelRenderer(ModelBase par1ModelBase, int par2, int par3) {
      this(par1ModelBase);
      this.setTextureOffset(par2, par3);
   }

   public void addChild(ModelRenderer par1ModelRenderer) {
      if(this.childModels == null) {
         this.childModels = new ArrayList();
      }

      this.childModels.add(par1ModelRenderer);
   }

   public ModelRenderer setTextureOffset(int par1, int par2) {
      this.textureOffsetX = par1;
      this.textureOffsetY = par2;
      return this;
   }

   public ModelRenderer addBox(String par1Str, float par2, float par3, float par4, int par5, int par6, int par7) {
      par1Str = this.boxName + "." + par1Str;
      TextureOffset var8 = this.baseModel.getTextureOffset(par1Str);
      this.setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
      this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par2, par3, par4, par5, par6, par7, 0.0F)).func_78244_a(par1Str));
      return this;
   }

   public ModelRenderer addBox(float par1, float par2, float par3, int par4, int par5, int par6) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, 0.0F));
      return this;
   }

   public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
   }

   public void setRotationPoint(float par1, float par2, float par3) {
      this.rotationPointX = par1;
      this.rotationPointY = par2;
      this.rotationPointZ = par3;
   }

   public void render(float par1) {
      if(!this.isHidden) {
         if(this.showModel) {
            if(!this.compiled) {
               this.compileDisplayList(par1);
            }

            GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
            int var2;
            if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
               if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                  GL11.glCallList(this.displayList);
                  if(this.childModels != null) {
                     for(var2 = 0; var2 < this.childModels.size(); ++var2) {
                        ((ModelRenderer)this.childModels.get(var2)).render(par1);
                     }
                  }
               } else {
                  GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
                  GL11.glCallList(this.displayList);
                  if(this.childModels != null) {
                     for(var2 = 0; var2 < this.childModels.size(); ++var2) {
                        ((ModelRenderer)this.childModels.get(var2)).render(par1);
                     }
                  }

                  GL11.glTranslatef(-this.rotationPointX * par1, -this.rotationPointY * par1, -this.rotationPointZ * par1);
               }
            } else {
               GL11.glPushMatrix();
               GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
               if(this.rotateAngleZ != 0.0F) {
                  GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
               }

               if(this.rotateAngleY != 0.0F) {
                  GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
               }

               if(this.rotateAngleX != 0.0F) {
                  GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
               }

               GL11.glCallList(this.displayList);
               if(this.childModels != null) {
                  for(var2 = 0; var2 < this.childModels.size(); ++var2) {
                     ((ModelRenderer)this.childModels.get(var2)).render(par1);
                  }
               }

               GL11.glPopMatrix();
            }

            GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
         }
      }
   }

   public void renderWithRotation(float par1) {
      if(!this.isHidden) {
         if(this.showModel) {
            if(!this.compiled) {
               this.compileDisplayList(par1);
            }

            GL11.glPushMatrix();
            GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
            if(this.rotateAngleY != 0.0F) {
               GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if(this.rotateAngleX != 0.0F) {
               GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if(this.rotateAngleZ != 0.0F) {
               GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
            }

            GL11.glCallList(this.displayList);
            GL11.glPopMatrix();
         }
      }
   }

   public void postRender(float par1) {
      if(!this.isHidden) {
         if(this.showModel) {
            if(!this.compiled) {
               this.compileDisplayList(par1);
            }

            if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
               if(this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
                  GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
               }
            } else {
               GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
               if(this.rotateAngleZ != 0.0F) {
                  GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
               }

               if(this.rotateAngleY != 0.0F) {
                  GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
               }

               if(this.rotateAngleX != 0.0F) {
                  GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
               }
            }
         }
      }
   }

   private void compileDisplayList(float par1) {
      this.displayList = GLAllocation.generateDisplayLists(1);
      GL11.glNewList(this.displayList, GL11.GL_COMPILE);
      Tessellator var2 = Tessellator.instance;

      for(int var3 = 0; var3 < this.cubeList.size(); ++var3) {
         ((ModelBox)this.cubeList.get(var3)).render(var2, par1);
      }

      GL11.glEndList();
      this.compiled = true;
   }

   public ModelRenderer setTextureSize(int par1, int par2) {
      this.textureWidth = (float)par1;
      this.textureHeight = (float)par2;
      return this;
   }
}
