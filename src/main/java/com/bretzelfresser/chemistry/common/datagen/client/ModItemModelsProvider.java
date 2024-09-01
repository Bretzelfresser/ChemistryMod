package com.bretzelfresser.chemistry.common.datagen.client;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ChemistryMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
