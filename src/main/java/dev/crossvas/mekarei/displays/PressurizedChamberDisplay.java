package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.PressurizedReactionRecipe;

import java.util.Collections;
import java.util.List;

public class PressurizedChamberDisplay extends BasicDisplay {

    PressurizedReactionRecipe RECIPE;
    GasStack GAS;

    public PressurizedChamberDisplay(PressurizedReactionRecipe recipe) {
        this(List.of(VanillaEntryHelper.fluids(recipe.getInputFluid().getRepresentations()),
                        MekanismEntryTypes.gases(recipe.getInputGas().getRepresentations()),
                        EntryIngredients.ofItemStacks(recipe.getInputSolid().getRepresentations())),
                List.of(EntryIngredients.of(recipe.getOutputDefinition().get(0).item()),
                        MekanismEntryTypes.gases(Collections.singleton(new GasStack(recipe.getOutputDefinition().get(0).gas(), recipe.getOutputDefinition().get(0).gas().getAmount())))));
        this.RECIPE = recipe;
        this.GAS = new GasStack(recipe.getOutputDefinition().get(0).gas(), recipe.getOutputDefinition().get(0).gas().getAmount());
    }

    public PressurizedChamberDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public GasStack gas() {
        return this.GAS;
    }

    public PressurizedReactionRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.PRESSURIZED_CHAMBER;
    }
}
