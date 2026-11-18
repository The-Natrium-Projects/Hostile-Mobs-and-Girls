package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.util.ModUtils;
import com.github.mechalopa.hmag.world.item.ILevelItem;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HMaG.MODID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = REGISTRY.register("tab", () -> CreativeModeTab.builder().title(Component.translatable("item_group." + HMaG.MODID + ".tab")).icon(() -> new ItemStack(ModItems.EVIL_CRYSTAL.get())).displayItems((features, output) -> {
		for (DeferredHolder<Item, ? extends Item> item : ModItems.getItemRegistry().getEntries())
		{
			if (item.get() instanceof ILevelItem)
			{
				ItemStack stack = new ItemStack(item.get());
				stack.set(ModDataComponentTypes.ITEM_LEVEL, ((ILevelItem)item.get()).getMaxLevel());
				output.accept(stack);
			}
			else
			{
				output.accept(item.get());
			}
		}

		for (ResourceKey<Enchantment> key : ModEnchantments.ALL)
		{
			features.holders().lookupOrThrow(Registries.ENCHANTMENT).get(key).ifPresent(enchantment ->
					output.accept(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.value().getMaxLevel()))));
		}

		for (Item potionItem : ModUtils.POTION_ITEMS)
		{
			for (DeferredHolder<Potion, ? extends Potion> potion : ModPotions.getPotionRegistry().getEntries())
			{
				output.accept(ModUtils.getPotionStack(potion, potionItem));
			}
		}
	}).build());
}