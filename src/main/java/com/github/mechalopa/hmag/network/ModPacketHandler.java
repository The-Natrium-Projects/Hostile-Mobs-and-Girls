package com.github.mechalopa.hmag.network;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.network.packet.SyncEnchantmentUpgradeMapPacket;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = HMaG.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModPacketHandler
{
	private static final String PROTOCOL_VERSION = "1";

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event)
	{
		final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
		registrar.playToClient(SyncEnchantmentUpgradeMapPacket.TYPE, SyncEnchantmentUpgradeMapPacket.STREAM_CODEC, SyncEnchantmentUpgradeMapPacket::handle);
	}
}