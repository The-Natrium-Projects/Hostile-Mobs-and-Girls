package com.github.mechalopa.hmag.world.level.storage.loot.conditions;

import com.github.mechalopa.hmag.registry.ModLootItemConditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.fml.ModList;

public class ModLoadedCondition implements LootItemCondition
{
    public static final MapCodec<ModLoadedCondition> CODEC =
        RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("modid")
                .forGetter(condition -> condition.modid)
        ).apply(instance, ModLoadedCondition::new));
	private final String modid;

	private ModLoadedCondition(String modid)
	{
		this.modid = modid;
	}

	@Override
	public LootItemConditionType getType()
	{
		return ModLootItemConditions.MOD_LOADED.get();
	}

	@Override
	public boolean test(LootContext lootContext)
	{
		return ModList.get().isLoaded(modid);
	}

	public static Builder builder(final String modid)
	{
		return new Builder(modid);
	}

	public static class Builder implements LootItemCondition.Builder
	{
		private final String modid;

		public Builder(String modid)
		{
			if (modid == null)
			{
				throw new IllegalArgumentException("Target modid must not be null");
			}

			this.modid = modid;
		}

		@Override
		public LootItemCondition build()
		{
			return new ModLoadedCondition(this.modid);
		}
	}

}