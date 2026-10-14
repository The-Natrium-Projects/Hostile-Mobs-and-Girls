package com.github.mechalopa.hmag.world.item;

import java.util.List;

import com.github.mechalopa.hmag.util.ModTags;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PurificationClothItem extends Item
{
	public PurificationClothItem(Item.Properties builder)
	{
		super(builder);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
	{
		if (!level.isClientSide())
		{
			for (MobEffect effect : getRemovableEffectList(livingEntity))
			{
				livingEntity.removeEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect));
			}

			level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.NEUTRAL, 0.25F, level.getRandom().nextFloat() * 0.1F + 0.9F);
		}

		if (livingEntity instanceof ServerPlayer serverplayerentity)
		{
			CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
			serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
		}

		if (livingEntity instanceof Player && !((Player)livingEntity).getAbilities().instabuild)
		{
			stack.shrink(1);
		}

		return stack;
	}

	private static List<MobEffect> getRemovableEffectList(LivingEntity livingEntity)
	{
		return livingEntity.getActiveEffects().stream().map(MobEffectInstance::getEffect).filter(p -> {
			return p != null && !p.value().isBeneficial() && !ModTags.checkTagContains(p.value(), ModTags.MobEffectTags.UNREMOVABLE_EFFECTS);
		}).map(Holder::value).toList();
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack)
	{
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack stack = player.getItemInHand(hand);
		List<MobEffect> list = getRemovableEffectList(player);

		if (!list.isEmpty())
		{
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(stack);
		}
		else
		{
			return InteractionResultHolder.fail(stack);
		}
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity user)
	{
		return 16;
	}


}