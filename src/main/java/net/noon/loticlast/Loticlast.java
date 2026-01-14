package net.noon.loticlast;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.noon.loticlast.block.BlocksEB;
import net.noon.loticlast.datagen.assets.BlockStateGen;
import net.noon.loticlast.datagen.assets.ItemModelsGen;
import net.noon.loticlast.datagen.assets.lang.EnglishGen;
import net.noon.loticlast.datagen.data.LootModifiersL;
import net.noon.loticlast.datagen.data.RecipeProviderL;
import net.noon.loticlast.datagen.data.loot.LootTableProviderEB;
import net.noon.loticlast.effect.EffectsEB;
import net.noon.loticlast.entity.EntitiesL;
import net.noon.loticlast.item.TabsEB;
import net.noon.loticlast.item.ItemsL;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.noon.loticlast.item.potion.BrewingRecipesEB;
import net.noon.loticlast.item.potion.PotionsEB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Loticlast.MOD_ID)
public class Loticlast {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "loticlast";

    public Loticlast() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ItemsL.ITEMS.register(eventBus);
        TabsEB.CREATIVE_MODE_TABS.register(eventBus);
        BlocksEB.BLOCKS.register(eventBus);
        EffectsEB.EFFECTS.register(eventBus);
        EntitiesL.ENTITIES.register(eventBus);
        PotionsEB.POTIONS.register(eventBus);
        LootModifiersL.GLM_EB.register(eventBus);

        eventBus.addListener(this::generateData);
        eventBus.addListener(TabsEB::buiCreativeTab);
        eventBus.addListener(this::onCommonSetup);

    }

    /**数据生成*/
    private void generateData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        // assets
        generator.addProvider(event.includeClient(), new BlockStateGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ItemModelsGen(packOutput,fileHelper));
        generator.addProvider(event.includeClient(), new EnglishGen(packOutput, "en_us"));
        // data
        generator.addProvider(event.includeServer(), new RecipeProviderL(packOutput));
        generator.addProvider(event.includeServer(), new LootTableProviderEB(packOutput));


    }


    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(BrewingRecipesEB::register);
    }



}
