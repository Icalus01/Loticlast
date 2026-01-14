package net.noon.loticlast.entity.custom.gelatin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.noon.loticlast.entity.goal.gelatin.EjectionAttackGoal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Gelatin extends Monster implements GeoEntity {

    public Gelatin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    // ============= 属性设置 =============
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D) // 增加视野范围
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9D)
                .add(Attributes.ARMOR, 0.0D);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true; // 无呼吸槽
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    // ============= AI注册 =============
    @Override
    protected void registerGoals() {
        // 基础移动目标

        this.goalSelector.addGoal(1, new EjectionAttackGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        // 目标选择器
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        // 增加范围参数，确保能找到玩家
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this,
                Player.class,
                20, // 检查范围
                true, // mustSee
                false, // mustReach
                null
        ));

        // 调试：每100tick显示一次目标状态
        this.goalSelector.addGoal(5, new Goal() {
            @Override
            public boolean canUse() {
                return Gelatin.this.tickCount % 100 == 0;
            }

            @Override
            public void start() {
                if (!Gelatin.this.level().isClientSide) {
                    LivingEntity target = Gelatin.this.getTarget();
                    String message = String.format(
                            "[Gelatin] Tick: %d, Target: %s, Alive: %s, Distance: %.1f",
                            Gelatin.this.tickCount,
                            target != null ? target.getName().getString() : "null",
                            target != null && target.isAlive(),
                            target != null ? Gelatin.this.distanceTo(target) : 0.0
                    );
                    System.out.println(message);
                }
            }
        });
    }

    // ============= Geckolib动画系统 =============
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    private PlayState predicate(AnimationState<Gelatin> state) {
        // 默认空闲动画
        return state.setAndContinue(RawAnimation.begin().thenLoop("animation.gelatin.idle"));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // ============= 自定义方法 =============

    @Override
    public void tick() {
        super.tick();
        // 可选：每100tick输出调试信息
        if (this.tickCount % 100 == 0 && !this.level().isClientSide) {
            System.out.println("[Gelatin Debug] Position: " + this.blockPosition() +
                    ", Target: " + this.getTarget() +
                    ", Goals: " + this.goalSelector.getAvailableGoals().size() +
                    ", TargetGoals: " + this.targetSelector.getAvailableGoals().size());
        }
    }
}