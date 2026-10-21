package com.github.mechalopa.hmag;

import com.github.mechalopa.hmag.registry.*;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(HMaG.MODID)
@EventBusSubscriber(modid = HMaG.MODID)
public class HMaG
{
	public static final String MODID = "hmag";

	public static final Logger LOGGER = LogManager.getLogger();

	@SuppressWarnings("removal")
	public HMaG()
	{
		ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ModConfigs.SERVER_CONFIG);

		IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();

		modEventBus.addListener(ModConfigs::loadConfig);

		ModBlocks.REGISTRY.register(modEventBus);
		ModCreativeModeTabs.REGISTRY.register(modEventBus);
		ModEffects.REGISTRY.register(modEventBus);
		ModEntityTypes.REGISTRY.register(modEventBus);
		ModItems.REGISTRY.register(modEventBus);
		ModLootModifiers.REGISTRY.register(modEventBus);
		ModParticleTypes.REGISTRY.register(modEventBus);
		ModPotions.REGISTRY.register(modEventBus);
		ModRecipes.REGISTRY.register(modEventBus);
		ModSoundEvents.REGISTRY.register(modEventBus);
		ModBiomeModifiers.REGISTRY.register(modEventBus);
		ModStructureModifiers.REGISTRY.register(modEventBus);
		ModDataComponentTypes.REGISTRY.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
		NeoForge.EVENT_BUS.register(new ModEvents());
	}
}