package com.github.mechalopa.hmag.world.level.storage.loot.modifiers;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddTableLootModifier extends LootModifier
{
	public static final Supplier<MapCodec<AddTableLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(m -> m.lootTable)).apply(inst, AddTableLootModifier::new)));
	private final ResourceKey<LootTable> lootTable;

	public AddTableLootModifier(LootItemCondition[] conditions, ResourceKey<LootTable> lootTable)
	{
		super(conditions);
		this.lootTable = lootTable;
	}

	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
	{
		LootTable table = context.getResolver().lookupOrThrow(Registries.LOOT_TABLE).get(this.lootTable).map(Holder::value).orElse(LootTable.EMPTY);
		table.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec()
	{
		return CODEC.get();
	}
}