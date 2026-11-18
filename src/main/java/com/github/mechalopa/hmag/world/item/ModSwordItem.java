package com.github.mechalopa.hmag.world.item;

import javax.annotation.Nullable;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.SimpleTier;

public class ModSwordItem extends SwordItem
{
	private final Tier baseTier;

	public ModSwordItem(Tier tier, Item.Properties builder)
	{
		this(tier, 3.0F, -2.4F, builder);
	}

	public ModSwordItem(Tier tier, float attackDamage, float attackSpeed, Item.Properties builder)
	{
		this(tier, attackDamage, attackSpeed, tier.getUses(), tier.getEnchantmentValue(), builder);
	}

	public ModSwordItem(Tier tier, float attackDamage, float attackSpeed, int maxDamage, int enchantability, Item.Properties builder)
	{
		super(createTier(tier, maxDamage, enchantability), builder.attributes(createAttributes(tier, attackDamage, attackSpeed)));
		this.baseTier = tier;
	}

	private static Tier createTier(Tier tier, int maxDamage, int enchantability)
	{
		if (tier.getUses() == maxDamage && tier.getEnchantmentValue() == enchantability)
		{
			return tier;
		}

		return new SimpleTier(tier.getIncorrectBlocksForDrops(), maxDamage, tier.getSpeed(), tier.getAttackDamageBonus(), enchantability, tier::getRepairIngredient);
	}

	public static ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed)
	{
		return ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.build();
	}

	@Override
	public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType)
	{
		return this.baseTier == Tiers.WOOD ? 200 : super.getBurnTime(stack, recipeType);
	}
}
