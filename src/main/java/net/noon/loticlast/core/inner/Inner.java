package net.noon.loticlast.core.inner;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.noon.loticlast.core.TagsL;
import net.noon.loticlast.core.capacity.system.DigestiveSystem;
import net.noon.loticlast.core.inner.nodes.BiomassNode;

public class Inner {

    /*
    * 包含的数据：元数据，用于调节各组件的关系
    * 其中元素全部独立，都为一级数据。
    *
    * 不负责实际的数据传输，只构建 网络所需要的数据
    * */

    public static final String INNER_DATA = TagsL.of("Inner");

    public DigestiveSystem digestiveSystem;
    public BiomassNode biomassNode;

    public static Inner load(LivingEntity living){
        CompoundTag data = living.getPersistentData();
        return null;
    }

    protected CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        return tag;
    }

    protected void deserializeNBT(CompoundTag nbt) {

    }

}
