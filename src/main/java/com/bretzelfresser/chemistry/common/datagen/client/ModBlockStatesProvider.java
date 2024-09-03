package com.bretzelfresser.chemistry.common.datagen.client;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.registries.ModBlocks;
import com.bretzelfresser.chemistry.common.util.ResourceLocationUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ChemistryMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlockWithParent(ModBlocks.NORMAL_REACTION_CHAMBER.get(), modLoc("reaction_chamber_prefab"), "texture", modLoc("block/normal_reaction_chamber"));
        horizontalBlockWithParent(ModBlocks.BIG_REACTION_CHAMBER.get(), modLoc("reaction_chamber_prefab"), "texture", modLoc("block/big_reaction_chamber"));
        simpleBlockWithItem(ModBlocks.SULFUR_ORE.get());
        simpleSidedBlock(ModBlocks.DEEPSLATE_SULFUR_ORE.get());

    }

    protected void simpleSidedBlock(Block block) {
        ResourceLocation key = key(block);
        ResourceLocation top = ResourceLocationUtils.prependExtend(key, "block/", "_top");
        ResourceLocation side = ResourceLocationUtils.prependExtend(key, "block/", "_side");
        ModelFile model = models().cubeBottomTop(key.getPath(), side, top, top);
        simpleBlockWithItem(block, model);
    }

    public void simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }


    protected void horizontalBlockWithParent(Block block, ResourceLocation parent, String textureKey, ResourceLocation texture) {
        BlockModelBuilder model = models().withExistingParent(name(block), parent).texture(textureKey, texture).renderType("translucent");
        horizontalBlock(block, $ -> model);
        simpleBlockItem(block, model);
    }

    public String name(Block block) {
        return key(block).getPath();
    }

    public ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
