package net.noon.loticlast.item;
import net.noon.loticlast.Loticlast;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabsEB {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Loticlast.MOD_ID);
    public static final RegistryObject<CreativeModeTab> THELEMA_THINGS = CREATIVE_MODE_TABS.register(
            "thelema_things",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + Loticlast.MOD_ID))
                    .icon(() -> new ItemStack(ItemsL.PRISTINE_LOTUS_SEED.get()))
                    .build()
    );

    public static void buiCreativeTab(BuildCreativeModeTabContentsEvent event){
        if (event.getTab() == THELEMA_THINGS.get()){
            event.accept(ItemsL.PRISTINE_LOTUS_SEED);
            event.accept(ItemsL.TURBID_LOTUS_SEED);
        }
    }

}
