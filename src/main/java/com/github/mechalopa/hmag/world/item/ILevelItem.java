package com.github.mechalopa.hmag.world.item;

import com.github.mechalopa.hmag.HMaG;

import com.github.mechalopa.hmag.registry.ModDataComponentTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ILevelItem
{
	public static final String LEVEL_KEY = HMaG.MODID + ".level";

	public default int getMinLevel()
	{
		return 0;
	}

	public int getMaxLevel();

	public static int getItemLevel(ItemStack stack)
	{
		if (stack == null || stack.isEmpty())
		{
			return 0;
		}
		else
		{
			return stack.getOrDefault(ModDataComponentTypes.ITEM_LEVEL, 0);
		}
	}

	public static void removeItemLevelTag(ItemStack stack)
	{
		stack.remove(ModDataComponentTypes.ITEM_LEVEL);
	}
}