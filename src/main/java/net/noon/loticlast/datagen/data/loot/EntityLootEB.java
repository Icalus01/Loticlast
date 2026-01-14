package net.noon.loticlast.datagen.data.loot;

import net.noon.loticlast.effect.EffectsEB;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class EntityLootEB extends EntityLootSubProvider {

    public EntityLootEB() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {

    }

    // 创建感染条件的辅助方法
    private static LootItemCondition.Builder createInfectedCondition() {
        return LootItemEntityPropertyCondition.hasProperties(
                LootContext.EntityTarget.THIS,
                EntityPredicate.Builder.entity()
                        .effects(MobEffectsPredicate.effects()
                                .and(EffectsEB.LOTICLAST_INF.get())
                        ).build()
        );
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
        );
    }
}
