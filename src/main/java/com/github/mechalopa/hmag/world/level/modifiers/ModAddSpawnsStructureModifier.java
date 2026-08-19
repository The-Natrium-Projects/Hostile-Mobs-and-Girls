package com.github.mechalopa.hmag.world.level.modifiers;

import java.util.function.Supplier;

import com.github.mechalopa.hmag.util.ModUtils;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.common.world.ModifiableStructureInfo.StructureInfo.Builder;
import net.neoforged.common.world.StructureModifier;

public record ModAddSpawnsStructureModifier(HolderSet<Structure> structures, SpawnerData spawner) implements StructureModifier
{
	public static final Supplier<MapCodec<ModAddSpawnsStructureModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(builder -> builder.group(ModUtils.STRUCTURE_LIST_CODEC.fieldOf("structures").forGetter(ModAddSpawnsStructureModifier::structures), SpawnerData.CODEC.fieldOf("spawner").forGetter(ModAddSpawnsStructureModifier::spawner)).apply(builder, ModAddSpawnsStructureModifier::new)));

	@Override
	public void modify(Holder<Structure> structure, Phase phase, Builder builder)
	{
		if (phase == Phase.ADD && this.structures.contains(structure))
		{
			EntityType<?> type = this.spawner.type;
			builder.getStructureSettings().getOrAddSpawnOverrides(type.getCategory()).addSpawn(this.spawner);
		}
	}

	@Override
	public MapCodec<? extends StructureModifier> codec()
	{
		return CODEC.get();
	}
}