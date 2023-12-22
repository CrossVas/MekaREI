package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.recipes.ChemicalDissolutionRecipe;

import java.util.List;

public class ChemicalDissolutionDisplay extends BasicDisplay {

    ChemicalDissolutionRecipe RECIPE;
    List<GasStack> GASES;
    List<SlurryStack> SLURRY;

    public ChemicalDissolutionDisplay(ChemicalDissolutionRecipe recipe) {
        this(List.of(EntryIngredients.of(MekanismEntryTypes.GAS_ENTRY, recipe.getGasInput().getRepresentations()), EntryIngredients.ofItemStacks(recipe.getItemInput().getRepresentations())),
                List.of(recipe.getOutputDefinition().get(0).getChemicalStack() instanceof GasStack gas ? MekanismEntryTypes.gases(List.of(new GasStack(gas.getType(), gas.getAmount()))) :
                        recipe.getOutputDefinition().get(0).getChemicalStack() instanceof SlurryStack slurry ? MekanismEntryTypes.slurries(List.of(new SlurryStack(slurry.getType(), slurry.getAmount()))) :
                                EntryIngredient.empty()));
        this.RECIPE = recipe;
        this.GASES = List.of(recipe.getOutputDefinition().get(0).getChemicalStack() instanceof GasStack gas ? new GasStack(gas.getType(), gas.getAmount()) : GasStack.EMPTY);
        this.SLURRY = List.of(recipe.getOutputDefinition().get(0).getChemicalStack() instanceof SlurryStack slurry ? new SlurryStack(slurry.getType(), slurry.getAmount()) : SlurryStack.EMPTY);
    }

    public ChemicalDissolutionDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public List<GasStack> gases() {
        return this.GASES;
    }

    public List<SlurryStack> slurry() {
        return this.SLURRY;
    }

    public ChemicalDissolutionRecipe recipe() {
        return this.RECIPE;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.CHEMICAL_DISSOLUTION;
    }
}
