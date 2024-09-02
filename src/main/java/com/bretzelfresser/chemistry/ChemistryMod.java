package com.bretzelfresser.chemistry;

import com.bretzelfresser.chemistry.common.registries.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChemistryMod.MODID)
public class ChemistryMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "chemistry";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChemistryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModMenus.MENU_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZER.register(modEventBus);
        ModRecipes.RECIPE_TYPES.register(modEventBus);
        ModCreativeModeTabs.TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation modLoc(String name){
        return new ResourceLocation(MODID, name);
    }

}
