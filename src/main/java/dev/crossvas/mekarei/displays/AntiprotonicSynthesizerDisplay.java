package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.NucleosynthesizingRecipe;

import java.util.Collections;
import java.util.List;

public class AntiprotonicSynthesizerDisplay extends BasicDisplay {

    NucleosynthesizingRecipe RECIPE;

    public AntiprotonicSynthesizerDisplay(NucleosynthesizingRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations()),
                        MekanismEntryTypes.gases(recipe.getChemicalInput().getRepresentations())),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem())));
        this.RECIPE = recipe;
    }

    public AntiprotonicSynthesizerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public NucleosynthesizingRecipe recipe() {
        return this.RECIPE;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.ANTIPROTONIC_SYNTH;
    }
}
