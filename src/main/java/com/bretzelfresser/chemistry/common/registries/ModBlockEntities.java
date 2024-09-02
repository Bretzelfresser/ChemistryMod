package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.blockEntity.NormalReactionChamberBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChemistryMod.MODID);

    public static final RegistryObject<BlockEntityType<NormalReactionChamberBlockEntity>> NORMAL_REACTION_CHAMBER = BLOCK_ENTITIES.register("normal_reaction_chamber", () -> BlockEntityType.Builder.of(NormalReactionChamberBlockEntity::new, ModBlocks.NORMAL_REACTION_CHAMBER.get()).build(null));
}
