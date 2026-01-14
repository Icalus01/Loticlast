package net.noon.loticlast.client;



import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.client.render.entity.WaterCandleRenderer;
import net.noon.loticlast.client.render.entity.ExampleEntityRenderer;
import net.noon.loticlast.client.render.entity.GelatinRenderer;
import net.noon.loticlast.entity.EntitiesL;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderers {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 注册实体渲染器
        event.registerEntityRenderer(EntitiesL.EXAMPLE_ENTITY.get(), ExampleEntityRenderer::new);

        event.registerEntityRenderer(EntitiesL.GELATIN.get(), GelatinRenderer::new);

        event.registerEntityRenderer(EntitiesL.WATER_CANDLE.get(), WaterCandleRenderer::new);
    }
}
