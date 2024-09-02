package com.bretzelfresser.chemistry.client.screens;

import com.bretzelfresser.chemistry.common.menu.ReactionChamberMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ReactionChamberScreen extends UtilScreen<ReactionChamberMenu> {
    public ReactionChamberScreen(ReactionChamberMenu container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        drawBlankBackground(pGuiGraphics);
        int firstDivisor = 1;
        int producAndEductSpace = menu.getBlockEntity().getProductAndEducSpace();
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
        drawSlotField(pGuiGraphics, leftPos + productX, topPos + productY, producAndEductSpace / maxDivisor, maxDivisor);
        int arrowX = productX + (producAndEductSpace / maxDivisor) * 18 + 3;
        drawLongArrow(pGuiGraphics, leftPos + arrowX, topPos + 3 + (70 - 16) / 2 - 7, this.menu.getProcessingPercentage());
        int flameX = arrowX + 22 - 8;
        drawSingleSlot(pGuiGraphics, leftPos + flameX, topPos + 3 + 70 - 18);
        drawFlame(pGuiGraphics, leftPos + flameX, topPos + 3 + 70 - 18 - 18, this.menu.getFuelPercentage());
        productX = arrowX + 44 + 3;
        drawSlotField(pGuiGraphics, leftPos + productX, topPos + productY, producAndEductSpace / maxDivisor, maxDivisor);
    }
}
