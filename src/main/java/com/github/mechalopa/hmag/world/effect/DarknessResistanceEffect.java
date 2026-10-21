package com.github.mechalopa.hmag.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class DarknessResistanceEffect extends MobEffect
{
	public DarknessResistanceEffect(MobEffectCategory type, int color)
	{
		super(type, color);
	}


	@Override
	public boolean applyEffectTick(LivingEntity livingEntity, int amplifier)
	{
		if (livingEntity.hasEffect(MobEffects.BLINDNESS))
		{
			livingEntity.removeEffect(MobEffects.BLINDNESS);
		}

		if (livingEntity.hasEffect(MobEffects.DARKNESS))
		{
			livingEntity.removeEffect(MobEffects.DARKNESS);
		}
		return true;
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
	{
		return duration > 0;
	}

}