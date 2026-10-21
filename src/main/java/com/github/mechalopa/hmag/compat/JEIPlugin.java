package com.github.mechalopa.hmag.compat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.util.ModTags;
import com.github.mechalopa.hmag.util.ModUtils;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeManager;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeManager.EnchantmentUpgradeProp;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeRecipe;
import com.github.mechalopa.hmag.world.item.crafting.RemoveCurseRecipe;
import com.github.mechalopa.hmag.world.item.crafting.SuspiciousStewUpgradeRecipe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
	@Override
	public ResourceLocation getPluginUid()
	{
		return ModUtils.createHMaGRL(HMaG.MODID);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		final List<Item> items = ImmutableList.of(
				Items.IRON_SWORD,
				Items.IRON_PICKAXE,
				Items.IRON_AXE,
				Items.IRON_SHOVEL,
				Items.IRON_HOE,
				Items.IRON_CHESTPLATE,
				Items.IRON_HELMET,
				Items.IRON_LEGGINGS,
				Items.IRON_BOOTS,
				Items.ELYTRA,
				Items.SHIELD,
				Items.BOW,
				Items.CROSSBOW,
				Items.TRIDENT,
				Items.FISHING_ROD,
				Items.SHEARS,
				Items.FLINT_AND_STEEL,
				Items.SHULKER_BOX,
				Items.IRON_INGOT);

		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null)
		{
			return;
		}
		HolderLookup.Provider registries = minecraft.level.registryAccess();
		Iterable<RecipeHolder<?>> recipes = minecraft.level.getRecipeManager().getRecipes();
		List<RecipeHolder<SmithingRecipe>> smithingRecipes = new ArrayList<>();
		List<RecipeHolder<CraftingRecipe>> shapelessRecipes = new ArrayList<>();
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;

		for (RecipeHolder<?> recipeHolder : recipes)
		{
			Recipe<?> recipe = recipeHolder.value();
			if (recipe != null)
			{
				if (recipe instanceof RemoveCurseRecipe && !flag)
				{
					flag = true;
					Ingredient ingredient = Ingredient.of(ModTags.ItemTags.CURSE_REMOVE_TEMPLATES);
					Ingredient ingredient1 = Ingredient.of(ModTags.ItemTags.CURSE_REMOVE_ITEMS);

					if (!ingredient.isEmpty() && !ingredient1.isEmpty())
					{
						for (Holder<Enchantment> enchantment : registries.lookupOrThrow(Registries.ENCHANTMENT).listElements().toList())
						{
							if (enchantment != null && RemoveCurseRecipe.isRemovableCurse(enchantment))
							{
								ItemStack stack1 = getEnchantableItemStack(registration, items, enchantment, ModTags.ItemTags.CURSE_UNREMOVABLES);

								if (!stack1.isEmpty())
								{
									List<ItemStack> stacks = Lists.newArrayList();

									for (int i = enchantment.value().getMinLevel(); i <= enchantment.value().getMaxLevel(); ++i)
									{
										if (i > 0)
										{
											ItemStack stack2 = stack1.copy();
											setEnchantmentLevel(stack2, enchantment, i);
											stacks.add(stack2);
										}
									}

									if (!stacks.isEmpty())
									{
										ResourceLocation enchid = enchantment.unwrapKey().orElseThrow().location();
										ResourceLocation id = ModUtils.createHMaGRL("jei." + recipeHolder.id().getPath() + "." + enchid.getNamespace() + "." + enchid.getPath());
										addSmithingRecipe(smithingRecipes, id, ingredient, Ingredient.of(stacks.stream()), ingredient1, stack1);
									}
								}
							}
						}
					}
				}
				else if (recipe instanceof EnchantmentUpgradeRecipe && !flag1)
				{
					flag1 = true;

					if (!EnchantmentUpgradeManager.getPropMap().isEmpty())
					{
						int i = 0;

						for (EnchantmentUpgradeProp prop : EnchantmentUpgradeManager.getPropMap().keySet())
						{
							if (prop.isValid(registries))
							{
								final Holder<Enchantment> enchantment = prop.getEnchantment(registries);
								ItemStack stack = getEnchantableItemStack(registration, items, enchantment, ModTags.ItemTags.ENCHANTMENT_NOT_UPGRADABLES);

								if (!stack.isEmpty())
								{
									final int minLevel = prop.getMinLevel();
									final int maxLevel = prop.getMaxLevel();

									for (int j = minLevel; j <= maxLevel; ++j)
									{
										ResourceLocation id = ModUtils.createHMaGRL("jei." + recipeHolder.id().getPath() + "." + i + "." + j);
										ItemStack stack1 = stack.copy();
										ItemStack stack2 = stack.copy();

										if (j > 0)
										{
											setEnchantmentLevel(stack1, enchantment, j);
										}

										setEnchantmentLevel(stack2, enchantment, j + 1);
										addSmithingRecipe(smithingRecipes, id, Ingredient.of(prop.getTemplate()), Ingredient.of(stack1), Ingredient.of(prop.getAddition()), stack2);
									}

									++i;
								}
							}
						}
					}
				}
				else if (recipe instanceof SuspiciousStewUpgradeRecipe && !flag2)
				{
					flag2 = true;
					Ingredient ingredient = Ingredient.of(ModTags.ItemTags.SUSPICIOUS_STEW_UPGRADE_ITEMS);

					if (!ingredient.isEmpty())
					{
						String group = "jei." + HMaG.MODID + ".suspicious.stew.upgrade";
						NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.SUSPICIOUS_STEW), ingredient);
						ItemStack output = new ItemStack(Items.SUSPICIOUS_STEW, 1);
						CompoundTag compoundtag = new CompoundTag();
						compoundtag.putBoolean(SuspiciousStewUpgradeRecipe.UPGRADED_KEY, true);
						output.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundtag));
						ResourceLocation id = ModUtils.createHMaGRL("jei." + recipeHolder.id().getPath());
						ShapelessRecipe recipe3 = new ShapelessRecipe(group, CraftingBookCategory.MISC, output, inputs);
						shapelessRecipes.add(new RecipeHolder<>(id, recipe3));
					}
				}

				if (flag && flag1 && flag2)
				{
					break;
				}
			}
		}

		registration.addRecipes(RecipeTypes.SMITHING, smithingRecipes);
		registration.addRecipes(RecipeTypes.CRAFTING, shapelessRecipes);
	}

	private static void addSmithingRecipe(List<RecipeHolder<SmithingRecipe>> smithingRecipes, ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition, ItemStack output)
	{
		smithingRecipes.add(new RecipeHolder<>(id, new FixedOutputSmithingTransformRecipe(template, base, addition, output)));
	}

	private static void setEnchantmentLevel(ItemStack stack, Holder<Enchantment> enchantment, int level)
	{
		ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(stack));
		mutable.set(enchantment, level);
		EnchantmentHelper.setEnchantments(stack, mutable.toImmutable());
	}

	private static ItemStack getEnchantableItemStack(IRecipeRegistration registration, List<Item> list, Holder<Enchantment> enchantment, TagKey<Item> blacklist)
	{
		for (Item item : list)
		{
			ItemStack stack = new ItemStack(item);

			if (stack.supportsEnchantment(enchantment) && !stack.is(blacklist))
			{
				return stack;
			}
		}

		Collection<ItemStack> stacks = registration.getIngredientManager().getAllIngredients(VanillaTypes.ITEM_STACK);

		for (ItemStack stack2 : stacks)
		{
			if (!stack2.isEmpty() && stack2.getItem() != Items.ENCHANTED_BOOK && stack2.supportsEnchantment(enchantment) && !stack2.is(blacklist))
			{
				return stack2.copy();
			}
		}

		return ItemStack.EMPTY;
	}

	private static final class FixedOutputSmithingTransformRecipe extends SmithingTransformRecipe
	{
		public FixedOutputSmithingTransformRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack output)
		{
			super(template, base, addition, output);
		}

		@Override
		public ItemStack assemble(SmithingRecipeInput container, HolderLookup.Provider registryAccess)
		{
			return this.getResultItem(registryAccess).copy();
		}
	}
}