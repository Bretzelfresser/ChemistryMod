package com.bretzelfresser.chemistry.common.datagen.server;

import com.bretzelfresser.chemistry.common.datagen.server.loot.ModBlockLoot;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class ModLoot extends LootTableProvider {
    public ModLoot(PackOutput pOutput) {
        super(pOutput, Set.of(), ImmutableList.of(
                new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }
}
