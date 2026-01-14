package net.noon.loticlast.datagen.assets.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.effect.EffectsEB;
import net.noon.loticlast.entity.EntitiesL;
import net.noon.loticlast.item.ItemsL;

public class EnglishGen extends LanguageProvider {

    public EnglishGen(PackOutput output, String locale) {
        super(output, Loticlast.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // =========== 物品 ==========
        add(ItemsL.TURBID_LOTUS_SEED.get(), "Turbid Lotus Seed");
        add(ItemsL.PRISTINE_LOTUS_SEED.get(), "Pristine Lotus Seed");

        // =========== 实体 ==========
        add(EntitiesL.GELATIN.get(), "Gelatin");
        add(EntitiesL.WATER_CANDLE.get(), "Water Candle");

        // =========== buff ==========
        add(EffectsEB.LOTICLAST_INF.get(), "Loticlast Inf");
        add(EffectsEB.LOTICLAST_REFACTOR.get(), "Loticlast Refactor");

        add("itemGroup.loticlast", "Loticlast");
        add("death.attack.loticlast.infect", "%1$s was infected");
        add("death.attack.loticlast.infect.player", "%1$s was infected by %2$s");

        add("key.loticlast.heal", "Heal (Regeneration)");
        add("category.loticlast", "Loticlast");

    }
}
