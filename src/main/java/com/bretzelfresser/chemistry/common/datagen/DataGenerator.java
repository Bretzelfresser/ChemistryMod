package com.bretzelfresser.chemistry.common.datagen;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.datagen.client.ModBlockStatesProvider;
import com.bretzelfresser.chemistry.common.datagen.client.ModItemModelsProvider;
import com.bretzelfresser.chemistry.common.datagen.client.ModLanguageProvider;
import com.bretzelfresser.chemistry.common.datagen.server.*;
import com.bretzelfresser.chemistry.common.datagen.server.loot.ModBlockLoot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ChemistryMod.MODID)
public class DataGenerator {


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput output = gen.getPackOutput();

        gen.addProvider(event.includeClient(), new ModBlockStatesProvider(output, helper));
        gen.addProvider(event.includeClient(), new ModItemModelsProvider(output, helper));
        gen.addProvider(event.includeClient(), new ModLanguageProvider(output));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(output));
        gen.addProvider(event.includeServer(), new ModLoot(output));

        ModBlockTagsProvider provider = gen.addProvider(event.includeServer(), new ModBlockTagsProvider(output, event.getLookupProvider(), helper));
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(output, event.getLookupProvider(), provider.contentsGetter(), helper));
        gen.addProvider(event.includeServer(), new ModDatapackProvider(output, event.getLookupProvider()));
    }
}
