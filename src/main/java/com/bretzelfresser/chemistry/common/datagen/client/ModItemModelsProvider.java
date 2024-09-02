package com.bretzelfresser.chemistry.common.datagen.client;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelsProvider extends ItemModelProvider {

    public final ModelFile generated = getExistingFile(mcLoc("item/generated"));
    public final ModelFile spawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));

    public final ModelFile rod = getExistingFile(mcLoc("item/handheld_rod"));
    public final ModelFile handheld = getExistingFile(mcLoc("item/handheld"));
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ChemistryMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simple(ModItems.SULFUR.get());
    }

    private void simple(Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            getBuilder(name).parent(generated).texture("layer0", "item/" + name);
        }
    }

    private void simple(ItemLike... items){
        for (ItemLike itemProvider : items){
            simple(itemProvider.asItem());
        }
    }

    private void spawnEgg(Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            getBuilder(name).parent(spawnEgg);
        }
    }

    private void rod(Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            getBuilder(name).parent(rod).texture("layer0", "item/" + name);
        }
    }

    protected void handheld(Item... items) {
        for (Item item : items) {
            String name = ForgeRegistries.ITEMS.getKey(item).getPath();
            getBuilder(name).parent(handheld).texture("layer0", "item/" + name);
        }
    }
}
