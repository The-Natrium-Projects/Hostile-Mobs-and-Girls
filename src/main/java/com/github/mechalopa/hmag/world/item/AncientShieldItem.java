package com.github.mechalopa.hmag.world.item;

import java.util.UUID;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.util.ModTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AncientShieldItem extends ModShieldItem
{
	public static final ResourceLocation ANCIENT_SHIELD_KNOCKBACK_RESISTANCE_KEY =
        ResourceLocation.fromNamespaceAndPath(HMaG.MODID, "ancient_shield_knockback_resistance");
	public static final AttributeModifier ANCIENT_SHIELD_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(ANCIENT_SHIELD_KNOCKBACK_RESISTANCE_KEY, 0.05F, AttributeModifier.Operation.ADD_VALUE);

	public AncientShieldItem(Item.Properties builder)
	{
		super(builder);
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack, ItemStack stack1)
	{
		return stack1.is(ModTags.ItemTags.ANCIENT_SHIELD_REPAIR_ITEMS);
	}
}