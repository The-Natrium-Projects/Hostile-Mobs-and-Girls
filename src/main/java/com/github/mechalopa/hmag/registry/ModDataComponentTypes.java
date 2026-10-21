package com.github.mechalopa.hmag.registry;

import com.github.mechalopa.hmag.HMaG;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponentTypes {

    public static final DeferredRegister<DataComponentType<?>> REGISTRY =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, HMaG.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SUSPICIOUS_STEW_UPGRADED
        = REGISTRY.register("suspicious_stew_upgraded", key -> DataComponentType.<Boolean>builder()
        .persistent(Codec.BOOL)
        .networkSynchronized(ByteBufCodecs.BOOL)
        .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ITEM_LEVEL
        = REGISTRY.register("item_level", key -> DataComponentType.<Integer>builder()
        .persistent(ExtraCodecs.intRange(1, 99))
        .networkSynchronized(ByteBufCodecs.INT)
        .build());

}