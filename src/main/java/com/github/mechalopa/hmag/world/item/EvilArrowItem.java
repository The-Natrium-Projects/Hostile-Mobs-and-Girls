package com.github.mechalopa.hmag.world.item;

import com.github.mechalopa.hmag.world.entity.projectile.EvilArrowEntity;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import javax.annotation.Nullable;

public class EvilArrowItem extends ArrowItem
{
	public EvilArrowItem(Item.Properties builder)
	{
		super(builder);
		DispenserBlock.registerBehavior(this, new ProjectileDispenseBehavior(this));
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity thrower, @Nullable ItemStack weapon)
	{
		return new EvilArrowEntity(level, thrower, weapon);
	}

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
        AbstractArrow arrow = new EvilArrowEntity(level, position.x(), position.y(), position.z(), null);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}