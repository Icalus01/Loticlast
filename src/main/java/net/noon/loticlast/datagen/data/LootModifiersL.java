package net.noon.loticlast.datagen.data;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.datagen.data.archaeology.ArchaeologyLootModifier;

public class LootModifiersL {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM_EB =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Loticlast.MOD_ID);

    public static final RegistryObject<Codec<ArchaeologyLootModifier>> ARCHAEOLOGY_LOOT_MODIFIER =
            GLM_EB.register("archaeology_loot_modifier", () -> ArchaeologyLootModifier.CODEC);

}
