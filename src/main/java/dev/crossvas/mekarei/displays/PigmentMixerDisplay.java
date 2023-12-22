package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.recipes.PigmentMixingRecipe;

import java.util.List;

public class PigmentMixerDisplay extends BasicDisplay {

    PigmentMixingRecipe RECIPE;

    public PigmentMixerDisplay(PigmentMixingRecipe recipe) {
        this(List.of(MekanismEntryTypes.pigments(recipe.getLeftInput().getRepresentations()),
                        MekanismEntryTypes.pigments(recipe.getRightInput().getRepresentations())),
                List.of(MekanismEntryTypes.pigments(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public PigmentMixerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public PigmentMixingRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.PIGMENT_MIXER;
    }
}
