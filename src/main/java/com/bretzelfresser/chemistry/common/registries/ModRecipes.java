package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.recipe.ReactionChamberRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChemistryMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ChemistryMod.MODID);

    public static final RegistryObject<RecipeType<ReactionChamberRecipe>> REACTION_CHAMBER_RECIPE = register("reaction_chamber_recipe", () -> ReactionChamberRecipe.SERIALIZER);

    public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name, Supplier<RecipeSerializer<T>> serializer) {
        RegistryObject<RecipeType<T>> object = RECIPE_TYPES.register(name, () -> RecipeType.simple(ChemistryMod.modLoc(name)));
        RECIPE_SERIALIZER.register(name, serializer);
        return object;
    }

}
