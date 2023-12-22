package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.common.recipe.impl.NutritionalLiquifierIRecipe;

import java.util.List;

public class NutritionalLiquificationDisplay extends BasicDisplay {

    NutritionalLiquifierIRecipe RECIPE;

    public NutritionalLiquificationDisplay(NutritionalLiquifierIRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())),
                List.of(VanillaEntryHelper.fluids(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public NutritionalLiquificationDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public NutritionalLiquifierIRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.NUTRITIONAL_LIQUIFICATION;
    }
}
