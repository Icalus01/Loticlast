package net.noon.loticlast.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.noon.loticlast.Loticlast;
import net.noon.loticlast.core.capacity.biomass.Biomass;
import net.noon.loticlast.core.capacity.loticlast.LoticlastCap;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TagsL {

    //================== 辅助方法 =====================

    public static String of(String name){
        return "loticlast:" + name;
    }

    //================== 数据迁移 =====================
    /**负责迁移玩家身上的全部自定义持久化数据，本类的标签，能力类的数据
     * 使用比较耗内存和性能的方式，能力系统在NeoForge高版本再使用，现在太难写了*/
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        //只有死亡时候复制数据
        if (event.isWasDeath()) {
            CompoundTag oldData = event.getOriginal().getPersistentData();
            CompoundTag newData = event.getEntity().getPersistentData();

            migrate(oldData, newData);

        }
    }

    /**迁移数据*/
    private static void migrate(CompoundTag oldData, CompoundTag newData) {

        // nbt数据
        nbtData(oldData, newData, Biomass.KEY);
        nbtData(oldData, newData, LoticlastCap.LOTICLAST_DATA);
    }

    private static void nbtData(CompoundTag oldData, CompoundTag newData, String key) {
        if (oldData.contains(key)){
            Tag tag = oldData.get(key);
            if (tag != null){
                newData.put(key, tag);
            }
        }
    }

    private static void booleanData(CompoundTag oldData, CompoundTag newData, String tag) {
        if (oldData.getBoolean(tag)){
            newData.putBoolean(tag, true);
        }
    }

    private static void intData(CompoundTag oldData, CompoundTag newData, String tag){
        if (oldData.contains(tag)){
            int oldInt = oldData.getInt(tag);
            newData.putInt(tag, oldInt);
        }
    }
}
