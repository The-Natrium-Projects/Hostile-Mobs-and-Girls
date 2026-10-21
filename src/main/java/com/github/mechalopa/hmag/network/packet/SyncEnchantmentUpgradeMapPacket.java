package com.github.mechalopa.hmag.network.packet;

import java.util.HashMap;

import com.github.mechalopa.hmag.util.ModUtils;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeManager;
import com.github.mechalopa.hmag.world.item.crafting.EnchantmentUpgradeManager.EnchantmentUpgradeProp;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncEnchantmentUpgradeMapPacket implements CustomPacketPayload
{
	public static final Type<SyncEnchantmentUpgradeMapPacket> TYPE = new Type<>(ModUtils.createHMaGRL("sync_enchantment_upgrade_map"));
	public static final StreamCodec<FriendlyByteBuf, SyncEnchantmentUpgradeMapPacket> STREAM_CODEC = StreamCodec.of((buf, msg) -> encode(msg, buf), SyncEnchantmentUpgradeMapPacket::decode);

	private final HashMap<EnchantmentUpgradeProp, Integer> propMap;

	public SyncEnchantmentUpgradeMapPacket(HashMap<EnchantmentUpgradeProp, Integer> map)
	{
		this.propMap = map;
	}

	public static void encode(SyncEnchantmentUpgradeMapPacket msg, FriendlyByteBuf buf)
	{
		HashMap<EnchantmentUpgradeProp, Integer> map = msg.getPropMap();
		EnchantmentUpgradeManager.encodeMap(map, buf);
	}

	public static SyncEnchantmentUpgradeMapPacket decode(FriendlyByteBuf buf)
	{
		HashMap<EnchantmentUpgradeProp, Integer> map = EnchantmentUpgradeManager.decodeMap(buf);
		return new SyncEnchantmentUpgradeMapPacket(map);
	}

	public static void handle(SyncEnchantmentUpgradeMapPacket msg, IPayloadContext context)
	{
		context.enqueueWork(() -> EnchantmentUpgradeManager.INSTANCE.updateFromServer(msg.getPropMap()));
	}

	@Override
	public Type<SyncEnchantmentUpgradeMapPacket> type()
	{
		return TYPE;
	}

	private HashMap<EnchantmentUpgradeProp, Integer> getPropMap()
	{
		return this.propMap;
	}
}