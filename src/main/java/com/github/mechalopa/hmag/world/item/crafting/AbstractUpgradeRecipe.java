package com.github.mechalopa.hmag.world.item.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmithingRecipe;

public abstract class AbstractUpgradeRecipe implements SmithingRecipe
{
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryAccess)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isSpecial()
	{
		return true;
	}
}
