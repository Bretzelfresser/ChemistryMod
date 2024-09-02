package com.bretzelfresser.chemistry.common.datagen.server.recipe;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.recipe.ReactionChamberRecipe;
import com.bretzelfresser.chemistry.common.recipe.RecipeUtils;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReactionChamberRecipeBuilder {

    public static ReactionChamberRecipeBuilder builder(ItemLike item) {
        return builder(item, 1);
    }

    public static ReactionChamberRecipeBuilder builder(ItemLike item, int count) {
        return new ReactionChamberRecipeBuilder(new ItemStack(item, count));
    }

    protected List<Pair<Ingredient, Integer>> inputs = new ArrayList<>();
    protected List<ItemStack> outputs = new ArrayList<>();

    protected int processingTime = 200;
    protected boolean needsEnergy = true;

    public ReactionChamberRecipeBuilder(ItemStack output) {
        this.outputs.add(output);
    }

    public ReactionChamberRecipeBuilder addInput(Ingredient input) {
        return addInput(input, 1);
    }

    public ReactionChamberRecipeBuilder addInput(ItemLike... input) {
        return addInput(1, input);
    }

    public ReactionChamberRecipeBuilder addInput(TagKey<Item> input) {
        return addInput(input, 1);
    }

    public ReactionChamberRecipeBuilder addInput(Ingredient input, int count) {
        this.inputs.add(Pair.of(input, count));
        return this;
    }

    public ReactionChamberRecipeBuilder addInput(int count, ItemLike... input) {
        if (input.length == 0) {
            throw new IllegalArgumentException("cant create an Ingredient with no items in it");
        }
        return addInput(Ingredient.of(input), count);
    }

    public ReactionChamberRecipeBuilder addInput(TagKey<Item> input, int count) {
        return addInput(Ingredient.of(input), count);
    }

    public ReactionChamberRecipeBuilder addOutput(ItemLike output) {
        return addOutput(output, 1);
    }

    public ReactionChamberRecipeBuilder addOutput(ItemLike output, int count) {
        return addOutput(new ItemStack(output, count));
    }

    public ReactionChamberRecipeBuilder addOutput(ItemStack output) {
        this.outputs.add(output);
        return this;
    }


    public ReactionChamberRecipeBuilder setProcessingTime(int processingTime) {
        if (this.processingTime != processingTime)
            this.processingTime = processingTime;
        return this;
    }

    public ReactionChamberRecipeBuilder doesntNeedEnergy() {
        this.needsEnergy = false;
        return this;
    }

    public void build(Consumer<FinishedRecipe> builder, String name) {
        build(builder, ChemistryMod.modLoc(name));
    }

    public void build(Consumer<FinishedRecipe> builder, ResourceLocation id) {
        if (inputs.isEmpty() || outputs.isEmpty())
            throw new IllegalStateException("inputs and outputs must at least have 1 element");
        builder.accept(new Result(id, inputs, outputs, processingTime, needsEnergy));
    }


    protected static class Result implements FinishedRecipe {

        protected final ResourceLocation id;
        protected final List<Pair<Ingredient, Integer>> inputs;
        protected final List<ItemStack> outputs;
        protected final int processingTime;
        protected final boolean needsEnergy;

        public Result(ResourceLocation id, List<Pair<Ingredient, Integer>> inputs, List<ItemStack> outputs, int processingTime, boolean neededEnergy) {
            this.id = id;
            this.inputs = inputs;
            this.outputs = outputs;
            this.processingTime = processingTime;
            this.needsEnergy = neededEnergy;
        }

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            jsonObject.add("inputs", RecipeUtils.serializeList(inputs, RecipeUtils::serializeIngredientWithCount));
            jsonObject.add("outputs", RecipeUtils.serializeList(outputs, RecipeUtils::toJson));
            if (!this.needsEnergy) {
                jsonObject.addProperty("needsEnergy", false);
            }
            if (this.processingTime != 200) {
                jsonObject.addProperty("processingTime", this.processingTime);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ReactionChamberRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
