package com.bretzelfresser.chemistry.common.registries;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.menu.ReactionChamberMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {


    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ChemistryMod.MODID);

    public static final RegistryObject<MenuType<ReactionChamberMenu>> REACTION_CHAMBER_MENU = MENU_TYPES.register("reaction_chamber", () -> IForgeMenuType.create(ReactionChamberMenu::new));
}
