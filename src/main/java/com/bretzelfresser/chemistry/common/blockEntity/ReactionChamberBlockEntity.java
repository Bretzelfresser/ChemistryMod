package com.bretzelfresser.chemistry.common.blockEntity;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.inventory.MappedInventory;
import com.bretzelfresser.chemistry.common.recipe.ReactionChamberRecipe;
import com.bretzelfresser.chemistry.common.registries.ModRecipes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReactionChamberBlockEntity extends BlockEntity {
    protected ItemStackHandler inventory;
    protected int maxProcessingTime = 1, processingTime = 0, maxFuel = 1, fuel = 0;
    protected final int productAndEducSpace;

    protected ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> processingTime;
                case 1 -> maxProcessingTime;
                case 2 -> fuel;
                case 3 -> maxFuel;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> processingTime = pValue;
                case 1 -> maxProcessingTime = pValue;
                case 2 -> fuel = pValue;
                case 3 -> maxFuel = pValue;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public ReactionChamberBlockEntity(BlockEntityType<?> pType, BlockPos p_155229_, BlockState p_155230_, int productAndEducSpace) {
        super(pType, p_155229_, p_155230_);
        this.productAndEducSpace = productAndEducSpace;
        inventory = new ItemStackHandler(2 * productAndEducSpace + 1);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ReactionChamberBlockEntity te) {
        ReactionChamberRecipe recipe = te.getRecipe();
        if (recipe != null && te.getOrCheckFuel(recipe)) {
            if (te.processingTime == 0) {
                te.startProcessing(recipe);
            }
            if (te.processingTime >= te.maxProcessingTime) {
                te.finishProcessing(recipe);
            } else {
                te.processingTime++;
            }
        } else if (te.processingTime != 0) {
            te.resetProcessing();
        }
        if (te.fuel > 0)
            te.fuel--;
    }

    protected boolean getOrCheckFuel(ReactionChamberRecipe recipe) {
        if (!recipe.doesNeedEnergy())
            return true;
        if (fuel > 0) {
            return true;
        } else {
            int burnTime = ForgeHooks.getBurnTime(this.inventory.getStackInSlot(this.inventory.getSlots() - 1), ModRecipes.REACTION_CHAMBER_RECIPE.get());
            if (burnTime > 0) {
                maxFuel = burnTime;
                fuel = maxFuel;
                this.inventory.getStackInSlot(this.inventory.getSlots() - 1).shrink(1);
            }
        }
        return fuel > 0;
    }

    protected void startProcessing(ReactionChamberRecipe recipe) {
        maxProcessingTime = recipe.getProcessingTime();
    }

    protected void finishProcessing(ReactionChamberRecipe recipe) {
        resetProcessing();
        shrinkInputs(recipe);
        growOutputs(recipe);
    }

    protected void growOutputs(ReactionChamberRecipe recipe) {
        MappedInventory inv = new MappedInventory();
        for (ItemStack stack : recipe.getOutputs()) {
            inv.addStack(stack.copy());
        }
        for (int i = productAndEducSpace; i < this.inventory.getSlots() - 1; i++) {
            inv.addStack(this.inventory.getStackInSlot(i));
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        List<ItemStack> finalStacks = inv.getAllSortedStacks();
        if (finalStacks.size() > productAndEducSpace) {
            throw new IllegalStateException(String.format("either the condition about the outputs didnt work cause the sorted and merged stacks had size : %s and we only had space for %s items", finalStacks.size(), productAndEducSpace));
        }
        for (int i = 0; i < finalStacks.size(); i++) {
            this.inventory.setStackInSlot(i + productAndEducSpace, finalStacks.get(i));
        }
    }

    protected void shrinkInputs(ReactionChamberRecipe recipe) {
        List<Pair<Ingredient, Integer>> inputs = recipe.getInputs();
        int[] counts = new int[inputs.size()];
        Ingredient[] ingredients = new Ingredient[inputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            counts[i] = inputs.get(i).getSecond();
            ingredients[i] = inputs.get(i).getFirst();
        }

        for (int i = 0; i < getProductAndEducSpace(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            for (int j = 0; j < inputs.size(); j++) {
                if (counts[j] > 0 && ingredients[j].test(stack)) {
                    ItemStack remainder = this.inventory.extractItem(i, counts[j], false);
                    counts[j] -= remainder.getCount();
                }
            }
        }
    }

    protected void resetProcessing() {
        maxProcessingTime = 1;
        processingTime = 0;
    }

    @Nullable
    protected ReactionChamberRecipe getRecipe() {
        Container inv = new SimpleContainer(this.inventory.getSlots());
        for (int i = 0; i < inv.getContainerSize(); i++) {
            inv.setItem(i, this.inventory.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.REACTION_CHAMBER_RECIPE.get(), inv, this.level).orElse(null);
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", inventory.serializeNBT());
        tag.putInt("processingTime", this.processingTime);
        tag.putInt("maxProcessingTime", this.maxProcessingTime);
        tag.putInt("fuel", this.fuel);
        tag.putInt("maxFuel", this.maxFuel);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("inventory"));
        this.processingTime = tag.getInt("processingTime");
        this.maxProcessingTime = tag.getInt("maxProcessingTime");
        this.fuel = tag.getInt("fuel");
        this.maxFuel = tag.getInt("maxFuel");
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getProductAndEducSpace() {
        return productAndEducSpace;
    }

    public ContainerData getData() {
        return data;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int getFuel() {
        return fuel;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> inventory).cast();
        }
        return super.getCapability(cap, side);
    }
}
