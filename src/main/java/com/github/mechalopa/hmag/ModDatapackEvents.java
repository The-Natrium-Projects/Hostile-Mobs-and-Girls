package com.github.mechalopa.hmag;

import com.github.mechalopa.hmag.network.ModPacketHandler;
import com.github.mechalopa.hmag.network.packet.SyncEnchantmentUpgradeMapPacket;
import com.github.mechalopa.hmag.world.item.RandomberryItem;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeManager;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = HMaG.MODID)
public class ModDatapackEvents
{
	@SubscribeEvent
	public static void addListener(final AddReloadListenerEvent event)
	{
		event.addListener(EnchantmentUpgradeManager.INSTANCE);
	}

	@SubscribeEvent
	public static void onDatapackSync(OnDatapackSyncEvent event)
	{
		if (event.getPlayer() != null)
		{
			ModPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> event.getPlayer()), new SyncEnchantmentUpgradeMapPacket(EnchantmentUpgradeManager.getPropMap()));
		}
		else
		{
			for (ServerPlayer player : event.getPlayerList().getPlayers())
			{
				ModPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncEnchantmentUpgradeMapPacket(EnchantmentUpgradeManager.getPropMap()));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer)
		{
			ModPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new SyncEnchantmentUpgradeMapPacket(EnchantmentUpgradeManager.getPropMap()));
		}
	}

	@SubscribeEvent
	public static void onTagsUpdated(final TagsUpdatedEvent event)
	{
		if (event.shouldUpdateStaticData())
		{
			RandomberryItem.refreshEffectList();
		}
	}
}