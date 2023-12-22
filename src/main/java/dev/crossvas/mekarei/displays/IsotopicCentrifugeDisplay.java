package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.recipes.GasToGasRecipe;

import java.util.List;

public class IsotopicCentrifugeDisplay extends BasicDisplay {

    GasToGasRecipe RECIPE;

    public IsotopicCentrifugeDisplay(GasToGasRecipe recipe) {
        this(List.of(MekanismEntryTypes.gases(recipe.getInput().getRepresentations())),
                List.of(MekanismEntryTypes.gases(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public IsotopicCentrifugeDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public GasToGasRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.ISOTOPIC_CENTRIFUGE;
    }
}
