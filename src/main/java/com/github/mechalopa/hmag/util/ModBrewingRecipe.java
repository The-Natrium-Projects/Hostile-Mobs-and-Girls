package com.github.mechalopa.hmag.util;

import javax.annotation.Nonnull;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;

public class ModBrewingRecipe extends BrewingRecipe
{
	@Nonnull
	private final Ingredient input;
	@Nonnull
	private final Ingredient ingredient;
	@Nonnull
	private final ItemStack output;

	public ModBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output)
	{
		super(input, ingredient, output);
		this.input = input;
		this.ingredient = ingredient;
		this.output = output;
	}

	@Override
	public boolean isInput(@Nonnull ItemStack stack)
	{
		if (stack != null)
		{
			ItemStack[] stacks = this.input.getItems();

			if (stacks.length > 0)
			{
				for (ItemStack stack1 : stacks)
				{
					if (ItemStack.isSameItem(stack1, stack)
                        && stack1.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                        .equals(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)))
					{
						return true;
					}
				}
			}
			else
			{
				return stack.isEmpty();
			}
		}

		return false;
	}
}