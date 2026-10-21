package com.github.mechalopa.hmag.registry;

import java.util.List;

import com.github.mechalopa.hmag.util.ModUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments
{
	public static final ResourceKey<Enchantment> HEALTH_BOOST = create("health_boost");
	public static final ResourceKey<Enchantment> WATER_ASPECT = create("water_aspect");
	public static final ResourceKey<Enchantment> ANTI_AIR = create("anti_air");
	public static final List<ResourceKey<Enchantment>> ALL = List.of(HEALTH_BOOST, WATER_ASPECT, ANTI_AIR);

	private static ResourceKey<Enchantment> create(String name)
	{
		return ResourceKey.create(Registries.ENCHANTMENT, ModUtils.createHMaGRL(name));
	}
}