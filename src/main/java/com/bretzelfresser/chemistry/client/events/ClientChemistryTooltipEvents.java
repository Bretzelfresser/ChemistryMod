package com.bretzelfresser.chemistry.client.events;

import com.bretzelfresser.chemistry.ChemistryMod;
import com.bretzelfresser.chemistry.common.registries.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChemistryMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientChemistryTooltipEvents {


    @SubscribeEvent
    public static void onTooltipCreation(ItemTooltipEvent event){
        if (event.getItemStack().getItem() == ModItems.SULFUR.get() && event.getFlags().isAdvanced()){
            event.getToolTip().add(Component.literal("S8"));
        }
    }
}
