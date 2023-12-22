package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackToPigmentRecipe;

import java.util.List;

public class PigmentExtractorDisplay extends BasicDisplay {

    ItemStackToPigmentRecipe RECIPE;

    public PigmentExtractorDisplay(ItemStackToPigmentRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())),
                List.of(MekanismEntryTypes.pigments(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public PigmentExtractorDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackToPigmentRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.PIGMENT_EXTRACTOR;
    }
}
