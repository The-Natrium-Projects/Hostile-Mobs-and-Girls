package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.level.modifiers.ModAddSpawnsStructureModifier;
import com.mojang.serialization.Codec;

import net.neoforged.common.world.StructureModifier;
import net.neoforged.eventbus.api.IEventBus;
import net.neoforged.eventbus.api.SubscribeEvent;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.registries.ForgeRegistries;
import net.neoforged.registries.RegistryObject;

public class ModStructureModifiers
{
	private static final DeferredRegister<Codec<? extends StructureModifier>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, HMaG.MODID);

	public static final RegistryObject<Codec<ModAddSpawnsStructureModifier>> ADD_SPAWNS = REGISTRY.register("add_spawns", ModAddSpawnsStructureModifier.CODEC);

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}
}