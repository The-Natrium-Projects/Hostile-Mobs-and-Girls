package com.github.mechalopa.hmag.client;

import com.github.mechalopa.hmag.registry.ModDataComponentTypes;
import com.github.mechalopa.hmag.world.item.ModBowItem;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@OnlyIn(Dist.CLIENT)
public class ModClientEvents
{
	private static final Component UPGRADED_SUSPICIOUS_STEW_TOOLTIP = Component.translatable("text.hmag.upgraded_suspicious_stew").withStyle(ChatFormatting.LIGHT_PURPLE);

	@SubscribeEvent
	public void onFOVModifier(ComputeFovModifierEvent event)
	{
		if (event.getPlayer() != null)
		{
			Player player = event.getPlayer();

			if (player.isUsingItem() && player.getUseItem() != null && !player.getUseItem().isEmpty() && player.getUseItem().getItem() instanceof ModBowItem bow)
			{
				float f = event.getFovModifier();
				int i = player.getTicksUsingItem();
				float f1 = (float)i / 20.0F;

				if (f1 > 1.0F)
				{
					f1 = 1.0F;
				}
				else
				{
					f1 = f1 * f1;
				}

				f *= 1.0F - f1 * bow.getBowFOV(player.getUseItem(), player);

				event.setNewFovModifier(f);
			}
		}
	}

	@SubscribeEvent
	public void addItemTooltip(final ItemTooltipEvent event)
	{
		if (!event.getToolTip().isEmpty()
			&& event.getItemStack() != null
			&& event.getItemStack().is(Items.SUSPICIOUS_STEW)
			&& event.getItemStack().getOrDefault(ModDataComponentTypes.SUSPICIOUS_STEW_UPGRADED, false))
		{
			event.getToolTip().add(UPGRADED_SUSPICIOUS_STEW_TOOLTIP);
		}
	}
}