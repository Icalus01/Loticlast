package net.noon.loticlast.core.capacity.loticlast;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoticlastCapEvent {

    /**回血时，25%尝试多恢复 1滴血（消耗营养值）*/
    @SubscribeEvent
    public static void boostHealSpeed(LivingHealEvent event){
        if (event.getEntity().level().isClientSide) return;

        LivingEntity living = event.getEntity();
        if (!(living instanceof Player player)) return;
        if (!LoticlastCap.contains(living)) return;

        LoticlastCap load = LoticlastCap.load(living);

        // 25% x level 的概率额外恢复
        if (living.getRandom().nextFloat() > 0.25 * load.boostRecovery) return;

        FoodData foodData = player.getFoodData();
        float saturationLevel = foodData.getSaturationLevel();

        // 检查条件：生命不满 且 有饱和度
        if (player.getHealth() < player.getMaxHealth() && saturationLevel >= 1.0f) {
            // 消耗饱和度
            foodData.setSaturation(saturationLevel - 1.0f);

            // 只增加治疗量，让原版系统处理
            float originalAmount = event.getAmount();
            event.setAmount(originalAmount + 1.0f);
        }

    }

}
