package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.world.effect.CombustionEffect;
import com.github.mechalopa.hmag.world.effect.DarknessResistanceEffect;
import com.github.mechalopa.hmag.world.effect.EnderRageEffect;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects
{
	private static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, HMaG.MODID);

	public static final DeferredHolder<MobEffect, EnderRageEffect> ENDER_RAGE = REGISTRY.register("ender_rage", () -> new EnderRageEffect(MobEffectCategory.HARMFUL, 0xC55BD3));
	public static final DeferredHolder<MobEffect, CombustionEffect> COMBUSTION = REGISTRY.register("combustion", () -> new CombustionEffect(MobEffectCategory.HARMFUL, 0xE66410));
	public static final DeferredHolder<MobEffect, DarknessResistanceEffect> DARKNESS_RESISTANCE = REGISTRY.register("darkness_resistance", () -> new DarknessResistanceEffect(MobEffectCategory.BENEFICIAL, 0xD5D8E4));

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}
}