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

		ModBlocks.register(modEventBus);
		ModCreativeModeTabs.register(modEventBus);
		ModEffects.register(modEventBus);
		ModEnchantments.register(modEventBus);
		ModEntityTypes.register(modEventBus);
		ModItems.register(modEventBus);
		ModLootModifiers.register(modEventBus);
		ModParticleTypes.register(modEventBus);
		ModPotions.register(modEventBus);
		ModRecipes.register(modEventBus);
		ModSoundEvents.register(modEventBus);
		ModBiomeModifiers.register(modEventBus);
		ModStructureModifiers.register(modEventBus);
		ModDataComponentTypes.REG.register(modEventBus);

		NeoForge.EVENT_BUS.register(new ModEvents());
	}
}