package net.minecraft.src;

public interface IRecipe {
   boolean matches(InventoryCrafting var1, World var2);

   ItemStack getCraftingResult(InventoryCrafting var1);

   int getRecipeSize();

   ItemStack getRecipeOutput();
}
