package com.bretzelfresser.chemistry.common.datagen.server;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ChemistryMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        iron(
                ModBlocks.SULFUR_ORE,
                ModBlocks.DEEPSLATE_SULFUR_ORE,
                ModBlocks.NORMAL_REACTION_CHAMBER
        );
        diamond(
                ModBlocks.BIG_REACTION_CHAMBER
        );
        pickaxe(
                ModBlocks.SULFUR_ORE,
                ModBlocks.DEEPSLATE_SULFUR_ORE,
                ModBlocks.BIG_REACTION_CHAMBER,
                ModBlocks.NORMAL_REACTION_CHAMBER
        );
    }

    public void pickaxe(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void shovel(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_SHOVEL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void axe(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_AXE);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void hoe(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_HOE);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void stone(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.NEEDS_STONE_TOOL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void iron(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.NEEDS_IRON_TOOL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void diamond(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.NEEDS_DIAMOND_TOOL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }
}
