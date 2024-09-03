package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.lang.module.Configuration;
import java.util.List;

public class ModPlacedFeatures {


    public static final ResourceKey<PlacedFeature> BIG_SULFUR = key("big_sulfur");
    public static final ResourceKey<PlacedFeature> SMALL_SULFUR_UPPER = key("small_sulfur_upper");
    public static final ResourceKey<PlacedFeature> SMALL_SULFUR_LOWER = key("small_sulfur_lower");

    public static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ChemistryMod.modLoc(name));
    }

    public static void generate(BootstapContext<PlacedFeature> generator) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeaturers = generator.lookup(Registries.CONFIGURED_FEATURE);
        generator.register(BIG_SULFUR, new PlacedFeature(configuredFeaturers.getOrThrow(ModConfiguredFeatures.SULFUR_ORE_LARGE), rareOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-40), VerticalAnchor.aboveBottom(80)))));
        generator.register(SMALL_SULFUR_UPPER, new PlacedFeature(configuredFeaturers.getOrThrow(ModConfiguredFeatures.SULFUR_ORE_SMALL), commonOrePlacement(40, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)))));
        generator.register(SMALL_SULFUR_LOWER, new PlacedFeature(configuredFeaturers.getOrThrow(ModConfiguredFeatures.SULFUR_ORE_SMALL), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    }


    public static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
