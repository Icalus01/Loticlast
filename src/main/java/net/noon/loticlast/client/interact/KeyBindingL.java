package net.noon.loticlast.client.interact;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBindingL {

    public static final KeyMapping INNER_VIEW_KEY = new KeyMapping(
            "key.loticlast.heal", // 本地化键名
            GLFW.GLFW_KEY_H,           // 默认按键 H
            "category.loticlast"  // 本地化类别名
    );



    @Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(INNER_VIEW_KEY);
        }
    }

}
