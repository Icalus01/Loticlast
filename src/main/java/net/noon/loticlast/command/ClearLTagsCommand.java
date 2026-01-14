package net.noon.loticlast.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import java.util.Collection;

public class ClearLTagsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("clearEBTags")
                .requires(source -> source.hasPermission(3))
                .then(Commands.argument("target", EntityArgument.entities())
                        .executes(context -> clearTags(context, EntityArgument.getEntities(context, "target")))
                )
        );
    }

    private static int clearTags(CommandContext<CommandSourceStack> context, Collection<? extends Entity> targets) {
        int clearedCount = 0;
        int entityCount = 0;

        for (Entity target : targets) {
            if (target instanceof LivingEntity living) {
                entityCount++;
                CompoundTag data = living.getPersistentData();
                int before = data.getAllKeys().size();

                // 收集要删除的标签键
                String[] keys = data.getAllKeys().toArray(new String[0]);
                for (String key : keys) {
                    if (key.startsWith("loticlast")) {
                        data.remove(key);
                        clearedCount++;
                    }
                }

                int after = data.getAllKeys().size();
                String result = String.format("  %s: 删除了%d个标签 (之前:%d, 之后:%d)",
                        living.getName().getString(), before - after, before, after);
                context.getSource().sendSuccess(() -> Component.literal(result), false);
            }
        }

        if (entityCount == 0) {
            context.getSource().sendFailure(Component.literal("无有效生物"));
            return 0;
        }

        String summary = String.format("总计: 从%d个实体中清除了%d个EB标签", entityCount, clearedCount);
        context.getSource().sendSuccess(() -> Component.literal(summary), true);

        return clearedCount;
    }
}
