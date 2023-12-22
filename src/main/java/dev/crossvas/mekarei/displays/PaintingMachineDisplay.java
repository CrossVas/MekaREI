package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.PaintingRecipe;

import java.util.List;

public class PaintingMachineDisplay extends BasicDisplay {

    PaintingRecipe RECIPE;

    public PaintingMachineDisplay(PaintingRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations()),
                        MekanismEntryTypes.pigments(recipe.getChemicalInput().getRepresentations())),
                List.of(EntryIngredients.ofItemStacks(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public PaintingMachineDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public PaintingRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.PAINTING;
    }
}
