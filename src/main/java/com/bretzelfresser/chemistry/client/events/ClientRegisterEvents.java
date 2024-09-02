package com.bretzelfresser.chemistry.client.events;


import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.client.screens.ReactionChamberScreen;
import com.bretzelfresser.chemistry.common.registries.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ChemistryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegisterEvents {


    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModMenus.REACTION_CHAMBER_MENU.get(), ReactionChamberScreen::new));
    }
}
