package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackGasToItemStackRecipe;

import java.util.List;

public class ChemicalInjectorDisplay extends BasicDisplay {

    ItemStackGasToItemStackRecipe RECIPE;

    public ChemicalInjectorDisplay(ItemStackGasToItemStackRecipe recipe) {
        this(List.of(MekanismEntryTypes.gases(recipe.getChemicalInput().getRepresentations()), EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations())),
                List.of(EntryIngredients.of(recipe.getResultItem())));
        this.RECIPE = recipe;
    }

    public ChemicalInjectorDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackGasToItemStackRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.CHEMICAL_INJECTOR;
    }
}
