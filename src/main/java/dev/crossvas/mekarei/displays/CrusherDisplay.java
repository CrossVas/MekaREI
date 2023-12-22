package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackToItemStackRecipe;

import java.util.List;

public class CrusherDisplay extends BasicDisplay {

    ItemStackToItemStackRecipe RECIPE;

    public CrusherDisplay(ItemStackToItemStackRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())),
                List.of(EntryIngredients.ofItemStacks(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public CrusherDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackToItemStackRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.CRUSHER;
    }
}
