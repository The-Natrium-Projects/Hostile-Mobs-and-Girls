package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.level.modifiers.ModAddSpawnsBiomeModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.neoforged.common.world.BiomeModifier;
import net.neoforged.eventbus.api.IEventBus;
import net.neoforged.eventbus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifiers
{
	private static final DeferredRegister<MapCodec<? extends BiomeModifier>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, HMaG.MODID);

	public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModAddSpawnsBiomeModifier>> ADD_SPAWNS = REGISTRY.register("add_spawns", ModAddSpawnsBiomeModifier.CODEC);

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}
}