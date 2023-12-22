package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.MetallurgicInfuserRecipe;

import java.util.Collections;
import java.util.List;

public class MetallurgicInfuserDisplay extends BasicDisplay {

    private MetallurgicInfuserRecipe RECIPE;

    public MetallurgicInfuserDisplay(MetallurgicInfuserRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations()),
                        MekanismEntryTypes.infusions(recipe.getChemicalInput().getRepresentations())),
                Collections.singletonList(EntryIngredients.ofItemStacks(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public MetallurgicInfuserDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public MetallurgicInfuserRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.METAL_INFUSING;
    }

}
