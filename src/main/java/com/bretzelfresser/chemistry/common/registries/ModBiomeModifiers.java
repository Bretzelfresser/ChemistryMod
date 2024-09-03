package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> SULFUR_OVERWORLD = key("sulfur_overworld");

    public static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ChemistryMod.modLoc(name));
    }

    public static void generate(BootstapContext<BiomeModifier> generator) {
        HolderGetter<PlacedFeature> placedFeatures = generator.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = generator.lookup(Registries.BIOME);

        generator.register(SULFUR_OVERWORLD, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(ModPlacedFeatures.BIG_SULFUR),
                        placedFeatures.getOrThrow(ModPlacedFeatures.SMALL_SULFUR_LOWER),
                        placedFeatures.getOrThrow(ModPlacedFeatures.SMALL_SULFUR_UPPER)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }
}
