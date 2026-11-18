package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.level.storage.loot.conditions.ModLoadedCondition;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModLootItemConditions
{
	public static final DeferredRegister<LootItemConditionType> REGISTRY = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, HMaG.MODID);

	public static final DeferredHolder<LootItemConditionType, LootItemConditionType> MOD_LOADED = REGISTRY.register("mod_loaded", () -> new LootItemConditionType(ModLoadedCondition.CODEC));
}
