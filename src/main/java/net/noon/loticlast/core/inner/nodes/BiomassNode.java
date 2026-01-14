package net.noon.loticlast.core.inner.nodes;

import net.minecraft.nbt.CompoundTag;
import net.noon.loticlast.core.capacity.biomass.Biomass;

public class BiomassNode {

    private Biomass biomass;

    /*
    * Node需要有存在的必要吗？
    *
    * Pipe：连接两端，负责传输，每tick尝试传输数据
    * Pipe只能传递一种数据，类型必须相同
    *
    * */

    public BiomassNode() {
    }

    public BiomassNode(Biomass biomass) {
        this.biomass = biomass;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("Biomass", biomass.toNbt());
        return tag;
    }

    public static BiomassNode fromNBT(CompoundTag nbt) {
        return new BiomassNode(Biomass.fromNBT(nbt));
    }
}
