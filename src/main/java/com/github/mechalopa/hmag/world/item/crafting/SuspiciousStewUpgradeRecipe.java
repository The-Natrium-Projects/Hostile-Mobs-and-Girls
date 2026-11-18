package com.github.mechalopa.hmag.world.item.crafting;

import java.util.ArrayList;
import java.util.List;

import com.github.mechalopa.hmag.registry.ModDataComponentTypes;
import com.github.mechalopa.hmag.registry.ModRecipes;
import com.github.mechalopa.hmag.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class SuspiciousStewUpgradeRecipe extends CustomRecipe
{
	public SuspiciousStewUpgradeRecipe(CraftingBookCategory category)
	{
		super(category);
	}

	@Override
	public boolean matches(CraftingInput inv, Level level)
	{
		boolean flag = false;
		boolean flag1 = false;

		for (int i = 0; i < inv.size(); ++i)
		{
			ItemStack stack = inv.getItem(i);

			if (!stack.isEmpty())
			{
				if (stack.is(Items.SUSPICIOUS_STEW) && (stack.has(DataComponents.SUSPICIOUS_STEW_EFFECTS) && !stack.getOrDefault(ModDataComponentTypes.SUSPICIOUS_STEW_UPGRADED, false)) && !flag)
				{
					flag = true;
				}
				else
				{
					if (!stack.is(ModTags.ItemTags.SUSPICIOUS_STEW_UPGRADE_ITEMS) || flag1)
					{
						return false;
					}

					flag1 = true;
				}
			}
		}

		return flag && flag1;
	}

	@Override
	public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registries)
	{
		ItemStack stack = ItemStack.EMPTY;

		for (int i = 0; i < inv.size(); ++i)
		{
			ItemStack stack1 = inv.getItem(i);

			if (!stack1.isEmpty() && stack1.is(Items.SUSPICIOUS_STEW))
			{
				stack = stack1.copy();
				break;
			}
		}

		if (!stack.isEmpty())
		{
			stack.set(ModDataComponentTypes.SUSPICIOUS_STEW_UPGRADED, true);
			SuspiciousStewEffects effects = stack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS);

			if (effects != null)
			{
				List<SuspiciousStewEffects.Entry> list = new ArrayList<>();

				for (SuspiciousStewEffects.Entry entry : effects.effects())
				{
					final int j = entry.duration();

					if (j > 0)
					{
						list.add(new SuspiciousStewEffects.Entry(entry.effect(), entry.effect().value().isInstantenous() ? j + 5 : j * 5));
					}
				}

				if (!list.isEmpty())
				{
					stack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects(list));
				}
			}
		}

		return stack;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return ModRecipes.SUSPICIOUS_STEW_UPGRADE.get();
	}
}