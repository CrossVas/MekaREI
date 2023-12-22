package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackToGasRecipe;

import java.util.List;

public class ItemStackToGasDisplay extends BasicDisplay {

    ItemStackToGasRecipe RECIPE;

    public ItemStackToGasDisplay(ItemStackToGasRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())),
                List.of(MekanismEntryTypes.gases(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public ItemStackToGasDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackToGasRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.ITEM_TO_GAS;
    }
}
