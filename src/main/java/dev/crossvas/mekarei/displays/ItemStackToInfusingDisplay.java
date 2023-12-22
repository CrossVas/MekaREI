package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackToInfuseTypeRecipe;

import java.util.Collections;
import java.util.List;

public class ItemStackToInfusingDisplay extends BasicDisplay {

    private ItemStackToInfuseTypeRecipe RECIPE;

    public ItemStackToInfusingDisplay(ItemStackToInfuseTypeRecipe recipe) {
        this(Collections.singletonList(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())),
                Collections.singletonList(MekanismEntryTypes.infusions(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public ItemStackToInfusingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackToInfuseTypeRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.ITEM_INFUSING;
    }
}
