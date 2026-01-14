package net.noon.loticlast.core.capacity.loticlast;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.noon.loticlast.core.TagsL;
import net.noon.loticlast.core.attribute.AttributeUtil;
import net.noon.loticlast.core.capacity.biomass.Biomass;

public class LoticlastCap {

    public static final String LOTICLAST_DATA = TagsL.of("Loticlast");

    public int boostHealth;
    public int boostRecovery;

    public LoticlastCap() {
    }

    public LoticlastCap(int boostHealth, int boostRecovery) {
        this.boostHealth = boostHealth;
        this.boostRecovery = boostRecovery;
    }

    // ============= 静态方法 ==============
    public static LoticlastCap load(LivingEntity living){
        CompoundTag data = living.getPersistentData();
        if (data.contains(LOTICLAST_DATA)){
            LoticlastCap loticlastCap = new LoticlastCap();
            loticlastCap.deserializeNBT(data.getCompound(LOTICLAST_DATA));
            return loticlastCap;
        }
        LoticlastCap loticlastCap = new LoticlastCap(4,1);
        loticlastCap.save(living);
        return loticlastCap;
    }
    public static boolean contains(LivingEntity living){
        return living.getPersistentData().contains(LOTICLAST_DATA);
    }

    /**转变为Loticlast*/
    public static void turn(LivingEntity living) {
        // Loticlast 基本能力
        LoticlastCap load = LoticlastCap.load(living);

        // 属性调整
        AttributeUtil.add(
                living,
                "maxHealthBoostFromLoticlast",
                Attributes.MAX_HEALTH,
                load.boostHealth,
                AttributeModifier.Operation.ADDITION
        );

        // 添加生物质
        //Biomass.load(living);
    }

    // ============= 序列化方法 ==============
    protected CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("boostHealth", boostHealth);
        tag.putInt("boostRecovery", boostRecovery);
        return tag;
    }

    protected void deserializeNBT(CompoundTag nbt) {
        boostHealth = nbt.getInt("boostHealth");
        boostRecovery = nbt.getInt("boostRecovery");
    }

    // ============= 底层执行方法 =============

    public void setBoostHealth(int amount){
        this.boostHealth = amount;
    }
    public void setBoostRecovery(int amount){
        this.boostRecovery = amount;
    }

    public void save(LivingEntity living){
        living.getPersistentData().put(LOTICLAST_DATA, serializeNBT());
    }


}
