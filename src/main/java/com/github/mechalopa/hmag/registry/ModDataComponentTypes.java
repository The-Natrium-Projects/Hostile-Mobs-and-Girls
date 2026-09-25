package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponentTypes {

    public static final DeferredRegister<DataComponentType<?>> REG =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, HMaG.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> SUSPICIOUS_STEW_UPGRADE_KEY
        = REG.register("suspicious_stew_upgrade_key", () -> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());


}
