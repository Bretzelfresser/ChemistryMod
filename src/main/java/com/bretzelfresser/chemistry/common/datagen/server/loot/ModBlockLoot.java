package com.bretzelfresser.chemistry.common.datagen.server.loot;

import com.bretzelfresser.chemistry.common.registries.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {

    protected List<Block> blocks = new ArrayList<>();

    public ModBlockLoot() {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS);
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }

    @Override
    protected void generate() {
        blocks.clear();
        dropSelf(ModBlocks.NORMAL_REACTION_CHAMBER.get());
        dropSelf(ModBlocks.BIG_REACTION_CHAMBER.get());
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        blocks.add(pBlock);
        super.add(pBlock, pBuilder);
    }
}
