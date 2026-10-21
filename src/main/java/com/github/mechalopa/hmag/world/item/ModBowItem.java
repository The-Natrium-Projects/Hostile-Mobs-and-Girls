package com.github.mechalopa.hmag.world.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class ModBowItem extends BowItem
{
	public ModBowItem(Item.Properties builder)
	{
		super(builder);
	}

	@Override
	protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectiles, float velocity, float inaccuracy, boolean critical, @Nullable LivingEntity target)
	{
		super.shoot(level, shooter, hand, weapon, projectiles, velocity * this.getArrowVelocity(weapon, shooter) / 3.0F, inaccuracy, critical, target);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack projectileStack, boolean critical)
	{
		Projectile projectile = super.createProjectile(level, shooter, weapon, projectileStack, critical);

		if (projectile instanceof AbstractArrow arrow)
		{
			arrow.setBaseDamage(arrow.getBaseDamage() + this.getBowDamage(weapon, shooter));
		}

		return projectile;
	}

	public float getBowDamage(ItemStack stack, LivingEntity livingEntity)
	{
		return 0.0F;
	}

	public float getArrowVelocity(ItemStack stack, LivingEntity livingEntity)
	{
		return 3.0F;
	}

	@OnlyIn(Dist.CLIENT)
	public float getBowFOV(ItemStack stack, Player player)
	{
		return 0.0F;
	}
}