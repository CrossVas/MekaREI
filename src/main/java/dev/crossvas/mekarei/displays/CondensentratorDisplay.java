package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.recipes.RotaryRecipe;

import java.util.List;

public class CondensentratorDisplay extends BasicDisplay {

    RotaryRecipe RECIPE;
    boolean GAS_TO_FLUID;

    public CondensentratorDisplay(RotaryRecipe recipe, boolean gasToFluid) {
        this(List.of(MekanismEntryTypes.gases(recipe.getGasInput().getRepresentations())),
                List.of(VanillaEntryHelper.fluids(recipe.getFluidInput().getRepresentations())));
        this.RECIPE = recipe;
        // because REI
        this.GAS_TO_FLUID = gasToFluid;
    }

    public CondensentratorDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public RotaryRecipe recipe() {
        return RECIPE;
    }

    public boolean gasToFluid() {
        return this.GAS_TO_FLUID;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return this.GAS_TO_FLUID ? Categories.CONDENSENTRATOR : Categories.DECONDENSENTRATOR;
    }
}
