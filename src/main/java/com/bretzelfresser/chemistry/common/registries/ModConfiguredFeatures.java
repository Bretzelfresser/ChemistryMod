package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_ORE_LARGE = key("sulfur_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_ORE_SMALL = key("sulfur_ore_small");


    public static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ChemistryMod.modLoc(name));
    }

    public static void generate(BootstapContext<ConfiguredFeature<?, ?>> generator) {
        List<OreConfiguration.TargetBlockState> sulfurTarget = makeWithDeepslate(ModBlocks.SULFUR_ORE.get(), ModBlocks.DEEPSLATE_SULFUR_ORE.get());
        generator.register(SULFUR_ORE_LARGE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(sulfurTarget, 20, 0.5f)));
        generator.register(SULFUR_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(sulfurTarget, 4, 0.2f)));
    }

    public static List<OreConfiguration.TargetBlockState> makeWithDeepslate(Block stoneOre, Block deepslateOre) {
        return List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), stoneOre.defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre.defaultBlockState()));
    }
}
