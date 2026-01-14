package net.noon.loticlast.core.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.noon.loticlast.Loticlast;

public class DamageTypesEB {

    public static final DeferredRegister<DamageType> DAMAGE_TYPES = DeferredRegister.create(Registries.DAMAGE_TYPE, Loticlast.MOD_ID);

    //=================================    伤害类型      ==============================

    public static final RegistryObject<DamageType> INFECT = register("infect");

    public static final RegistryObject<DamageType> CYTOKINES = register("cytokines");


    private static RegistryObject<DamageType> register(String type) {
        return DAMAGE_TYPES.register(type, () -> new DamageType(type, DamageScaling.NEVER, 0.0f, DamageEffects.HURT));
    }


}
