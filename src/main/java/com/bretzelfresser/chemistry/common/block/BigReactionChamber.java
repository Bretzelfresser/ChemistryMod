package com.bretzelfresser.chemistry.common.block;

import com.bretzelfresser.chemistry.common.blockEntity.ReactionChamberBlockEntity;
import com.bretzelfresser.chemistry.common.menu.UtilMenu;
import com.bretzelfresser.chemistry.common.registries.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BigReactionChamber extends ReactionChamberBlock{
    public BigReactionChamber(Properties p_54120_) {
        super(p_54120_, 9);
    }

    @Override
    public Component getInventoryTitle() {
        return UtilMenu.makeTranslationComponent("big_reaction_chamber");
    }

    @Override
    public <T extends ReactionChamberBlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) ModBlockEntities.BIG_REACTION_CHAMBER.get();
    }
}
