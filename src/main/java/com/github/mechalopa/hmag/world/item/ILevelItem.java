package com.github.mechalopa.hmag.world.item;

import com.github.mechalopa.hmag.HMaG;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
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
			CompoundTag compoundnbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
			return compoundnbt != null && compoundnbt.contains(LEVEL_KEY) ? (int)compoundnbt.getByte(LEVEL_KEY) : 0;
		}
	}

	public static void removeItemLevelTag(ItemStack stack)
	{
		CompoundTag compoundnbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

		if (compoundnbt != null && compoundnbt.contains(LEVEL_KEY))
		{
			compoundnbt.remove(LEVEL_KEY);
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundnbt));
		}

		public static void setItemLevel(ItemStack stack, int level)
		{
			CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
			tag.putByte(LEVEL_KEY, (byte)level);
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		}
	}
}