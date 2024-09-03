package com.bretzelfresser.chemistry.common.datagen.server;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.registries.ModBiomeModifiers;
import com.bretzelfresser.chemistry.common.registries.ModConfiguredFeatures;
import com.bretzelfresser.chemistry.common.registries.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {

    protected static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::generate)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::generate)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::generate);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ChemistryMod.MODID, "minecraft"));
    }
}
