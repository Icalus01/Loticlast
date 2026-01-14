package net.noon.loticlast.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.noon.loticlast.Loticlast;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.noon.loticlast.effect.EffectsEB;

public class ItemsL {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Loticlast.MOD_ID);

    //======================= 食物 =========================

    public static final RegistryObject<Item> TURBID_LOTUS_SEED = ITEMS.register("turbid_lotus_seed", () ->
            new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .saturationMod(0)
                            .nutrition(0)
                            .effect(() -> new MobEffectInstance(
                                    EffectsEB.LOTICLAST_INF.get(), 1200
                            ), 1.0f)
                            .alwaysEat()
                            .build()
            )));

    public static final RegistryObject<Item> PRISTINE_LOTUS_SEED = ITEMS.register("pristine_lotus_seed", () ->
            new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .saturationMod(0)
                            .nutrition(0)
                            .effect(() -> new MobEffectInstance(
                                    EffectsEB.LOTICLAST_REFACTOR.get(), 1200
                            ),1.0f)
                            .alwaysEat()
                            .build()
            )));


    /*public static final RegistryObject<Item> INF_GOLDEN_APPLE = ITEMS.register("inf_golden_apple", () ->
            new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .saturationMod(4)
                            .nutrition(4)
                            .effect(() -> new MobEffectInstance(
                                    EffectsEB.CYTOKINES_STORM.get(), 1200
                            ),1.0f)
                            .build()
            )));

    public static final RegistryObject<Item> INF_BEEF = ITEMS.register("inf_beef", () ->
            new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .saturationMod(1)
                            .nutrition(1)
                            .build()
            )));


    public static final RegistryObject<Item> INF_APPLE = ITEMS.register("inf_apple", () ->
            new Item(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .saturationMod(4)
                            .nutrition(4)
                            .build()
            )));*/

}
