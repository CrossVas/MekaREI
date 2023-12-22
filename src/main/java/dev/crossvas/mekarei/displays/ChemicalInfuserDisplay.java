package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.recipes.ChemicalInfuserRecipe;

import java.util.List;

public class ChemicalInfuserDisplay extends BasicDisplay {

    ChemicalInfuserRecipe RECIPE;

    public ChemicalInfuserDisplay(ChemicalInfuserRecipe recipe) {
        this(List.of(MekanismEntryTypes.gases(recipe.getLeftInput().getRepresentations()),
                MekanismEntryTypes.gases(recipe.getRightInput().getRepresentations())),
                List.of(MekanismEntryTypes.gases(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public ChemicalInfuserDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ChemicalInfuserRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.CHEMICAL_INFUSER;
    }
}
