package net.noon.loticlast.util;

import net.minecraft.world.phys.Vec3;

public class ParabolicCalculator {

    /**
     * 获取固定角度下的弹射矢量
     * @param start 起点位置
     * @param target 目标位置
     * @param angleDegrees 发射角度（度）
     * @return 弹射速度矢量，若不可行返回null
     */
    public static Vec3 getVector(Vec3 start, Vec3 target, double angleDegrees) {
        double dx = target.x - start.x;
        double dz = target.z - start.z;
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);
        double dy = target.y - start.y;

        // 转换为弧度
        double angleRad = Math.toRadians(angleDegrees);

        // Minecraft重力常数（每tick²）
        final double GRAVITY = 0.08;

        // 正确的弹道公式：dy = d*tanθ - (g*d²)/(2*v²*cos²θ)
        // 解出 v² = (g*d²) / (2*cos²θ*(d*tanθ - dy))

        double tanTheta = Math.tan(angleRad);
        double cosTheta = Math.cos(angleRad);
        double cosSquared = cosTheta * cosTheta;

        // 避免除零
        if (horizontalDist < 0.001) {
            horizontalDist = 0.001;
        }

        double numerator = GRAVITY * horizontalDist * horizontalDist;
        double denominator = 2.0 * cosSquared * (horizontalDist * tanTheta - dy);

        // 检查是否可行（分母必须为正）
        if (denominator <= 0.001) {
            // 尝试使用最小可行速度
            double minVelocity = Math.sqrt(GRAVITY * horizontalDist);
            double vx = (dx / horizontalDist) * minVelocity * cosTheta;
            double vz = (dz / horizontalDist) * minVelocity * cosTheta;
            double vy = minVelocity * Math.sin(angleRad);
            return new Vec3(vx, vy, vz);
        }

        double vSquared = numerator / denominator;

        if (vSquared <= 0) {
            return null; // 无实数解
        }

        double velocity = Math.sqrt(vSquared);

        // 计算速度分量
        double horizontalSpeed = velocity * cosTheta;
        double vx = (dx / horizontalDist) * horizontalSpeed;
        double vz = (dz / horizontalDist) * horizontalSpeed;
        double vy = velocity * Math.sin(angleRad);

        return new Vec3(vx, vy, vz);
    }

    /**
     * 简化版本：计算相同水平面弹射
     * @param start 起点
     * @param target 目标
     * @param angleDegrees 角度
     * @return 速度矢量
     */
    public static Vec3 getSimpleVector(Vec3 start, Vec3 target, double angleDegrees) {
        double dx = target.x - start.x;
        double dz = target.z - start.z;
        double horizontalDist = Math.sqrt(dx * dx + dz * dz);
        double angleRad = Math.toRadians(angleDegrees);

        // 简化公式：对于相同水平面(dy=0)，v = sqrt(g*d / sin(2θ))
        double sin2Theta = Math.sin(2 * angleRad);

        if (Math.abs(sin2Theta) < 0.001) {
            return null;
        }

        final double GRAVITY = 0.08;
        double velocity = Math.sqrt(GRAVITY * horizontalDist / sin2Theta);

        double vx = (dx / horizontalDist) * velocity * Math.cos(angleRad);
        double vz = (dz / horizontalDist) * velocity * Math.cos(angleRad);
        double vy = velocity * Math.sin(angleRad);

        return new Vec3(vx, vy, vz);
    }

    /**
     * 测试函数：计算理想弹道
     */
    public static Vec3 getIdealVector(Vec3 start, Vec3 target) {
        // 使用45度角，这是最远水平距离的角度
        return getSimpleVector(start, target, 45.0);
    }
}