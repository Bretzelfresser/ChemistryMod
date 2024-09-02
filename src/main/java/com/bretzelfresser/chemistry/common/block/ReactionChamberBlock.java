package com.bretzelfresser.chemistry.common.block;

import com.bretzelfresser.chemistry.common.blockEntity.ReactionChamberBlockEntity;
import com.bretzelfresser.chemistry.common.menu.ReactionChamberMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public abstract class ReactionChamberBlock extends HorizontalFacingBlock implements EntityBlock {

    protected final int producAndEductSpace;

    public ReactionChamberBlock(Properties p_54120_, int producAndEductSpace) {
        super(p_54120_);
        this.producAndEductSpace = producAndEductSpace;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide){
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof ReactionChamberBlockEntity reactionChamber){
                NetworkHooks.openScreen((ServerPlayer) pPlayer, new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer1) -> new ReactionChamberMenu(pContainerId, pPlayerInventory, reactionChamber), getInventoryTitle()), pPos);
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return getBlockEntityType().create(pos, state);
    }

    public abstract Component getInventoryTitle();

    public abstract <T extends ReactionChamberBlockEntity> BlockEntityType<T> getBlockEntityType();

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, getBlockEntityType(), ReactionChamberBlockEntity::serverTick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>) p_152135_ : null;
    }
}
