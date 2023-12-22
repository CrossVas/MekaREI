package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.client.jei.recipe.SPSJEIRecipe;

import java.util.Collections;
import java.util.List;

public class SPSDisplay extends BasicDisplay {

    SPSJEIRecipe RECIPE;

    public SPSDisplay(SPSJEIRecipe recipe) {
        this(List.of(MekanismEntryTypes.gases(recipe.input().getRepresentations())),
                List.of(MekanismEntryTypes.gases(Collections.singleton(recipe.output()))));
        this.RECIPE = recipe;
    }

    public SPSDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public SPSJEIRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.SPS;
    }
}
