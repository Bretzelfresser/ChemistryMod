package com.bretzelfresser.chemistry.common.menu;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public abstract class UtilMenu extends AbstractContainerMenu {

    public static String makeTranslationKey(String name){
        return "menu." + ChemistryMod.MODID + "." + name;
    }
    public static Component makeTranslationComponent(String name){
        return Component.translatable(makeTranslationKey(name));
    }

    protected final Inventory playerInventory;

    public UtilMenu(@Nullable MenuType<?> type, int containerId, Inventory playerInv) {
        super(type, containerId);
        this.playerInventory = playerInv;
    }

    public int addHorizontalSlots(IItemHandler handler, int index, int x, int y, int amount) {
        return addHorizontalSlots(handler, index, x, y, amount, 18);
    }

    public int addHorizontalSlots(IItemHandler handler, int index, int x, int y, int amount, int distanceBetweenSlots) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            index++;
            x += distanceBetweenSlots;
        }
        return index;
    }

    public int addSlotField(IItemHandler handler, int StartIndex, int x, int y, int horizontalAmount, int verticalAmount) {
        return addSlotField(handler, StartIndex, x, y, horizontalAmount, 18, verticalAmount, 18);
    }

    public int addSlotField(IItemHandler handler, int StartIndex, int x, int y, int horizontalAmount,
                            int horizontalDistance, int verticalAmount, int VerticalDistance) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    public void addPlayerInventory(int x, int y) {
        IItemHandler playerInv = new InvWrapper(playerInventory);

        // the Rest
        addSlotField(playerInv, 9, x, y, 9, 18, 3, 18);
        y += 58;
        // Hotbar
        addHorizontalSlots(playerInv, 0, x, y, 9, 18);
    }

}
