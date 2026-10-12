package com.github.mechalopa.hmag.network;

import com.github.mechalopa.hmag.util.ModUtils;
import net.neoforged.neoforge.network.registration.NetworkRegistry;


public class ModPacketHandler
{
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(ModUtils.createHMaGRL("main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
}