package com.bretzelfresser.chemistry.common.datagen.server;

import com.bretzelfresser.chemistry.common.datagen.server.recipe.ReactionChamberRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> generator) {
        ReactionChamberRecipeBuilder.builder(Items.CHARCOAL, 2).addInput(Items.COAL).addInput(ItemTags.PLANKS).setProcessingTime(400).build(generator, "charcoal_from_coal");
    }
}
