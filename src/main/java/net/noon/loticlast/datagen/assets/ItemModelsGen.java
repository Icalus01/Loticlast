package net.noon.loticlast.datagen.assets;

import net.noon.loticlast.Loticlast;
import net.noon.loticlast.block.BlocksEB;
import net.noon.loticlast.item.ItemsL;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.Block;

public class ItemModelsGen extends ItemModelProvider {

    private final ModelFile GENERATED = getExistingFile(mcLoc("item/generated"));

    public ItemModelsGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Loticlast.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {


        itemModel(ItemsL.PRISTINE_LOTUS_SEED, GENERATED);
        itemModel(ItemsL.TURBID_LOTUS_SEED, GENERATED);
        blockModel(BlocksEB.EMERALD_BLOCK);


    }

    private ItemModelBuilder itemModel(RegistryObject<?> item, ModelFile modelFile) {
        return getBuilder(item.getId().getPath()).parent(modelFile).texture("layer0", "item/" + item.getId().getPath());
    }

    public void blockModel(RegistryObject<? extends Block> block) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }

    public void blockModel(RegistryObject<? extends Block> block, String suffix) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath() + "_" + suffix));
    }


}
