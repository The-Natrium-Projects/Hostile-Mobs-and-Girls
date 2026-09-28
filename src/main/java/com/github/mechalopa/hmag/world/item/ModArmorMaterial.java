package com.github.mechalopa.hmag.world.item;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.github.mechalopa.hmag.HMaG;
import com.github.mechalopa.hmag.util.ModTags;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.A;

@SuppressWarnings("deprecation")
public class ModArmorMaterial {
	public static final DeferredRegister<ArmorMaterial> REG = DeferredRegister.create(Registries.ARMOR_MATERIAL, HMaG.MODID);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ANCIENT =
		REG.register("ancient", () -> new ArmorMaterial(Map.of(Type.BOOTS, 3, Type.LEGGINGS, 6, Type.CHESTPLATE, 8, Type.HELMET, 3),
			15,
			SoundEvents.ARMOR_EQUIP_IRON,
			() -> Ingredient.of(ModTags.ItemTags.ANCIENT_ARMOR_REPAIR_ITEMS),
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(HMaG.MODID, "ancient"), "", false)),
			2.5f, 0.05f));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NECROTIC_CHAIN =
		REG.register("necrotic_chainmail", () -> new ArmorMaterial(Map.of(Type.BOOTS, 2, Type.LEGGINGS, 5, Type.CHESTPLATE, 6, Type.HELMET, 2),
			15,
			SoundEvents.ARMOR_EQUIP_CHAIN,
			() -> Ingredient.of(ModTags.ItemTags.NECROTIC_CHAINMAIL_ARMOR_REPAIR_ITEMS),
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(HMaG.MODID, "necrotic_chainmail"), "", false)),
			2f, 0f));
}
