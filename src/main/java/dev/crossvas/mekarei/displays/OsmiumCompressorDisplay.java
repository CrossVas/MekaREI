package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.recipes.ItemStackGasToItemStackRecipe;

import java.util.List;

public class OsmiumCompressorDisplay extends BasicDisplay {

    ItemStackGasToItemStackRecipe RECIPE;

    public OsmiumCompressorDisplay(ItemStackGasToItemStackRecipe recipe) {
        this(List.of(EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations()),
                        MekanismEntryTypes.gases(recipe.getChemicalInput().getRepresentations())),
                List.of(EntryIngredients.ofItemStacks(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public OsmiumCompressorDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ItemStackGasToItemStackRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.OSMIUM_COMPRESSOR;
    }
}
