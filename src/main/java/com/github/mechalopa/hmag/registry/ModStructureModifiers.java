package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.level.modifiers.ModAddSpawnsStructureModifier;
import com.mojang.serialization.MapCodec;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModStructureModifiers
{
	private static final DeferredRegister<MapCodec<? extends StructureModifier>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, HMaG.MODID);

	public static final DeferredHolder<MapCodec<? extends StructureModifier>, MapCodec<ModAddSpawnsStructureModifier>> ADD_SPAWNS = REGISTRY.register("add_spawns", ModAddSpawnsStructureModifier.CODEC);

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}
}