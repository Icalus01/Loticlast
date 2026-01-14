package net.noon.loticlast.entity.custom.disperser;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.noon.loticlast.entity.goal.WaterCandleExplodeGoal;
import net.noon.loticlast.core.capacity.loticlast.LoticlastCap;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WaterCandle extends FlyingMob implements GeoEntity{

    public WaterCandle(EntityType<? extends FlyingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
        this.moveControl = new FlyingMoveControl(this,180, true);
    }

    public static AttributeSupplier.Builder createAttributes() {

        return FlyingMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .add(Attributes.ARMOR, 0.0D);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel) {
            public boolean isStableDestination(BlockPos pPos) {
                return !level().getBlockState(pPos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected void registerGoals() {
        // 优先：爆炸攻击

        //this.goalSelector.addGoal(1, new WaterCandleFollowGoal(this, 0.2D, 10));
        this.goalSelector.addGoal(2, new WaterCandleExplodeGoal(this));

        // 目标选择
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true,
                living -> !living.getPersistentData().contains(LoticlastCap.LOTICLAST_DATA)
                ));

    }

    // =========== 动画部分 =============
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<WaterCandle> state) {
        // 默认空闲动画
        return state.setAndContinue(RawAnimation.begin().thenLoop("float"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}