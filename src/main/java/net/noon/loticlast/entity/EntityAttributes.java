package net.noon.loticlast.entity;


import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.entity.custom.ExampleEntity;
import net.noon.loticlast.entity.custom.disperser.WaterCandle;
import net.noon.loticlast.entity.custom.gelatin.Gelatin;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributes {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) { // 确保事件类型正确

        event.put(EntitiesL.EXAMPLE_ENTITY.get(), ExampleEntity.createAttributes().build());

        event.put(EntitiesL.GELATIN.get(), Gelatin.createAttributes().build());

        event.put(EntitiesL.WATER_CANDLE.get(), WaterCandle.createAttributes().build());
    }

}
