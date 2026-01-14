package net.noon.loticlast.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegister {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CheckLTagsCommand.register(event.getDispatcher());
        ClearLTagsCommand.register(event.getDispatcher());
    }
}
