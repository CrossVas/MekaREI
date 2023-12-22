package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackToEnergyRecipe;

import java.util.List;

public class ItemStackToEnergyDisplay extends BasicDisplay {

    ItemStackToEnergyRecipe RECIPE;

    public ItemStackToEnergyDisplay(ItemStackToEnergyRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getInput().getRepresentations())), List.of(EntryIngredient.empty()));
        this.RECIPE = recipe;
    }

    public ItemStackToEnergyDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackToEnergyRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.ITEM_TO_ENERGY;
    }
}
