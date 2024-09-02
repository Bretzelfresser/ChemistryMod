package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChemistryMod.MODID);


    public static final RegistryObject<CreativeModeTab> ITEMS = register("items", () -> CreativeModeTab.builder().icon(() -> ModItems.SULFUR.get().getDefaultInstance()));


    public static RegistryObject<CreativeModeTab> register(String name, Supplier<CreativeModeTab.Builder> builder){
        return TABS.register(name, () -> builder.get().title(Component.translatable(name)).build());
    }

    /**
     * this creates the translation key for the creative tabs
     * for example: input: "test_tab" -> "creativetab.(modid).test_tab"
     * this is used so the LanguageProvider can actually use this as translation key
     */
    public static String createTranslationKey(String name) {
        return "creativetab." + ChemistryMod.MODID + "." + name;
    }
}
