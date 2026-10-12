package com.github.mechalopa.hmag.network;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.util.ModUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = HMaG.MODID)
public class ModPacketHandler
{
	private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    private static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar =
            event.registrar(PROTOCOL_VERSION);

        // Register each payload here, for example:
        // registrar.playToServer(
        //         MyPayload.TYPE,
        //         MyPayload.STREAM_CODEC,
        //         MyPayload::handle
        // );
    }
}