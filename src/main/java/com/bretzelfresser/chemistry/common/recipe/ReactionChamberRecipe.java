package com.bretzelfresser.chemistry.common.recipe;

import com.bretzelfresser.chemistry.common.registries.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReactionChamberRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    protected final ResourceLocation id;
    protected final List<Pair<Ingredient, Integer>> inputs;
    protected final List<ItemStack> outputs;
    protected final int processingTime;
    protected final boolean neededEnergy;

    public ReactionChamberRecipe(ResourceLocation id, List<Pair<Ingredient, Integer>> inputs, List<ItemStack> outputs, int processingTime, boolean neededEnergy) {
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
        this.processingTime = processingTime;
        this.neededEnergy = neededEnergy;
    }

    @Override
    public boolean matches(Container container, Level level) {
        int productSpace = (container.getContainerSize() - 1) / 2;
        ItemStackHandler wrapper = new ItemStackHandler(productSpace);
        for (int i = productSpace; i < container.getContainerSize() - 1; i++) {
            wrapper.setStackInSlot(i - productSpace, container.getItem(i).copy());
        }
        if (inputs.size() > productSpace || outputs.size() > productSpace)
            return false;
        int[] counts = new int[productSpace];
        Ingredient[] ingredients = new Ingredient[productSpace];
        for (int i = 0; i < inputs.size(); i++) {
            counts[i] = inputs.get(i).getSecond();
            ingredients[i] = inputs.get(i).getFirst();
        }

        for (int i = 0; i < productSpace; i++) {
            ItemStack stack = container.getItem(i);
            for (int j = 0; j < inputs.size(); j++) {
                if (counts[j] > 0 && ingredients[j].test(stack)) {
                    counts[j] -= Math.min(counts[j], stack.getCount());
                }
            }
        }

        for (int value : counts) {
            if (value != 0)
                return false;
        }


        //outputs
        List<ItemStack> outputs = new ArrayList<>();
        for (ItemStack stack : this.outputs) {
            outputs.add(stack.copy());
        }
        for (int i = 0; i < wrapper.getSlots() - 1; i++) {
            for (int j = 0; j < outputs.size(); j++) {
                outputs.set(j, wrapper.insertItem(i, outputs.get(j), false));
                if (outputs.get(j).isEmpty())
                    break;
            }
        }
        outputs.removeIf(ItemStack::isEmpty);
        return outputs.isEmpty();
    }

    public List<ItemStack> getOutputs() {
        return outputs;
    }

    public List<Pair<Ingredient, Integer>> getInputs() {
        return inputs;
    }

    public boolean doesNeedEnergy() {
        return neededEnergy;
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return outputs.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return outputs.get(0).copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.REACTION_CHAMBER_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<ReactionChamberRecipe> {

        @Override
        public ReactionChamberRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            int processingTime = GsonHelper.getAsInt(jsonObject, "processingTime", 200);
            JsonArray array = GsonHelper.getAsJsonArray(jsonObject, "inputs");
            if (array.isEmpty()) {
                throw new JsonParseException(String.format("the recipe with id: %s has an invalid amount of inputs: %s", id, array.size()));
            }
            List<Pair<Ingredient, Integer>> inputs = new ArrayList<>();
            for (JsonElement el : array) {
                JsonObject obj = el.getAsJsonObject();
                inputs.add(RecipeUtils.deserializeIngredientWithCount(obj));
            }
            array = GsonHelper.getAsJsonArray(jsonObject, "outputs");
            if (array.isEmpty()) {
                throw new JsonParseException(String.format("the recipe with id: %s has an invalid amount of outputs: %s", id, array.size()));
            }
            List<ItemStack> outputs = new ArrayList<>();
            for (JsonElement el : array) {
                outputs.add(ShapedRecipe.itemStackFromJson(el.getAsJsonObject()));
            }
            boolean needsEnergy = GsonHelper.getAsBoolean(jsonObject, "needsEnergy", true);
            return new ReactionChamberRecipe(id, inputs, outputs, processingTime, needsEnergy);
        }

        @Override
        public @Nullable ReactionChamberRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            List<Pair<Ingredient, Integer>> inputs = RecipeUtils.readList(buffer, RecipeUtils::readIngredientWithCount);
            List<ItemStack> outputs = RecipeUtils.readList(buffer, FriendlyByteBuf::readItem);
            int processingTime = buffer.readVarInt();
            boolean neededEnergy = buffer.readBoolean();
            return new ReactionChamberRecipe(id, inputs, outputs, processingTime, neededEnergy);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ReactionChamberRecipe recipe) {
            RecipeUtils.writeList(buffer, recipe.inputs, RecipeUtils::writeIngredientWithCount);
            RecipeUtils.writeList(buffer, recipe.outputs, FriendlyByteBuf::writeItem);
            buffer.writeVarInt(recipe.processingTime);
            buffer.writeBoolean(recipe.neededEnergy);
        }
    }
}
