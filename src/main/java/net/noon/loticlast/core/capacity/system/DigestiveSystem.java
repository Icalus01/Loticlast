package net.noon.loticlast.core.capacity.system;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.noon.loticlast.core.TagsL;

public class DigestiveSystem {

    // 属于，玩家数据的一部分
    public static final String BIOMASS_DATA = TagsL.of("Biomass");

    // 实体引用
    private Player player;

    //功能：饥饿值，营养值，原版数据修正
    private int foodLevel;
    private float saturation;


    // ============= 静态方法 ==============
    public static DigestiveSystem load(Player player) {
        FoodData foodData = player.getFoodData();
        DigestiveSystem d = new DigestiveSystem();
        d.foodLevel = foodData.getFoodLevel();
        d.saturation = foodData.getSaturationLevel();
        d.player = player;
        return d;
    }


    // ============= 底层执行方法 ===================
    // 设置饱食度
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }
    // 设置营养值
    public void setSaturation(float saturation){
        this.saturation = saturation;
    }
    // 获取
    public int getFoodLevel(){
        return this.foodLevel;
    }
    public float getSaturation(){
        return this.saturation;
    }

    public void save() {
        FoodData foodData = player.getFoodData();
        foodData.setFoodLevel(foodLevel);
        foodData.setSaturation(saturation);
    }
}
