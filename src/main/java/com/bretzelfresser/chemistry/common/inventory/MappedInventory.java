package com.bretzelfresser.chemistry.common.inventory;

import com.google.common.collect.Lists;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.ArrayUtils;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MappedInventory {

    Map<Item, List<ItemStack>> stacks = new HashMap<>();

    /**
     * will create this with all the initial stack inside it, this can be later modified by {@link MappedInventory#addStack(ItemStack)}
     */
    public MappedInventory(ItemStack... initialStacks){
        for (ItemStack stack : initialStacks){
            this.addStack(stack);
        }
    }

    public void addStack(ItemStack stack){
        if (!stacks.containsKey(stack.getItem())){
            stacks.put(stack.getItem(), Lists.newArrayList(stack));
        }else {
            stacks.get(stack.getItem()).add(stack);
        }
    }

    /**
     *
     * @param item gets all the stacks for this item, the stack dont necessarily match in terms of tag, they all must have the same {@link ItemStack#getItem()}
     */
    public List<ItemStack> getPerItem(ItemLike item){
        return stacks.getOrDefault(item.asItem(), Lists.newArrayList());
    }

    public int getCountPerItem(ItemLike item){
        return stacks.getOrDefault(item.asItem(), Lists.newArrayList()).stream().mapToInt(ItemStack::getCount).sum();
    }

    /**
     * this will return a list of stacks where all stacks that could be merged where merged
     */
    public List<ItemStack> getSortedAndMergedStacks(ItemLike item){
        if (!stacks.containsKey(item.asItem()))
            return Lists.newArrayList();
        List<ItemStack> stackPerItem = stacks.get(item.asItem());
        List<ItemStack> finalStacks = new ArrayList<>();
        for (ItemStack stack : stackPerItem){
            ItemStack saveToWorkWith = stack.copy();
            for (ItemStack finalStack : finalStacks) {
                if (ItemStack.isSameItemSameTags(finalStack, saveToWorkWith)) {
                    int maxGrow = Math.min(saveToWorkWith.getMaxStackSize() - finalStack.getCount(), saveToWorkWith.getCount());
                    finalStack.grow(maxGrow);
                    saveToWorkWith.shrink(maxGrow);
                    if (saveToWorkWith.isEmpty())
                        break;
                }
            }
            if (!saveToWorkWith.isEmpty())
                finalStacks.add(saveToWorkWith);
        }
        return finalStacks;
    }

    public void forSortedAllStacks(Consumer<ItemStack> stackComsumer){
        for (Item item : this.stacks.keySet()){
            List<ItemStack> sroted = this.getSortedAndMergedStacks(item);
            sroted.forEach(stackComsumer);
        }
    }


    public List<ItemStack> getAllSortedStacks(){
        List<ItemStack> stack = new ArrayList<>();
        for (Item item : this.stacks.keySet()){
            List<ItemStack> sroted = this.getSortedAndMergedStacks(item);
            stack.addAll(sroted);
        }
        return stack;
    }
}
