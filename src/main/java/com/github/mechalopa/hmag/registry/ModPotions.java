package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions
{
	private static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(BuiltInRegistries.POTION, HMaG.MODID);

	public static final DeferredHolder<Potion, Potion> KOBOLD = REGISTRY.register("hmag.kobold", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 180 * 20, 0), new MobEffectInstance(MobEffects.WEAKNESS, 180 * 20, 0)));
	public static final DeferredHolder<Potion, Potion> KOBOLD_STRONG = REGISTRY.register("hmag.kobold_strong", () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 90 * 20, 1), new MobEffectInstance(MobEffects.WEAKNESS, 90 * 20, 1)));
	public static final DeferredHolder<Potion, Potion> HEALING_III = REGISTRY.register("hmag.healing_3", () -> new Potion(new MobEffectInstance(MobEffects.HEAL, 1, 2)));
	public static final DeferredHolder<Potion, Potion> HARMING_III = REGISTRY.register("hmag.harming_3", () -> new Potion(new MobEffectInstance(MobEffects.HARM, 1, 2)));
	public static final DeferredHolder<Potion, Potion> REGENERATION_III = REGISTRY.register("hmag.regeneration_3", () -> new Potion(new MobEffectInstance(MobEffects.REGENERATION, 20 * 20, 2)));
	public static final DeferredHolder<Potion, Potion> OGRE = REGISTRY.register("hmag.ogre", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120 * 20, 1), new MobEffectInstance(MobEffects.DIG_SPEED, 120 * 20, 0), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120 * 20, 1)));
	public static final DeferredHolder<Potion, Potion> VORACITY = REGISTRY.register("hmag.voracity", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 30 * 20, 2), new MobEffectInstance(MobEffects.HUNGER, 120 * 20, 2)));
	public static final DeferredHolder<Potion, Potion> ENDER_RAGE = REGISTRY.register("hmag.ender_rage", () -> new Potion(new MobEffectInstance(ModEffects.ENDER_RAGE.get(), 90 * 20, 0)));
	public static final DeferredHolder<Potion, Potion> ENDER_RAGE_LONG = REGISTRY.register("hmag.ender_rage_long", () -> new Potion(new MobEffectInstance(ModEffects.ENDER_RAGE.get(), 180 * 20, 0)));
	public static final DeferredHolder<Potion, Potion> ENDER_RAGE_STRONG = REGISTRY.register("hmag.ender_rage_strong", () -> new Potion(new MobEffectInstance(ModEffects.ENDER_RAGE.get(), 45 * 20, 2)));
	public static final DeferredHolder<Potion, Potion> COMBUSTION = REGISTRY.register("hmag.combustion", () -> new Potion(new MobEffectInstance(ModEffects.COMBUSTION.get(), 45 * 20, 0)));
	public static final DeferredHolder<Potion, Potion> COMBUSTION_LONG = REGISTRY.register("hmag.combustion_long", () -> new Potion(new MobEffectInstance(ModEffects.COMBUSTION.get(), 90 * 20, 0)));

	@SubscribeEvent
	public static void register(IEventBus eventBus)
	{
		REGISTRY.register(eventBus);
	}

	public static DeferredRegister<Potion> getPotionRegistry()
	{
		return REGISTRY;
	}
}