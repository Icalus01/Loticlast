package net.noon.loticlast.entity.goal.gelatin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.noon.loticlast.util.ParabolicCalculator;
import net.minecraft.server.level.ServerLevel;

public class EjectionAttackGoal extends Goal {

    private final PathfinderMob mob;
    private LivingEntity target;
    private Vec3 vector;
    private int chargingProgress;
    private int lastProgressMessageTick = 0;

    public EjectionAttackGoal(PathfinderMob mob) {
        this.mob = mob;
        this.vector = null;
        this.getFlags().add(Flag.MOVE);
    }

    @Override
    public boolean canUse() {
        // 每秒进行检测
        if (this.mob.tickCount % adjustedTickDelay(20) != 0) {
            return false;
        }

        // 目标状态判定
        LivingEntity target = this.mob.getTarget();
        if (target == null || target.isDeadOrDying()) {
            sendMessageToAllPlayers("[EjectionAttack] Target is null or dying", ChatFormatting.RED);
            return false;
        }

        double v = this.mob.distanceToSqr(target);
        // 在20和40内，进行弹射
        if (v <= 400 || v >= 1600) {
            sendMessageToAllPlayers(String.format("[EjectionAttack] Distance %.1f not in range 400-1600", v), ChatFormatting.YELLOW);
            return false;
        }

        // 获取 30度角下，从 A到 B，需要的矢量（方向，速度），返回null，表示无实数解
        Vec3 vec3 = ParabolicCalculator.getVector(this.mob.position(), target.position(), 30);
        if (vec3 == null) {
            sendMessageToAllPlayers("[EjectionAttack] No valid parabola solution", ChatFormatting.RED);
            return false;
        }

        this.vector = vec3;
        this.target = target;
        sendMessageToAllPlayers(String.format("[EjectionAttack] Launch vector calculated: (%.2f, %.2f, %.2f) speed: %.2f",
                vec3.x, vec3.y, vec3.z, vec3.length()), ChatFormatting.GREEN);
        return true;
    }

    @Override
    public void start() {
        chargingProgress = 0;
        lastProgressMessageTick = 0;
        sendMessageToAllPlayers("[EjectionAttack] Start charging", ChatFormatting.AQUA);
        // 停止移动，专注于蓄力
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        chargingProgress++;

        // 调试：显示当前计算信息
        if (chargingProgress == 1) {
            System.out.println("[EjectionAttackGoal] Start position: " + mob.position());
            System.out.println("[EjectionAttackGoal] Target position: " + target.position());
            System.out.println("[EjectionAttackGoal] Distance: " + mob.distanceTo(target));
            System.out.println("[EjectionAttackGoal] Horizontal distance: " +
                    Math.sqrt(Math.pow(target.getX() - mob.getX(), 2) +
                            Math.pow(target.getZ() - mob.getZ(), 2)));
        }

        // 每10tick显示进度
        if (chargingProgress % 10 == 0) {
            System.out.println("[EjectionAttackGoal] Charging progress: " + chargingProgress + "/40");
        }

        if (chargingProgress >= 40) {
            System.out.println("[EjectionAttackGoal] Launch vector: " + vector);
            System.out.println("[EjectionAttackGoal] Vector length: " + vector.length());
            System.out.println("[EjectionAttackGoal] Horizontal speed: " +
                    Math.sqrt(vector.x * vector.x + vector.z * vector.z));
            System.out.println("[EjectionAttackGoal] Vertical speed: " + vector.y);

            // 测试不同计算方法
            Vec3 simpleVec = ParabolicCalculator.getSimpleVector(mob.position(), target.position(), 30);
            Vec3 idealVec = ParabolicCalculator.getIdealVector(mob.position(), target.position());

            System.out.println("[EjectionAttackGoal] Simple vector: " + simpleVec);
            System.out.println("[EjectionAttackGoal] Ideal vector (45°): " + idealVec);

            // 使用修正后的向量（尝试45度角）
            if (idealVec != null) {
                mob.setDeltaMovement(idealVec);
                System.out.println("[EjectionAttackGoal] Using ideal vector (45°)");
            } else {
                mob.setDeltaMovement(vector);
                System.out.println("[EjectionAttackGoal] Using original vector");
            }

            this.stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        // 继续条件：有目标且正在蓄力中
        return target != null &&
                target.isAlive() &&
                chargingProgress > 0 &&
                chargingProgress < 40;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        chargingProgress = 0;
        vector = null;
        target = null;
        sendMessageToAllPlayers("[EjectionAttack] Stop", ChatFormatting.DARK_GRAY);
    }

    /**
     * 发送消息给所有在线玩家
     */
    private void sendMessageToAllPlayers(String message, ChatFormatting color) {
        if (mob.level() instanceof ServerLevel serverLevel) {
            Component text = Component.literal(message).withStyle(color);
            for (ServerPlayer player : serverLevel.players()) {
                player.sendSystemMessage(text);
            }
        }
    }
}