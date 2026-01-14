package net.noon.loticlast.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ExampleEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ExampleEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    // Geckolib动画系统
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "controller", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<ExampleEntity> state) {
        // 根据状态播放动画
        if (state.isMoving()) {
            state.setAnimation(RawAnimation.begin().thenPlay("animation.sf_nba.alligator.walk"));
            return PlayState.CONTINUE;
        }

        state.setAnimation(RawAnimation.begin().thenPlay("animation.sf_nba.alligator.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();

        // 0: 浮在水面上（如果是水生或怕水生物）
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // 1: 被玩家手持特定物品吸引（类似被动生物）
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.1D, Ingredient.of(Items.APPLE), false));
        // 2: 近战攻击 (如果实体是敌对的)
        // 参数：实体，移动速度，攻击时是否追逐（对会飞的生物设为false）
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        // 3: 随机漫步（避开水面）
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        // 4: 看向最近的玩家
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        // 5: 随机环顾四周
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        // 0: 被攻击时，将攻击者设为目标（并通知同类）
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        // 1: 主动以玩家为目标（可在此增加条件，如只在黑夜攻击）
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));


    }

    // 静态属性构建方法（将在事件中调用）
    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ARMOR, 2.0);
    }

}