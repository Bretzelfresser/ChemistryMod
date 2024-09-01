package com.bretzelfresser.chemistry.common.menu;

import com.bretzelfresser.chemistry.common.blockEntity.ReactionChamberBlockEntity;
import com.bretzelfresser.chemistry.common.registries.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ReactionChamberMenu extends BlockEntityMenu<ReactionChamberBlockEntity> {
    public ReactionChamberMenu(int containerId, Inventory playerInv, ReactionChamberBlockEntity blockEntity) {
        super(ModMenus.REACTION_CHAMBER_MENU.get(), containerId, playerInv, blockEntity);
    }

    public ReactionChamberMenu(int containerId, Inventory playerInv, FriendlyByteBuf buffer) {
        super(ModMenus.REACTION_CHAMBER_MENU.get(), containerId, playerInv, buffer);
    }

    @Override
    public void init() {
        int margin = 10;
        int y = 84 / 2 - (2 * 18 + margin) / 2 + 1;
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 10, y));
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 10, y + 18 + margin));

        addSlot(new SlotItemHandler(blockEntity.getInventory(), 3, 60, 42 - 9));

        addPlayerInventory(8, 84);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
