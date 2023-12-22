package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ElectrolysisRecipe;

import java.util.List;

public class ElectrolyticSeparatorDisplay extends BasicDisplay {

    List<GasStack> LEFT_GAS;
    List<GasStack> RIGHT_GAS;

    ElectrolysisRecipe RECIPE;

    public ElectrolyticSeparatorDisplay(ElectrolysisRecipe recipe) {
        this(List.of(VanillaEntryHelper.fluids(recipe.getInput().getRepresentations())),
                List.of(MekanismEntryTypes.gases(recipe.getOutputDefinition().stream().map(ElectrolysisRecipe.ElectrolysisRecipeOutput::left).toList()),
                        MekanismEntryTypes.gases(recipe.getOutputDefinition().stream().map(ElectrolysisRecipe.ElectrolysisRecipeOutput::right).toList())));
        this.LEFT_GAS = recipe.getOutputDefinition().stream().map(ElectrolysisRecipe.ElectrolysisRecipeOutput::left).toList();
        this.RIGHT_GAS = recipe.getOutputDefinition().stream().map(ElectrolysisRecipe.ElectrolysisRecipeOutput::right).toList();
        this.RECIPE = recipe;
    }

    public ElectrolyticSeparatorDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ElectrolysisRecipe recipe() {
        return this.RECIPE;
    }

    public List<GasStack> leftGases() {
        return this.LEFT_GAS;
    }

    public List<GasStack> rightGases() {
        return this.RIGHT_GAS;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.SEPARATOR;
    }
}
