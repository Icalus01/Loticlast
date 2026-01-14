package net.noon.loticlast.effect.loticlast_inf;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.noon.loticlast.core.capacity.loticlast.LoticlastCap;
import net.noon.loticlast.core.damage.DamageStacker;
import net.noon.loticlast.core.damage.DamageTypesEB;
import org.jetbrains.annotations.NotNull;

public class LoticlastInf extends MobEffect {


    public LoticlastInf() {
        super(MobEffectCategory.NEUTRAL, 0xC2C2C2);
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity living, int pAmplifier) {
        if (living.level().isClientSide) {
            return;
        }

        if (living.getPersistentData().getBoolean(LoticlastCap.LOTICLAST_DATA)) {
            // 清除效果
            living.removeEffect(this);
            return;
        }

        // I
        if (pAmplifier == 0) {
            DamageStacker.hurt(living, 0.5f, DamageTypesEB.INFECT.getId());
        }
        // II
        else if (pAmplifier == 1 || pAmplifier == 2) {
            DamageStacker.hurt(living, 1.0f, DamageTypesEB.INFECT.getId());
        }
        // III
        else if (pAmplifier >= 3) {
            DamageStacker.hurt(living, 2.0f, DamageTypesEB.INFECT.getId());
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        if (pAmplifier >= 2) {
            return pDuration % 10 == 0;
        }
        return pDuration % 20 == 0;
    }


}


