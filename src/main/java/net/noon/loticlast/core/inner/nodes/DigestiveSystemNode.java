package net.noon.loticlast.core.inner.nodes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.noon.loticlast.core.TagsL;
import net.noon.loticlast.core.capacity.system.DigestiveSystem;

public class DigestiveSystemNode {

    public static final String DS_DATA = TagsL.of("DS");



    // 其中具有两个数据
    private DigestiveSystem system;

    // 单位：tick
    private int inputSpeed = 20;
    private int outputSpeed = 20;

    // 输出
    public static DigestiveSystemNode load(LivingEntity living){
        if (living.getPersistentData().contains(DS_DATA)){
            CompoundTag data = living.getPersistentData();
            DigestiveSystemNode node = new DigestiveSystemNode();
            node.deserializeNBT(data);
            return node;
        }
        return new DigestiveSystemNode();
    }

    protected CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("inputSpeed", inputSpeed);
        tag.putInt("outputSpeed", outputSpeed);
        return tag;
    }

    protected void deserializeNBT(CompoundTag nbt) {
        this.inputSpeed = nbt.getInt("inputSpeed");
        this.outputSpeed = nbt.getInt("outputSpeed");
    }






}
