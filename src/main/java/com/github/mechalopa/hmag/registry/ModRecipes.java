package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeRecipe;
import com.github.mechalopa.hmag.world.item.crafting.RemoveCurseRecipe;
import com.github.mechalopa.hmag.world.item.crafting.SimpleUpgradeRecipeSerializer;
import com.github.mechalopa.hmag.world.item.crafting.SuspiciousStewUpgradeRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes
{
	private static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, HMaG.MODID);

	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RemoveCurseRecipe>> REMOVE_CURSE = REGISTRY.register("remove_curse", () -> new SimpleUpgradeRecipeSerializer<>(RemoveCurseRecipe::new));
	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EnchantmentUpgradeRecipe>> ENCHANTMENT_UPGRADE = REGISTRY.register("enchantment_upgrade", () -> new SimpleUpgradeRecipeSerializer<>(EnchantmentUpgradeRecipe::new));
	public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SuspiciousStewUpgradeRecipe>> SUSPICIOUS_STEW_UPGRADE = REGISTRY.register("suspicious_stew_upgrade", () -> new SimpleCraftingRecipeSerializer<>(SuspiciousStewUpgradeRecipe::new));

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}
}