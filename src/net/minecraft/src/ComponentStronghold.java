package net.minecraft.src;

import java.util.List;
import java.util.Random;

abstract class ComponentStronghold extends StructureComponent {
   protected EnumDoor field_143013_d;

   public ComponentStronghold() {
      this.field_143013_d = EnumDoor.OPENING;
   }

   protected ComponentStronghold(int par1) {
      super(par1);
      this.field_143013_d = EnumDoor.OPENING;
   }

   protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setString("EntryDoor", this.field_143013_d.name());
   }

   protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
      this.field_143013_d = EnumDoor.valueOf(par1NBTTagCompound.getString("EntryDoor"));
   }

   protected void placeDoor(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, EnumDoor par4EnumDoor, int par5, int par6, int par7) {
      switch(EnumDoorHelper.doorEnum[par4EnumDoor.ordinal()]) {
      case 1:
      default:
         this.fillWithBlocks(par1World, par3StructureBoundingBox, par5, par6, par7, par5 + 3 - 1, par6 + 3 - 1, par7, 0, 0, false);
         break;
      case 2:
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.doorWood.blockID, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.doorWood.blockID, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
         break;
      case 3:
         this.placeBlockAtCurrentPosition(par1World, 0, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, 0, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
         break;
      case 4:
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneButton.blockID, this.getMetadataWithOffset(Block.stoneButton.blockID, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
         this.placeBlockAtCurrentPosition(par1World, Block.stoneButton.blockID, this.getMetadataWithOffset(Block.stoneButton.blockID, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
      }
   }

   protected EnumDoor getRandomDoor(Random par1Random) {
      int var2 = par1Random.nextInt(5);
      switch(var2) {
      case 0:
      case 1:
      default:
         return EnumDoor.OPENING;
      case 2:
         return EnumDoor.WOOD_DOOR;
      case 3:
         return EnumDoor.GRATES;
      case 4:
         return EnumDoor.IRON_DOOR;
      }
   }

   protected StructureComponent getNextComponentNormal(ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5) {
      switch(this.coordBaseMode) {
      case 0:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
      case 1:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType());
      case 2:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
      case 3:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType());
      default:
         return null;
      }
   }

   protected StructureComponent getNextComponentX(ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5) {
      switch(this.coordBaseMode) {
      case 0:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
      case 1:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
      case 2:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
      case 3:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
      default:
         return null;
      }
   }

   protected StructureComponent getNextComponentZ(ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5) {
      switch(this.coordBaseMode) {
      case 0:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
      case 1:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
      case 2:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
      case 3:
         return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
      default:
         return null;
      }
   }

   protected static boolean canStrongholdGoDeeper(StructureBoundingBox par0StructureBoundingBox) {
      return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
   }
}
