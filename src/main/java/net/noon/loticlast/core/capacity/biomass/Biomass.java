package net.noon.loticlast.core.capacity.biomass;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.noon.loticlast.core.TagsL;
import net.noon.loticlast.core.capacity.loticlast.LoticlastCap;


public class Biomass{

    public static final String KEY = TagsL.of("Biomass");

    private int maxAmount;
    private int amount;  // 为 Biomass 数据类型

    public Biomass() {}
    public Biomass(int maxAmount, int amount) {
        this.maxAmount = maxAmount;
        this.amount = Math.min(amount, maxAmount);
    }

    public String getKey() {
        return KEY;
    }

    public static Biomass load(LivingEntity living){
        CompoundTag data = living.getPersistentData();
        if (data.contains(KEY)){
            Biomass biomass = new Biomass();
            biomass.deserializeNBT(data.getCompound(KEY));
            return biomass;
        }
        Biomass biomass = new Biomass(100,0);
        biomass.save(living);
        return biomass;
    }

    public static boolean contains(LivingEntity living){
        return living.getPersistentData().contains(KEY);
    }

    protected CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("maxAmount", maxAmount);
        tag.putInt("amount", amount);
        return tag;
    }

    protected void deserializeNBT(CompoundTag nbt) {
        maxAmount = nbt.getInt("maxAmount");
        amount = nbt.getInt("amount");
    }

    // 判满
    public boolean isFull(){
        return amount >= maxAmount;
    }
    // 添加，有上限
    public void add(int amount){
        this.amount = Math.min(maxAmount, this.amount + amount);
    }
    // 消耗，前提足够
    public void consume(int amount){
        if (this.amount >= amount) this.amount -= amount;
    }

    public void save(LivingEntity living){
        living.getPersistentData().put(KEY, serializeNBT());
    }


}
