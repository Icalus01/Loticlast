package net.noon.loticlast.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.entity.custom.ExampleEntity;
import net.noon.loticlast.entity.custom.disperser.WaterCandle;
import net.noon.loticlast.entity.custom.gelatin.Gelatin;

public class EntitiesL {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Loticlast.MOD_ID);

    public static final RegistryObject<EntityType<ExampleEntity>> EXAMPLE_ENTITY =
            ENTITIES.register("example_entity",
                    () -> EntityType.Builder.of(ExampleEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.9f) // 宽度, 高度
                            .clientTrackingRange(10) // 客户端追踪范围
                            .build("example_entity"));

    public static final RegistryObject<EntityType<Gelatin>> GELATIN =
            ENTITIES.register("gelatin",
                    () -> EntityType.Builder.of(Gelatin::new, MobCategory.CREATURE)
                            .sized(1.0f, 1.0f)
                            .clientTrackingRange(20)
                            .build("gelatin"));

    public static final RegistryObject<EntityType<WaterCandle>> WATER_CANDLE =
            ENTITIES.register("water_candle", () -> EntityType.Builder.of(WaterCandle::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.0f)
                    .clientTrackingRange(20)
                    .build("water_candle"));

}
