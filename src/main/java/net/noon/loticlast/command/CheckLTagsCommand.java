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

public class CheckLTagsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("checkTag")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.entities())
                        .executes(context -> checkTags(context, EntityArgument.getEntities(context, "target")))
                )
        );
    }

    private static int checkTags(CommandContext<CommandSourceStack> context, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target.level().isClientSide) {
                return 0;
            }
            if (target instanceof LivingEntity living) {
                CompoundTag data = living.getPersistentData();
                StringBuilder msg = new StringBuilder();
                msg.append(living.getName().getString()).append(":\n");
                boolean found = false;

                for (String key : data.getAllKeys()) {
                    if (key.startsWith("loticlast")) {
                        found = true;
                        msg.append("  ").append(key).append(" : ").append(data.get(key)).append("\n");
                    }
                }

                if (!found) msg.append("  无自定义标签");
                context.getSource().sendSuccess(() -> Component.literal(msg.toString()), false);
            }
        }
        return targets.size();
    }
}