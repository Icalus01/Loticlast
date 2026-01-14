package net.noon.loticlast.item.potion;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.noon.loticlast.Loticlast;

public class PotionsEB {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Loticlast.MOD_ID);

  /*  public static final RegistryObject<Potion> IMMUNE_OVERACTIVITY_POTION = POTIONS.register("immune_overactivity_potion", ()-> new Potion(new MobEffectInstance(EffectsEB.CYTOKINES_STORM.get(), 1800)));

    public static final RegistryObject<Potion> LONG_IMMUNE_OVERACTIVITY_POTION = POTIONS.register("long_immune_overactivity_potion", ()-> new Potion(new MobEffectInstance(EffectsEB.CYTOKINES_STORM.get(), 4800)));

    public static final RegistryObject<Potion> STRONG_IMMUNE_OVERACTIVITY_POTION = POTIONS.register("strong_immune_overactivity_potion", ()-> new Potion(new MobEffectInstance(EffectsEB.CYTOKINES_STORM.get(), 1200, 1)));

*/
}
