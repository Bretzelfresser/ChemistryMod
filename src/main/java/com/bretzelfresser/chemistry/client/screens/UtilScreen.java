package com.bretzelfresser.chemistry.client.screens;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class UtilScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public static final ResourceLocation BLANK_GUI = ChemistryMod.modLoc("textures/gui/inventory_blank.png");
    public static final ResourceLocation SLOT_FIELD = ChemistryMod.modLoc("textures/gui/slot_field.png");
    public static final ResourceLocation NORMAL_ARROW = ChemistryMod.modLoc("textures/gui/arrow.png");
    public static final ResourceLocation LONG_ARROW = ChemistryMod.modLoc("textures/gui/arrow_long.png");
    public static final ResourceLocation FLAME = ChemistryMod.modLoc("textures/gui/flame.png");

    public UtilScreen(T container, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(container, playerInventory, title);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public UtilScreen(T container, Inventory playerInventory, Component title) {
        this(container, playerInventory, title, 176, 166);
    }


    /**
     * this will draw the blank gui in the middle of the screen
     * this uses {@link AbstractContainerScreen#leftPos} and {@link AbstractContainerScreen#topPos}
     */
    protected void drawBlankBackground(GuiGraphics graphics) {
        drawBlankBackground(graphics, leftPos, topPos);
    }

    /**
     * this will draw a blank inventory screen at the given x and y position
     *
     * @param x the x position of the blank screen
     * @param y the y position of the blank inventory screen
     */
    protected void drawBlankBackground(GuiGraphics graphics, int x, int y) {
        graphics.blit(BLANK_GUI, x, y, 0, 0, imageWidth, imageHeight);
    }

    /**
     * draws a normal arrow which is 22 pixels long and 16 pixels height
     *
     * @param scale how much of the arrow is filled up this is clamped in the range [0, 1]
     */
    protected void drawNormalArrow(GuiGraphics graphics, int x, int y, double scale) {
        scale = Mth.clamp(scale, 0d, 1d);
        graphics.blit(NORMAL_ARROW, x, y, 0, 0, 22, 16, 44, 16);
        if (scale == 0)
            return;
        double arrowWidth = 22d * scale;
        graphics.blit(NORMAL_ARROW, x, y, 22, 0, (int) arrowWidth, 16, 44, 16);
    }

    /**
     * draws a long arrow which is 44 pixels long and 16 pixels height
     *
     * @param scale how much of the arrow is filled up this is clamped in the range [0, 1]
     */
    protected void drawLongArrow(GuiGraphics graphics, int x, int y, double scale) {
        scale = Mth.clamp(scale, 0d, 1d);
        graphics.blit(LONG_ARROW, x, y, 0, 0, 44, 16, 88, 16);
        if (scale == 0)
            return;
        double arrowWidth = 44d * scale;
        graphics.blit(LONG_ARROW, x, y, 44, 0, (int) arrowWidth, 16, 88, 16);
    }

    /**
     * @param x                the x position where this is set, this isnt relative to the gui position
     * @param y                the y position where this is set, this isnt relative to the gui position
     * @param horizontalAmount the amount of slots which should be drawn in x direction
     * @param verticalAmount   the amount of slots which should be drawn in the y direction
     */
    protected void drawSlotField(GuiGraphics graphics, int x, int y, int horizontalAmount, int verticalAmount) {
        horizontalAmount = Mth.clamp(horizontalAmount, 1, 16);
        verticalAmount = Mth.clamp(verticalAmount, 1, 16);
        int width = 18 * horizontalAmount;
        int height = 18 * verticalAmount;
        graphics.blit(SLOT_FIELD, x, y, 0, 0, width, height, 288, 288);
    }

    /**
     * draws a single slot
     */
    protected void drawSingleSlot(GuiGraphics graphics, int x, int y) {
        drawSlotField(graphics, x, y, 1, 1);
    }

    protected void drawFlame(GuiGraphics graphics, int x, int y, double scale){
        scale = Mth.clamp(scale, 0d, 1d);
        graphics.blit(FLAME, x, y, 0, 0, 16, 16, 32, 16);
        if (scale == 0)
            return;
        double height = 16d * scale;
        int flameHeight = (int) Math.round(height);
        graphics.blit(FLAME, x, y + 16 - flameHeight, 16, 16 - flameHeight, 16, flameHeight, 32, 16);
    }


}
