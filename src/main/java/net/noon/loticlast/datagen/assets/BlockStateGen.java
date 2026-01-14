package net.noon.loticlast.datagen.assets;

import net.noon.loticlast.Loticlast;
import net.noon.loticlast.block.BlocksEB;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


public class BlockStateGen extends BlockStateProvider{

    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Loticlast.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlock(BlocksEB.EMERALD_BLOCK.get());


    }
}
