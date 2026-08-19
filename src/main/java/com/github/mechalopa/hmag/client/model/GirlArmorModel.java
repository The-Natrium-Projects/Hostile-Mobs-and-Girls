package com.github.mechalopa.hmag.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GirlArmorModel<T extends LivingEntity> extends AbstractGirlModel<T>
{
	public GirlArmorModel(ModelPart modelPart)
	{
		super(modelPart);
	}
}