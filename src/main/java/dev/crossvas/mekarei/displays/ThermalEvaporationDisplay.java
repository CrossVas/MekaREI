package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.recipes.FluidToFluidRecipe;

import java.util.List;

public class ThermalEvaporationDisplay extends BasicDisplay {

    FluidToFluidRecipe RECIPE;

    public ThermalEvaporationDisplay(FluidToFluidRecipe recipe) {
        this(List.of(VanillaEntryHelper.fluids(recipe.getInput().getRepresentations())),
                List.of(VanillaEntryHelper.fluids(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public ThermalEvaporationDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public FluidToFluidRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.EVAPORATION_TOWER;
    }
}
