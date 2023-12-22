package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.recipes.FluidSlurryToSlurryRecipe;

import java.util.List;

public class ChemicalWasherDisplay extends BasicDisplay {

    FluidSlurryToSlurryRecipe RECIPE;

    public ChemicalWasherDisplay(FluidSlurryToSlurryRecipe recipe) {
        this(List.of(MekanismEntryTypes.slurries(recipe.getChemicalInput().getRepresentations()),
                        VanillaEntryHelper.fluids(recipe.getFluidInput().getRepresentations())),
                List.of(MekanismEntryTypes.slurries(recipe.getOutputDefinition())));
        this.RECIPE = recipe;
    }

    public ChemicalWasherDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public FluidSlurryToSlurryRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.CHEMICAL_WASHER;
    }
}
