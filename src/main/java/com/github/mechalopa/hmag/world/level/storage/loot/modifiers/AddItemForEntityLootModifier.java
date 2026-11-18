package com.github.mechalopa.hmag.world.level.storage.loot.modifiers;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddItemForEntityLootModifier extends LootModifier
{
	public static final Supplier<MapCodec<AddItemForEntityLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).and(inst.group(LootItemFunctions.ROOT_CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(m -> m.functions), BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("addition", Items.BARRIER).forGetter(m -> m.addition))).apply(inst, AddItemForEntityLootModifier::new)));
	private final List<LootItemFunction> functions;
	private final Item addition;

	public AddItemForEntityLootModifier(LootItemCondition[] conditions, List<LootItemFunction> functions, Item addition)
	{
		super(conditions);
		this.functions = functions;
		this.addition = addition;
	}

	@Nonnull
	@Override
	public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
	{
		if (context.getParamOrNull(LootContextParams.THIS_ENTITY) != null && context.getParamOrNull(LootContextParams.BLOCK_STATE) == null && this.addition != null && !this.addition.equals(Items.BARRIER))
		{
			ItemStack stack = this.addition.getDefaultInstance();

			for (LootItemFunction function : this.functions)
			{
				stack = function.apply(stack, context);
			}

			generatedLoot.add(stack);
		}

		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec()
	{
		return CODEC.get();
	}
}