package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.SawmillRecipe;

import java.util.List;

public class SawmillDisplay extends BasicDisplay {

    SawmillRecipe RECIPE;

    public SawmillDisplay(SawmillRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())),
                List.of(EntryIngredients.ofItemStacks(recipe.getMainOutputDefinition()),
                        EntryIngredients.ofItemStacks(recipe.getSecondaryOutputDefinition())));
        this.RECIPE = recipe;
    }

    public SawmillDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public SawmillRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.SAWMILL;
    }
}
