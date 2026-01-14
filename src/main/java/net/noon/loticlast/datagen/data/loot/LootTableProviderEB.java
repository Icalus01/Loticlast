package net.noon.loticlast.datagen.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class LootTableProviderEB extends LootTableProvider {

    public LootTableProviderEB(PackOutput output) {
        super(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(
                        EntityLootEB::new,
                        LootContextParamSets.ENTITY
                )
        ));
    }
}
