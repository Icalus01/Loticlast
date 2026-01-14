package net.noon.loticlast.core.capacity.biomass;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.core.TagsL;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomassEventL {

    public static final String TEMP_FOOD_DATA = TagsL.of("tempFoodData");

    @SubscribeEvent
    public static void startEat(LivingEntityUseItemEvent.Start event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            ItemStack item = event.getItem();
            if (item.isEdible()) {
                FoodData foodData = player.getFoodData();
                CompoundTag tag = new CompoundTag();
                tag.putInt("FoodLevel", foodData.getFoodLevel());
                tag.putFloat("SaturationLevel", foodData.getSaturationLevel());
                player.getPersistentData().put(TEMP_FOOD_DATA, tag);
            }
        }
    }

    @SubscribeEvent
    public static void stopEat(LivingEntityUseItemEvent.Stop event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            ItemStack item = event.getItem();
            if (item.isEdible()) {
                CompoundTag data = player.getPersistentData();
                if (data.contains(TEMP_FOOD_DATA)) {
                    data.remove(TEMP_FOOD_DATA);
                }
            }
        }
    }

    @SubscribeEvent
    public static void finishEat(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof Player player && Biomass.contains(player)) {

            ItemStack item = event.getItem();

            if (item.isEdible()) {

                FoodProperties foodProperties = item.getFoodProperties(player);
                int nutrition = foodProperties.getNutrition();
                float saturation = foodProperties.getSaturationModifier() * nutrition * 2.0f;

                CompoundTag tag = player.getPersistentData().getCompound(TEMP_FOOD_DATA);
                if (tag.isEmpty()) return;
                int foodLevel = tag.getInt("FoodLevel");
                float saturationLevel = tag.getFloat("SaturationLevel");

                int overN = 0;
                float overS = 0;
                // 饥饿值溢出
                if (nutrition + foodLevel > 20) {
                    overN = nutrition + foodLevel - 20;
                    // 计算营养值溢出
                    if (saturation + saturationLevel > 20) {
                        overS = saturation + saturationLevel - 20;
                    }
                    Biomass biomass = Biomass.load(player);
                    biomass.add(overN + (int)(overS * 2));
                    biomass.save(player);
                    //Biomass.add(player, overN + (int) (overS * 2));
                }
                // 饥饿值没有溢出
                else {
                    int max = nutrition + foodLevel;

                    // 计算营养值溢出
                    if (saturation + saturationLevel > max) {
                        overS = saturation + saturationLevel - max;
                    }
                    Biomass biomass = Biomass.load(player);
                    biomass.add(overN + (int) (overS * 2));
                    biomass.save(player);
//                    Biomass.add(player, overN + (int) (overS * 2));
                }

            }

        }

    }

}
