package com.bretzelfresser.chemistry.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RecipeUtils {


    public static <T> List<T> deserializeList(JsonObject el, String memberName, Function<JsonElement, T> deserializer){
        return deserializeList(el, memberName, Function.identity(), deserializer);
    }
    public static <T, B> List<T> deserializeList(JsonObject el, String memberName,Function<JsonElement, B> mapper, Function<B, T> deserializer){
        JsonArray array = GsonHelper.getAsJsonArray(el, memberName);
        List<T> result = new ArrayList<>();
        for (JsonElement element : array){
            result.add(deserializer.apply(mapper.apply(element)));
        }
        return result;
    }
    public static <T> JsonElement serializeList(List<T> list, Function<T, JsonElement> serializer){
        JsonArray array = new JsonArray();
        for (T t : list){
            array.add(serializer.apply(t));
        }
        return array;
    }

    public static Pair<Ingredient, Integer> deserializeIngredientWithCount(JsonObject obj) {
        int count = GsonHelper.getAsInt(obj, "count", 1);
        Ingredient item = Ingredient.fromJson(obj.get("ingredient"));
        return Pair.of(item, count);
    }

    public static JsonObject serializeIngredientWithCount(Pair<Ingredient, Integer> ingredient) {
        JsonObject obj = new JsonObject();
        if (ingredient.getSecond() > 1) {
            obj.addProperty("count", ingredient.getSecond());
        }
        obj.add("ingredient", ingredient.getFirst().toJson());
        return obj;
    }

    public static Pair<Ingredient, Integer> readIngredientWithCount(FriendlyByteBuf buffer) {
        int count = buffer.readInt();
        Ingredient item = Ingredient.fromNetwork(buffer);
        return Pair.of(item, count);
    }

    public static void writeIngredientWithCount(FriendlyByteBuf buffer, Pair<Ingredient, Integer> ingredient) {
        buffer.writeInt(ingredient.getSecond());
        ingredient.getFirst().toNetwork(buffer);
    }

    public static <T> void writeList(FriendlyByteBuf buffer, List<T> list, BiConsumer<FriendlyByteBuf, T> writer) {
        buffer.writeInt(list.size());
        for (T t : list) {
            writer.accept(buffer, t);
        }
    }

    public static <T> List<T> readList(FriendlyByteBuf buffer, Function<FriendlyByteBuf, T> reader) {
        int size = buffer.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.apply(buffer));
        }
        return list;
    }

    public static boolean canMerge(ItemStack from, ItemStack into) {
        if (into.isEmpty())
            return true;
        return ItemStack.matches(from, into) && into.getCount() + from.getCount() <= into.getMaxStackSize();
    }

    public static JsonObject toJson(ItemStack stack) {
        JsonObject output = new JsonObject();
        output.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
        if (stack.getCount() > 1)
            output.addProperty("count", stack.getCount());
        return output;
    }
}
