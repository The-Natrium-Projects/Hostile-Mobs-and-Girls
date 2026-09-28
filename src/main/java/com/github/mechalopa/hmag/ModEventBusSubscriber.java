package com.github.mechalopa.hmag;

import com.github.mechalopa.hmag.network.ModPacketHandler;
import com.github.mechalopa.hmag.network.packet.SyncEnchantmentUpgradeMapPacket;
import com.github.mechalopa.hmag.registry.ModEntityTypes;
import com.github.mechalopa.hmag.registry.ModItems;
import com.github.mechalopa.hmag.registry.ModPotions;
import com.github.mechalopa.hmag.util.ModBrewingRecipe;
import com.github.mechalopa.hmag.util.ModSpawnRules;
import com.github.mechalopa.hmag.util.ModUtils;
import com.github.mechalopa.hmag.world.entity.AlrauneEntity;
import com.github.mechalopa.hmag.world.entity.BansheeEntity;
import com.github.mechalopa.hmag.world.entity.CatoblepasEntity;
import com.github.mechalopa.hmag.world.entity.CreeperGirlEntity;
import com.github.mechalopa.hmag.world.entity.CrimsonSlaughtererEntity;
import com.github.mechalopa.hmag.world.entity.CursedDollEntity;
import com.github.mechalopa.hmag.world.entity.DodomekiEntity;
import com.github.mechalopa.hmag.world.entity.DoguEntity;
import com.github.mechalopa.hmag.world.entity.DrownedGirlEntity;
import com.github.mechalopa.hmag.world.entity.DullahanEntity;
import com.github.mechalopa.hmag.world.entity.DyssomniaEntity;
import com.github.mechalopa.hmag.world.entity.EnderExecutorEntity;
import com.github.mechalopa.hmag.world.entity.FortressKeeperEntity;
import com.github.mechalopa.hmag.world.entity.GhastlySeekerEntity;
import com.github.mechalopa.hmag.world.entity.GhostEntity;
import com.github.mechalopa.hmag.world.entity.GiantMummyEntity;
import com.github.mechalopa.hmag.world.entity.GlaryadEntity;
import com.github.mechalopa.hmag.world.entity.HarpyEntity;
import com.github.mechalopa.hmag.world.entity.HornetEntity;
import com.github.mechalopa.hmag.world.entity.HuskGirlEntity;
import com.github.mechalopa.hmag.world.entity.ImpEntity;
import com.github.mechalopa.hmag.world.entity.JackFrostEntity;
import com.github.mechalopa.hmag.world.entity.JiangshiEntity;
import com.github.mechalopa.hmag.world.entity.KashaEntity;
import com.github.mechalopa.hmag.world.entity.KoboldEntity;
import com.github.mechalopa.hmag.world.entity.LichEntity;
import com.github.mechalopa.hmag.world.entity.MagicalSlimeEntity;
import com.github.mechalopa.hmag.world.entity.MeltyMonsterEntity;
import com.github.mechalopa.hmag.world.entity.MonolithEntity;
import com.github.mechalopa.hmag.world.entity.NecroticReaperEntity;
import com.github.mechalopa.hmag.world.entity.NightwalkerEntity;
import com.github.mechalopa.hmag.world.entity.OgreEntity;
import com.github.mechalopa.hmag.world.entity.RedcapEntity;
import com.github.mechalopa.hmag.world.entity.SavagefangEntity;
import com.github.mechalopa.hmag.world.entity.ScorpionEntity;
import com.github.mechalopa.hmag.world.entity.SkeletonGirlEntity;
import com.github.mechalopa.hmag.world.entity.SlimeGirlEntity;
import com.github.mechalopa.hmag.world.entity.SnowCanineEntity;
import com.github.mechalopa.hmag.world.entity.SpiderNestEntity;
import com.github.mechalopa.hmag.world.entity.StrayGirlEntity;
import com.github.mechalopa.hmag.world.entity.SwamperEntity;
import com.github.mechalopa.hmag.world.entity.WitherGhostEntity;
import com.github.mechalopa.hmag.world.entity.WitherSkeletonGirlEntity;
import com.github.mechalopa.hmag.world.entity.ZombieGirlEntity;
import com.github.mechalopa.hmag.world.level.storage.loot.conditions.ModLoadedCondition;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = HMaG.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber
{
	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event)
	{
		ModPacketHandler.INSTANCE.registerMessage(0, SyncEnchantmentUpgradeMapPacket.class, SyncEnchantmentUpgradeMapPacket::encode, SyncEnchantmentUpgradeMapPacket::decode, SyncEnchantmentUpgradeMapPacket::handle);

		event.enqueueWork(() -> {
			registerCompostables();
			registerBrewingRecipes();
		});

		Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, ModUtils.createHMaGRL("mod_loaded"), ModLoadedCondition.TYPE);
	}

//	@SubscribeEvent
//	public static void enqueueIMC(final InterModEnqueueEvent event){}
//
//	@SubscribeEvent
//	public static void processIMC(final InterModProcessEvent event){}
//
//	@SubscribeEvent
//	public void onServerStarting(FMLServerStartingEvent event){}

	public static void registerCompostables()
	{
		ComposterBlock.COMPOSTABLES.put(ModItems.LEMON.get(), 0.65F);
		ComposterBlock.COMPOSTABLES.put(ModItems.CUREBERRY.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModItems.RANDOMBERRY.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModItems.EXP_BERRY.get(), 0.3F);
		ComposterBlock.COMPOSTABLES.put(ModItems.HONEYED_APPLE.get(), 0.5F);
		ComposterBlock.COMPOSTABLES.put(ModItems.HONEYED_LEMON.get(), 0.5F);
		ComposterBlock.COMPOSTABLES.put(ModItems.LEMON_PIE.get(), 1.0F);
		ComposterBlock.COMPOSTABLES.put(ModItems.CUBIC_NUCLEUS.get(), 0.65F);
	}

	public static void registerBrewingRecipes()
	{
		if (ModConfigs.cachedServer.KOBOLD_POTION_BREWING_RECIPES)
		{
			registerBrewingRecipe(Potions.AWKWARD, ModItems.KOBOLD_LEATHER.get(), ModPotions.KOBOLD.get());
			registerBrewingRecipe(ModPotions.KOBOLD.get(), Items.GLOWSTONE_DUST, ModPotions.KOBOLD_STRONG.get());
		}

		if (ModConfigs.cachedServer.HEALING_III_POTION_BREWING_RECIPES)
			registerBrewingRecipe(Potions.STRONG_HEALING, ModItems.LICH_CLOTH.get(), ModPotions.HEALING_III.get());

		if (ModConfigs.cachedServer.HARMING_III_POTION_BREWING_RECIPES)
		{
			registerBrewingRecipe(Potions.STRONG_HARMING, ModItems.LICH_CLOTH.get(), ModPotions.HARMING_III.get());
			registerBrewingRecipe(ModPotions.HEALING_III.get(), Items.FERMENTED_SPIDER_EYE, ModPotions.HARMING_III.get());
		}

		if (ModConfigs.cachedServer.REGENERATION_III_POTION_BREWING_RECIPES)
			registerBrewingRecipe(Potions.STRONG_REGENERATION, ModItems.CUBIC_NUCLEUS.get(), ModPotions.REGENERATION_III.get());
		if (ModConfigs.cachedServer.OGRE_POTION_BREWING_RECIPES)
			registerBrewingRecipe(Potions.STRENGTH, ModItems.OGRE_HORN.get(), ModPotions.OGRE.get());
		if (ModConfigs.cachedServer.VORACITY_POTION_BREWING_RECIPES)
			registerBrewingRecipe(Potions.STRONG_STRENGTH, ModItems.CRIMSON_CUTICULA.get(), ModPotions.VORACITY.get());

		if (ModConfigs.cachedServer.ENDER_RAGE_POTION_BREWING_RECIPES)
		{
			registerBrewingRecipe(Potions.AWKWARD, ModItems.ENDER_PLASM.get(), ModPotions.ENDER_RAGE.get());
			registerBrewingRecipe(ModPotions.ENDER_RAGE.get(), Items.REDSTONE, ModPotions.ENDER_RAGE_LONG.get());
			registerBrewingRecipe(ModPotions.ENDER_RAGE.get(), Items.GLOWSTONE_DUST, ModPotions.ENDER_RAGE_STRONG.get());
		}

		if (ModConfigs.cachedServer.COMBUSTION_POTION_BREWING_RECIPES)
		{
			registerBrewingRecipe(Potions.AWKWARD, ModItems.BURNING_CORE.get(), ModPotions.COMBUSTION.get());
			registerBrewingRecipe(ModPotions.COMBUSTION.get(), Items.REDSTONE, ModPotions.COMBUSTION_LONG.get());
			registerBrewingRecipe(ModPotions.COMBUSTION.get(), Items.FERMENTED_SPIDER_EYE, Potions.FIRE_RESISTANCE);
			registerBrewingRecipe(ModPotions.COMBUSTION_LONG.get(), Items.FERMENTED_SPIDER_EYE, Potions.LONG_FIRE_RESISTANCE);
		}

		if (ModConfigs.cachedServer.SLOWNESS_POTION_BREWING_RECIPES)
			registerBrewingRecipe(Potions.AWKWARD, ModItems.NECROFIBER.get(), Potions.SLOWNESS);
		if (ModConfigs.cachedServer.INVISIBILITY_POTION_BREWING_RECIPES)
			registerBrewingRecipe(Potions.AWKWARD, ModItems.SOUL_APPLE.get(), Potions.INVISIBILITY);
		if (ModConfigs.cachedServer.BLASTING_BOTTLE_BREWING_RECIPE)
			BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Ingredient.of(ModItems.FIRE_BOTTLE.get()), Ingredient.of(ModItems.BURNING_CORE.get()), new ItemStack(ModItems.BLASTING_BOTTLE.get(), 1)));
		if (ModConfigs.cachedServer.LIGHTNING_BOTTLE_BREWING_RECIPE)
			BrewingRecipeRegistry.addRecipe(new ModBrewingRecipe(Ingredient.of(ModItems.FIRE_BOTTLE.get()), Ingredient.of(ModItems.LIGHTNING_PARTICLE.get()), new ItemStack(ModItems.LIGHTNING_BOTTLE.get(), 1)));
	}

	@SubscribeEvent
	public static void registerSpawnPlacements(final RegisterSpawnPlacementsEvent event)
	{
		event.register(ModEntityTypes.ZOMBIE_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.HUSK_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkMonsterSpawnCanSeeSkyRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.DROWNED_GIRL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DrownedGirlEntity::checkDrownedGirlSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SKELETON_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.WITHER_SKELETON_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.STRAY_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StrayGirlEntity::checkStrayGirlSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.CREEPER_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.GHOST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.WITHER_GHOST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.ENDER_EXECUTOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.KOBOLD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KoboldEntity::checkKoboldSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.LICH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LichEntity::checkLichSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.OGRE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OgreEntity::checkOgreSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SPIDER_NEST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpiderNestEntity::checkSpiderNestSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.MELTY_MONSTER.get(), SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MeltyMonsterEntity::checkMeltyMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.CURSED_DOLL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.JACK_FROST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.HORNET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.DULLAHAN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.BANSHEE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.ALRAUNE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.CATOBLEPAS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SCORPION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.KASHA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.DOGU.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkMonsterSpawnCanNotSeeSkyRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.GHASTLY_SEEKER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GhastlySeekerEntity::checkGhastlySeekerSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.REDCAP.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SLIME_GIRL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.MAGICAL_SLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkMobSpawnInLightRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.MONOLITH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MonolithEntity::checkMonolithSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.CRIMSON_SLAUGHTERER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrimsonSlaughtererEntity::checkCrimsonSlaughtererSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.DYSSOMNIA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DyssomniaEntity::checkDyssomniaSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SNOW_CANINE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.HARPY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SAVAGEFANG.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SavagefangEntity::checkSavagefangSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.FORTRESS_KEEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.NECROTIC_REAPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NecroticReaperEntity::checkNecroticReaperSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.DODOMEKI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.IMP.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.GLARYAD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlaryadEntity::checkGlaryadSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.JIANGSHI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.GIANT_MUMMY.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GiantMummyEntity::checkGiantMummySpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.NIGHTWALKER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
		event.register(ModEntityTypes.SWAMPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawnRules::checkSurfaceMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	@SubscribeEvent
	public static void createEntityAttributes(final EntityAttributeCreationEvent event)
	{
		event.put(ModEntityTypes.ZOMBIE_GIRL.get(), ZombieGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.HUSK_GIRL.get(), HuskGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.DROWNED_GIRL.get(), DrownedGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.SKELETON_GIRL.get(), SkeletonGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.WITHER_SKELETON_GIRL.get(), WitherSkeletonGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.STRAY_GIRL.get(), StrayGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.CREEPER_GIRL.get(), CreeperGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.GHOST.get(), GhostEntity.createAttributes().build());
		event.put(ModEntityTypes.WITHER_GHOST.get(), WitherGhostEntity.createAttributes().build());
		event.put(ModEntityTypes.ENDER_EXECUTOR.get(), EnderExecutorEntity.createAttributes().build());
		event.put(ModEntityTypes.KOBOLD.get(), KoboldEntity.createAttributes().build());
		event.put(ModEntityTypes.LICH.get(), LichEntity.createAttributes().build());
		event.put(ModEntityTypes.OGRE.get(), OgreEntity.createAttributes().build());
		event.put(ModEntityTypes.SPIDER_NEST.get(), SpiderNestEntity.createAttributes().build());
		event.put(ModEntityTypes.MELTY_MONSTER.get(), MeltyMonsterEntity.createAttributes().build());
		event.put(ModEntityTypes.CURSED_DOLL.get(), CursedDollEntity.createAttributes().build());
		event.put(ModEntityTypes.JACK_FROST.get(), JackFrostEntity.createAttributes().build());
		event.put(ModEntityTypes.HORNET.get(), HornetEntity.createAttributes().build());
		event.put(ModEntityTypes.DULLAHAN.get(), DullahanEntity.createAttributes().build());
		event.put(ModEntityTypes.BANSHEE.get(), BansheeEntity.createAttributes().build());
		event.put(ModEntityTypes.ALRAUNE.get(), AlrauneEntity.createAttributes().build());
		event.put(ModEntityTypes.CATOBLEPAS.get(), CatoblepasEntity.createAttributes().build());
		event.put(ModEntityTypes.SCORPION.get(), ScorpionEntity.createAttributes().build());
		event.put(ModEntityTypes.KASHA.get(), KashaEntity.createAttributes().build());
		event.put(ModEntityTypes.DOGU.get(), DoguEntity.createAttributes().build());
		event.put(ModEntityTypes.GHASTLY_SEEKER.get(), GhastlySeekerEntity.createAttributes().build());
		event.put(ModEntityTypes.REDCAP.get(), RedcapEntity.createAttributes().build());
		event.put(ModEntityTypes.SLIME_GIRL.get(), SlimeGirlEntity.createAttributes().build());
		event.put(ModEntityTypes.MAGICAL_SLIME.get(), MagicalSlimeEntity.createAttributes().build());
		event.put(ModEntityTypes.MONOLITH.get(), MonolithEntity.createAttributes().build());
		event.put(ModEntityTypes.CRIMSON_SLAUGHTERER.get(), CrimsonSlaughtererEntity.createAttributes().build());
		event.put(ModEntityTypes.DYSSOMNIA.get(), DyssomniaEntity.createAttributes().build());
		event.put(ModEntityTypes.SNOW_CANINE.get(), SnowCanineEntity.createAttributes().build());
		event.put(ModEntityTypes.HARPY.get(), HarpyEntity.createAttributes().build());
		event.put(ModEntityTypes.SAVAGEFANG.get(), SavagefangEntity.createAttributes().build());
		event.put(ModEntityTypes.FORTRESS_KEEPER.get(), FortressKeeperEntity.createAttributes().build());
		event.put(ModEntityTypes.NECROTIC_REAPER.get(), NecroticReaperEntity.createAttributes().build());
		event.put(ModEntityTypes.DODOMEKI.get(), DodomekiEntity.createAttributes().build());
		event.put(ModEntityTypes.IMP.get(), ImpEntity.createAttributes().build());
		event.put(ModEntityTypes.GLARYAD.get(), GlaryadEntity.createAttributes().build());
		event.put(ModEntityTypes.JIANGSHI.get(), JiangshiEntity.createAttributes().build());
		event.put(ModEntityTypes.GIANT_MUMMY.get(), GiantMummyEntity.createAttributes().build());
		event.put(ModEntityTypes.NIGHTWALKER.get(), NightwalkerEntity.createAttributes().build());
		event.put(ModEntityTypes.SWAMPER.get(), SwamperEntity.createAttributes().build());
	}

	private static void registerBrewingRecipe(RegisterBrewingRecipesEvent event, Potion inputPotion, Item item, Potion outputPotion)
	{
		event.getBuilder().addRecipe(new ModBrewingRecipe(Ingredient.of(ModUtils.getPotionStack(inputPotion)), Ingredient.of(item), ModUtils.getPotionStack(outputPotion)));
		event.getBuilder().addRecipe(new ModBrewingRecipe(Ingredient.of(ModUtils.getPotionStack(inputPotion, Items.SPLASH_POTION)), Ingredient.of(item), ModUtils.getPotionStack(outputPotion, Items.SPLASH_POTION)));
		event.getBuilder().addRecipe(new ModBrewingRecipe(Ingredient.of(ModUtils.getPotionStack(inputPotion, Items.LINGERING_POTION)), Ingredient.of(item), ModUtils.getPotionStack(outputPotion, Items.LINGERING_POTION)));
	}
}