package net.noon.loticlast.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.noon.loticlast.Loticlast;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxyEB {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
        });

    }

}
