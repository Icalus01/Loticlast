package net.noon.loticlast.client.interact;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.core.inner.InnerScreen;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyHandlerL {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        // 防御性判断：避免游戏未加载完成/暂停/有GUI时触发
        if (event.phase != TickEvent.Phase.END || mc.isPaused() || mc.player == null) {
            return;
        }

        // 官方标准：使用 consumeClick() 消费按键点击（自动处理按下/重复）
        if (KeyBindingL.INNER_VIEW_KEY.consumeClick()) {
            // 打开界面（如果已有界面，可选择关闭或替换）
            if (mc.screen == null) {
                mc.setScreen(new InnerScreen());
            }
        }
    }
}