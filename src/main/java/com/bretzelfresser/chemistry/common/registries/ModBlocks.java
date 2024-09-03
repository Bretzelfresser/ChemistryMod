package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.block.BigReactionChamber;
import com.bretzelfresser.chemistry.common.block.NormalReactionChamberBlock;
import com.bretzelfresser.chemistry.common.block.ReactionChamberBlock;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChemistryMod.MODID);

    public static final RegistryObject<ReactionChamberBlock> NORMAL_REACTION_CHAMBER = registerWithTab("normal_reaction_chamber", () -> new NormalReactionChamberBlock(BlockBehaviour.Properties.of().strength(5).dynamicShape().noOcclusion().requiresCorrectToolForDrops()), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<ReactionChamberBlock> BIG_REACTION_CHAMBER = registerWithTab("big_reaction_chamber", () -> new BigReactionChamber(BlockBehaviour.Properties.of().strength(15).dynamicShape().noOcclusion().requiresCorrectToolForDrops()), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    public static final RegistryObject<DropExperienceBlock> SULFUR_ORE = registerWithTab("sulfur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3).requiresCorrectToolForDrops()), ModCreativeModeTabs.BLOCKS);
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_SULFUR_ORE = registerWithTab("deepslate_sulfur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).strength(4.5f, 3f).requiresCorrectToolForDrops()), ModCreativeModeTabs.BLOCKS);


    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier) {
        return register(name, blockSupplier, Item.Properties::new);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties) {
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Supplier<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, Item.Properties::new, tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties, Supplier<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, b -> new BlockItem(b, properties.get()), tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction, Supplier<CreativeModeTab> tab) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.registerWithCreativeModeTab(name, tab, () -> blockItemFunction.apply(block.get()));
        return block;
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, ResourceKey<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, Item.Properties::new, tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties, ResourceKey<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, b -> new BlockItem(b, properties.get()), tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction, ResourceKey<CreativeModeTab> tab) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.registerWithCreativeModeTab(name, tab, () -> blockItemFunction.apply(block.get()));
        return block;
    }
}
