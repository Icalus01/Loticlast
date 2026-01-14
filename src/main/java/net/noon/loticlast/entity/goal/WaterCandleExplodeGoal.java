package net.noon.loticlast.entity.goal;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.noon.loticlast.entity.custom.disperser.WaterCandle;


import java.util.EnumSet;

public class WaterCandleExplodeGoal extends Goal {

    private final WaterCandle waterCandle;
    private int chargeTime = 0;

    public WaterCandleExplodeGoal(WaterCandle waterCandle) {
        this.waterCandle = waterCandle;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = waterCandle.getTarget();
        return target != null && target.isAlive() &&
                waterCandle.distanceToSqr(target) <= 400.0D; // 20格内
    }

    @Override
    public void start() {
        chargeTime = 0;

        super.start();
    }

    @Override
    public void tick() {

        if (waterCandle.level().isClientSide)return;

        LivingEntity target = waterCandle.getTarget();
        if (target != null && waterCandle.distanceToSqr(target) <= 4.0D){
            chargeTime++;
        }

        if (waterCandle.getTarget() != null){
            PathNavigation navigation = waterCandle.getNavigation();
            navigation.moveTo(waterCandle.getTarget(), 1.0D);
        }

        // 1.5秒后爆炸
        if (chargeTime >= 30) {

            Level level = waterCandle.level();
            level.explode(waterCandle,
                    waterCandle.getX(),
                    waterCandle.getY(),
                    waterCandle.getZ(), 5, Level.ExplosionInteraction.NONE);

            chargeTime = 0;
            this.waterCandle.remove(Entity.RemovalReason.KILLED);
        }
    }

}
