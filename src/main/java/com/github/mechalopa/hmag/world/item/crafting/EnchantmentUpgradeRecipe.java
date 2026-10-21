package com.github.mechalopa.hmag.world.item.crafting;

import com.github.mechalopa.hmag.registry.ModRecipes;
import com.github.mechalopa.hmag.util.ModTags;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeManager.EnchantmentUpgradeProp;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

public class EnchantmentUpgradeRecipe extends AbstractUpgradeRecipe
{
	@Override
	public boolean matches(SmithingRecipeInput inv, Level level)
	{
		ItemStack stack = inv.getItem(0);
		ItemStack stack1 = inv.getItem(1);
		ItemStack stack2 = inv.getItem(2);

		if (!stack.isEmpty() && stack.getItem() != null && !stack1.isEmpty() && stack1.getItem() != null && !stack1.is(ModTags.ItemTags.ENCHANTMENT_NOT_UPGRADABLES) && !stack2.isEmpty() && stack2.getItem() != null && !EnchantmentUpgradeManager.getPropMap().isEmpty())
		{
			for (EnchantmentUpgradeProp prop : EnchantmentUpgradeManager.getPropMap().keySet())
			{
				if (prop.isValid(level.registryAccess()) && prop.getTemplate().equals(stack.getItem()) && prop.getAddition().equals(stack2.getItem()))
				{
					final Holder<Enchantment> enchantment = prop.getEnchantment(level.registryAccess());

					if (checkEnchantableItem(stack1, enchantment, prop.getMinLevel(), prop.getMaxLevel()))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	private static boolean checkEnchantableItem(ItemStack stack, Holder<Enchantment> enchantment, int min, int max)
	{
		if (!stack.isEmpty() && (stack.is(Items.ENCHANTED_BOOK) || stack.supportsEnchantment(enchantment)))
		{
			ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
			int i = enchantments.getLevel(enchantment);

			if (i == 0 && min == 0 && max >= 0)
			{
				if (enchantments.keySet().stream().allMatch(existing -> Enchantment.areCompatible(existing, enchantment)))
				{
					return true;
				}
			}
			else if (i >= min && i <= max)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack assemble(SmithingRecipeInput inv, HolderLookup.Provider registryAccess)
	{
		for (EnchantmentUpgradeProp prop : EnchantmentUpgradeManager.getPropMap().keySet())
		{
			if (prop.isValid(registryAccess) && prop.getTemplate().equals(inv.getItem(0).getItem()) && prop.getAddition().equals(inv.getItem(2).getItem()))
			{
				final Holder<Enchantment> enchantment = prop.getEnchantment(registryAccess);
				ItemStack stack = inv.getItem(1);

				if (!stack.is(ModTags.ItemTags.ENCHANTMENT_NOT_UPGRADABLES) && checkEnchantableItem(stack, enchantment, prop.getMinLevel(), prop.getMaxLevel()))
				{
					ItemStack stack1 = stack.copy();
					ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
					ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);
					mutable.set(enchantment, enchantments.getLevel(enchantment) + 1);
					EnchantmentHelper.setEnchantments(stack1, mutable.toImmutable());

					if (!enchantments.isEmpty())
					{
						stack1.set(DataComponents.REPAIR_COST, AnvilMenu.calculateIncreasedRepairCost(stack.getOrDefault(DataComponents.REPAIR_COST, 0)));
					}

					return stack1;
				}
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public boolean isTemplateIngredient(ItemStack stack)
	{
		if (stack.getItem() != null && !EnchantmentUpgradeManager.getPropMap().isEmpty())
		{
			for (EnchantmentUpgradeProp prop : EnchantmentUpgradeManager.getPropMap().keySet())
			{
				if (prop.isValid() && prop.getTemplate().equals(stack.getItem()))
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean isBaseIngredient(ItemStack stack)
	{
		return !stack.is(ModTags.ItemTags.ENCHANTMENT_NOT_UPGRADABLES);
	}

	@Override
	public boolean isAdditionIngredient(ItemStack stack)
	{
		if (stack.getItem() != null && !EnchantmentUpgradeManager.getPropMap().isEmpty())
		{
			for (EnchantmentUpgradeProp prop : EnchantmentUpgradeManager.getPropMap().keySet())
			{
				if (prop.isValid() && prop.getAddition().equals(stack.getItem()))
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return ModRecipes.ENCHANTMENT_UPGRADE.get();
	}
}