package com.bretzelfresser.chemistry.common.blockEntity;

import com.bretzelfresser.chemistry.common.registries.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BigReactionChamberBlockEntity extends ReactionChamberBlockEntity{
    public BigReactionChamberBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.BIG_REACTION_CHAMBER.get(), p_155229_, p_155230_, 9);
    }
}
