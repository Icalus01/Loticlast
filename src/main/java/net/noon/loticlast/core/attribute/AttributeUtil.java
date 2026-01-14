package net.noon.loticlast.core.attribute;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.noon.loticlast.core.TagsL;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AttributeUtil {
    // 添加属性修改器
    public static void add(LivingEntity living,
                           String name,
                           Attribute attribute,
                           double amount,
                           AttributeModifier.Operation operation
    ){
        if (living == null || attribute == null) return;

        AttributeInstance instance = living.getAttribute(attribute);
        if (instance == null) return;

        UUID uuid = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));

        // 先移除已存在的
        instance.removeModifier(uuid);

        // 添加新的
        AttributeModifier modifier = new AttributeModifier(
                uuid,
                TagsL.of(name),
                amount,
                operation
        );
        instance.addPermanentModifier(modifier);
    }
    // 删除属性修改器
    public static void remove(LivingEntity living, String name, Attribute attribute) {
        if (living == null || attribute == null) return;

        AttributeInstance instance = living.getAttribute(attribute);
        if (instance != null) {
            UUID uuid = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
            instance.removeModifier(uuid);
        }
    }
}
