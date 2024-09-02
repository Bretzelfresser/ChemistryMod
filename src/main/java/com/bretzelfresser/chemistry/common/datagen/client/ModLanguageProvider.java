package com.bretzelfresser.chemistry.common.datagen.client;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.menu.UtilMenu;
import com.bretzelfresser.chemistry.common.registries.ModCreativeModeTabs;
import com.bretzelfresser.chemistry.common.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.RegistryObject;

public class ModLanguageProvider extends BetterLanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, ChemistryMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(this::simpleItem);
        simpleMenuName("normal_reaction_chamber");
        simpleMenuName("big_reaction_chamber");

        simpleCreativeTab(ModCreativeModeTabs.ITEMS);
    }


}
