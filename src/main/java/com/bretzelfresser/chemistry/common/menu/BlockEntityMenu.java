package com.bretzelfresser.chemistry.common.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class BlockEntityMenu<T extends BlockEntity> extends UtilMenu {

    protected final T blockEntity;

    public BlockEntityMenu(@Nullable MenuType<?> type, int containerId, Inventory playerInv, T blockEntity) {
        super(type, containerId, playerInv);
        this.blockEntity = blockEntity;
        init();
    }
    public BlockEntityMenu(@Nullable MenuType<?> type, int containerId, Inventory playerInv, FriendlyByteBuf buffer) {
        this(type, containerId, playerInv, getClientTe(playerInv, buffer));
    }

    public abstract void init();


    /**
     * reads the entity from the {@link FriendlyByteBuf}
     * make sure u wrote the blockentity pos via {@link net.minecraftforge.network.NetworkHooks#openScreen(ServerPlayer, MenuProvider, Consumer)}
     * in the consumer with {@link FriendlyByteBuf#writeBlockPos(BlockPos)}
     */
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    protected static <X extends BlockEntity> X getClientTe(final Inventory inventory,
                                                           final FriendlyByteBuf buffer) {
        Objects.requireNonNull(inventory, "the inventory must not be null");
        Objects.requireNonNull(buffer, "the buffer must not be null");
        final BlockEntity entity = inventory.player.level().getBlockEntity(buffer.readBlockPos());
        return (X) entity;
    }

    public T getBlockEntity() {
        return blockEntity;
    }
}
