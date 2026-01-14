package net.noon.loticlast.datagen.data.archaeology;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ArchaeologyLootModifier extends LootModifier {

    public static final Codec<ArchaeologyLootModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance) // 继承基类的conditions
                    .and(
                            instance.group(
                                    ForgeRegistries.ITEMS.getCodec().fieldOf("addition").forGetter(m -> m.addition),
                                    Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance)
                            )
                    )
                    .apply(instance, ArchaeologyLootModifier::new)
    );

    private final Item addition;
    private final float chance; // 添加几率 (0.0 ~ 1.0)

    public ArchaeologyLootModifier(LootItemCondition[] conditions, Item addition, float chance) {
        super(conditions);
        this.addition = addition;
        this.chance = chance;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // 根据概率判断是否添加物品
        if (context.getRandom().nextFloat() < chance) {
            generatedLoot.add(new ItemStack(addition, 1));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
