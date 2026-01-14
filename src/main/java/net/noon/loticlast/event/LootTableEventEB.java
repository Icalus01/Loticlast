package net.noon.loticlast.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.noon.loticlast.Loticlast;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Loticlast.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootTableEventEB {

    private static final ResourceLocation DESERT_PYRAMID = new ResourceLocation("minecraft", "archaeology/desert_pyramid");
    private static final ResourceLocation TRAIL_RUINS = new ResourceLocation("minecraft", "archaeology/trail_ruins");


    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event){

        ResourceLocation tableID = event.getName();
        LootTable table = event.getTable();
        if (DESERT_PYRAMID.equals(tableID) || TRAIL_RUINS.equals(tableID)){


            /*// 战利品条目
            LootPoolSingletonContainer.Builder<?> lotus = LootItem.lootTableItem(ItemsEB.TURBID_LOTUS_SEED.get())
                    .setWeight(800)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)));
            // 新建一个战利品池
            LootPool.Builder lotusPool = LootPool.lootPool()
                    .name("emergent_being_desert_pyramid_lotus_pool")
                    .add(lotus);
            // 添加战利品池
            table.addPool(lotusPool.build());*/
        }


    }

}
