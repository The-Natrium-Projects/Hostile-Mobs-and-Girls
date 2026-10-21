package com.github.mechalopa.hmag.world.item.crafting;

import com.github.mechalopa.hmag.registry.ModRecipes;
import com.github.mechalopa.hmag.util.ModTags;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class RemoveCurseRecipe extends AbstractUpgradeRecipe
{
	@Override
	public boolean matches(SmithingRecipeInput inv, Level level)
	{
		if (inv.getItem(0).is(ModTags.ItemTags.CURSE_REMOVE_TEMPLATES))
		{
			ItemStack stack = inv.getItem(1);
			ItemStack stack1 = inv.getItem(2);

			if (!stack.isEmpty() && !stack1.isEmpty() && stack1.getItem() != null && stack1.is(ModTags.ItemTags.CURSE_REMOVE_ITEMS) && !stack.is(ModTags.ItemTags.CURSE_UNREMOVABLES))
			{
				for (Holder<Enchantment> enchantment : EnchantmentHelper.getEnchantmentsForCrafting(stack).keySet())
				{
					if (isRemovableCurse(enchantment))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isRemovableCurse(Holder<Enchantment> enchantment)
	{
		return enchantment.is(EnchantmentTags.CURSE) && !enchantment.is(ModTags.EnchantmentTags.UNREMOVABLE_CURSES);
	}

	@Override
	public ItemStack assemble(SmithingRecipeInput inv, HolderLookup.Provider registryAccess)
	{
		ItemStack stack = inv.getItem(1);
		ItemStack stack1 = stack.copy();
		EnchantmentHelper.updateEnchantments(stack1, mutable -> mutable.removeIf(RemoveCurseRecipe::isRemovableCurse));
		boolean empty = EnchantmentHelper.getEnchantmentsForCrafting(stack1).isEmpty();
		stack1.set(DataComponents.REPAIR_COST, 0);

		if (stack1.is(Items.ENCHANTED_BOOK) && empty)
		{
			stack1 = stack1.transmuteCopy(Items.BOOK);
			stack1.remove(DataComponents.STORED_ENCHANTMENTS);
		}

		if (!empty)
		{
			stack1.set(DataComponents.REPAIR_COST, AnvilMenu.calculateIncreasedRepairCost(stack.getOrDefault(DataComponents.REPAIR_COST, 0)));
		}

		return stack1;
	}

	@Override
	public boolean isTemplateIngredient(ItemStack stack)
	{
		return stack.is(ModTags.ItemTags.CURSE_REMOVE_TEMPLATES);
	}

	@Override
	public boolean isBaseIngredient(ItemStack stack)
	{
		return !stack.is(ModTags.ItemTags.CURSE_UNREMOVABLES);
	}

	@Override
	public boolean isAdditionIngredient(ItemStack stack)
	{
		return stack.is(ModTags.ItemTags.CURSE_REMOVE_ITEMS);
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return ModRecipes.REMOVE_CURSE.get();
	}
}