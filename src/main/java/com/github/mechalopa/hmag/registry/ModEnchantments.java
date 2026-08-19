package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.util.ModUtils;
import com.github.mechalopa.hmag.world.item.enchantment.AntiAirEnchantment;
import com.github.mechalopa.hmag.world.item.enchantment.HealthBoostEnchantment;
import com.github.mechalopa.hmag.world.item.enchantment.WaterAspectEnchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantments
{
	private static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(Registries.ENCHANTMENT, HMaG.MODID);

	public static final DeferredHolder<Enchantment, Enchantment> HEALTH_BOOST = REGISTRY.register("health_boost", () -> new HealthBoostEnchantment(Enchantment.Rarity.UNCOMMON, ModUtils.ARMOR_SLOTS));
	public static final DeferredHolder<Enchantment, Enchantment> WATER_ASPECT = REGISTRY.register("water_aspect", () -> new WaterAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
	public static final DeferredHolder<Enchantment, Enchantment> ANTI_AIR = REGISTRY.register("anti_air", () -> new AntiAirEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}

	public static DeferredRegister<Enchantment> getEnchantmentRegistry()
	{
		return REGISTRY;
	}
}