package com.github.mechalopa.hmag.world.item.crafting;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleUpgradeRecipeSerializer<T extends AbstractUpgradeRecipe> implements RecipeSerializer<T>
{
	private final MapCodec<T> codec;
	private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

	public SimpleUpgradeRecipeSerializer(Supplier<T> constructor)
	{
		this.codec = MapCodec.unit(constructor);
		this.streamCodec = StreamCodec.unit(constructor.get());
	}

	@Override
	public MapCodec<T> codec()
	{
		return this.codec;
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec()
	{
		return this.streamCodec;
	}
}