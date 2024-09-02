package com.bretzelfresser.chemistry.common.block;

import com.bretzelfresser.chemistry.common.blockEntity.ReactionChamberBlockEntity;
import com.bretzelfresser.chemistry.common.menu.UtilMenu;
import com.bretzelfresser.chemistry.common.registries.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class NormalReactionChamberBlock extends ReactionChamberBlock{
    public NormalReactionChamberBlock(Properties p_54120_) {
        super(p_54120_, 6);
    }

    @Override
    public Component getInventoryTitle() {
        return UtilMenu.makeTranslationComponent("normal_reaction_chamber");
    }

    @Override
    public <T extends ReactionChamberBlockEntity> BlockEntityType<T> getBlockEntityType() {
        return (BlockEntityType<T>) ModBlockEntities.NORMAL_REACTION_CHAMBER.get();
    }
}
