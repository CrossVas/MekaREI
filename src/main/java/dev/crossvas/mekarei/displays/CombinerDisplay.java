package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.CombinerRecipe;

import java.util.List;

public class CombinerDisplay extends BasicDisplay {

    CombinerRecipe RECIPE;

    public CombinerDisplay(CombinerRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getMainInput().getRepresentations()), EntryIngredients.ofItemStacks(recipe.getExtraInput().getRepresentations())),
                List.of(EntryIngredients.ofItemStacks(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public CombinerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public CombinerRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.COMBINER;
    }
}
