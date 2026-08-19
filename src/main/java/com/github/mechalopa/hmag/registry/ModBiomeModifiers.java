package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.level.modifiers.ModAddSpawnsBiomeModifier;
import com.mojang.serialization.Codec;

import net.neoforged.common.world.BiomeModifier;
import net.neoforged.eventbus.api.IEventBus;
import net.neoforged.eventbus.api.SubscribeEvent;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.registries.ForgeRegistries;
import net.neoforged.registries.RegistryObject;

public class ModBiomeModifiers
{
	private static final DeferredRegister<Codec<? extends BiomeModifier>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, HMaG.MODID);

	public static final RegistryObject<Codec<ModAddSpawnsBiomeModifier>> ADD_SPAWNS = REGISTRY.register("add_spawns", ModAddSpawnsBiomeModifier.CODEC);

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}
}