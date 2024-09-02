package com.bretzelfresser.chemistry.common.menu;

import com.bretzelfresser.chemistry.common.blockEntity.ReactionChamberBlockEntity;
import com.bretzelfresser.chemistry.common.registries.ModMenus;
import com.bretzelfresser.chemistry.common.registries.ModRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

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
        int firstDivisor = 1;
        int producAndEductSpace = blockEntity.getProductAndEducSpace();
        for (int i = (int) Math.ceil(Math.sqrt(producAndEductSpace)); i > 1; i--) {
            if (producAndEductSpace % i == 0) {
                firstDivisor = i;
                break;
            }
        }
        int secondDivisor = producAndEductSpace / firstDivisor;
        int maxDivisor = Math.max(firstDivisor, secondDivisor);
        int height = maxDivisor * 18;
        int productX = 7;
        int productY = 3 + (70 - height);

        //products
        addSlotField(blockEntity.getInventory(), 0, productX + 1, productY + 1, producAndEductSpace / maxDivisor, maxDivisor);
        int arrowX = productX + (producAndEductSpace / maxDivisor) * 18 + 3;
        int flameX = arrowX + 22 - 8;

        productX = arrowX + 44 + 3;

        //educts
        addSlotField(blockEntity.getInventory(), producAndEductSpace, productX + 1, productY + 1, producAndEductSpace / maxDivisor, maxDivisor);
        addSlot(new SlotItemHandler(blockEntity.getInventory(), blockEntity.getInventory().getSlots() - 1, flameX + 1, 4 + 70 - 18){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, ModRecipes.REACTION_CHAMBER_RECIPE.get()) > 0;
            }
        });

        addPlayerInventory(8, 84);

        addDataSlots(blockEntity.getData());
    }

    public double getFuelPercentage() {
        return (double) blockEntity.getFuel() / (double) blockEntity.getMaxFuel();
    }

    public double getProcessingPercentage() {
        return (double) blockEntity.getProcessingTime() / (double) blockEntity.getMaxProcessingTime();
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return this.getSlot(index).getItem();
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
