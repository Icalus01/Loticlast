package net.noon.loticlast.effect;


import net.noon.loticlast.Loticlast;
import net.noon.loticlast.effect.loticlast_inf.LoticlastInf;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noon.loticlast.effect.loticlast_refactor.LoticlastRefactor;

public class EffectsEB {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Loticlast.MOD_ID);

    public static final RegistryObject<MobEffect> LOTICLAST_INF = EFFECTS.register("loticlast_inf", LoticlastInf::new);

    public static final RegistryObject<MobEffect> LOTICLAST_REFACTOR = EFFECTS.register("loticlast_refactor", LoticlastRefactor::new);
}

