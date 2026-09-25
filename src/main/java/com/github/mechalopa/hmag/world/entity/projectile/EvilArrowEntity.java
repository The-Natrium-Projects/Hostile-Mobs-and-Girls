package com.github.mechalopa.hmag.world.entity.projectile;

import com.github.mechalopa.hmag.registry.ModEntityTypes;
import com.github.mechalopa.hmag.registry.ModItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EvilArrowEntity extends AbstractArrow
{
	public EvilArrowEntity(EntityType<? extends EvilArrowEntity> type, Level level)
	{
		super(type, level);
	}

	public EvilArrowEntity(Level level, LivingEntity thrower, @Nullable ItemStack firedFromWeapon)
	{
		super(ModEntityTypes.EVIL_ARROW.get(), thrower, level, ModItems.EVIL_ARROW.get().getDefaultInstance(), firedFromWeapon);
	}

	public EvilArrowEntity(Level level, double x, double y, double z, @Nullable ItemStack firedFromWeapon)
	{
		super(ModEntityTypes.EVIL_ARROW.get(), x, y, z, level, ModItems.EVIL_ARROW.get().getDefaultInstance(), firedFromWeapon);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ModItems.EVIL_ARROW.get().getDefaultInstance();
	}
}