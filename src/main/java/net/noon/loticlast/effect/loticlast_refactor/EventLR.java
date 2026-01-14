package net.noon.loticlast.effect.loticlast_refactor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.effect.EffectsEB;
import net.noon.loticlast.core.capacity.loticlast.LoticlastCap;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventLR {

    // Loticlast标签添加
    @SubscribeEvent
    public static void turn(MobEffectEvent.Expired event){
        if (event.getEntity().level().isClientSide)return;

        LivingEntity living = event.getEntity();
        MobEffectInstance effect = living.getEffect(EffectsEB.LOTICLAST_REFACTOR.get());
        if (effect != null){

            LoticlastCap.turn(living);
        }

    }
}
