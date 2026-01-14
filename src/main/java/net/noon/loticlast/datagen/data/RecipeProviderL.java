package net.noon.loticlast.datagen.data;


import net.noon.loticlast.block.BlocksEB;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RecipeProviderL extends RecipeProvider implements IConditionBuilder {

    public RecipeProviderL(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        addCraftingRecipes(pWriter);
        addCookingRecipes(pWriter);
    }

    private void addCraftingRecipes(Consumer<FinishedRecipe> writer){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlocksEB.EMERALD_BLOCK.get(), 1)
                .define('E', Items.EMERALD)
                .pattern("EE")
                .pattern("EE")
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EMERALD, 4)
                .requires(BlocksEB.EMERALD_BLOCK.get())
                .unlockedBy(getHasName(BlocksEB.EMERALD_BLOCK.get()), has(BlocksEB.EMERALD_BLOCK.get()))
                .save(writer);

    }

    private void addCookingRecipes(Consumer<FinishedRecipe> writer){
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(Blocks.RAW_IRON_BLOCK),
                RecipeCategory.MISC, Blocks.IRON_BLOCK, 9, 1800)
                .unlockedBy("smelting_raw_iron_block", has(Blocks.RAW_IRON_BLOCK))
                .save(writer);

    }


}
