package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.blockEntity.BigReactionChamberBlockEntity;
import com.bretzelfresser.chemistry.common.blockEntity.NormalReactionChamberBlockEntity;
import com.sun.jna.platform.win32.OaIdl;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChemistryMod.MODID);

    public static final RegistryObject<BlockEntityType<NormalReactionChamberBlockEntity>> NORMAL_REACTION_CHAMBER = BLOCK_ENTITIES.register("normal_reaction_chamber", () -> BlockEntityType.Builder.of(NormalReactionChamberBlockEntity::new, ModBlocks.NORMAL_REACTION_CHAMBER.get()).build(null));
    public static final RegistryObject<BlockEntityType<BigReactionChamberBlockEntity>> BIG_REACTION_CHAMBER = BLOCK_ENTITIES.register("big_reaction_chamber", () -> BlockEntityType.Builder.of(BigReactionChamberBlockEntity::new, ModBlocks.BIG_REACTION_CHAMBER.get()).build(null));
}
