package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.Categories;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.recipes.ChemicalCrystallizerRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CrystallizerDisplay extends BasicDisplay {

    List<GasStack> GASES;
    List<InfusionStack> INFUSIONS;
    List<PigmentStack> PIGMENTS;
    List<SlurryStack> SLURRY;

    List<ItemStack> INPUTS;
    ItemStack OUTPUT;
    ChemicalCrystallizerRecipe RECIPE;

    // TODO: re-think this
    public CrystallizerDisplay(ChemicalCrystallizerRecipe recipe) {
        this(List.of(
                recipe.getInput() instanceof ChemicalStackIngredient.GasStackIngredient gas ? MekanismEntryTypes.gases(gas.getRepresentations()) : // gas input
                        recipe.getInput() instanceof ChemicalStackIngredient.InfusionStackIngredient infusion ? MekanismEntryTypes.infusions(infusion.getRepresentations()) : // infusion input
                                recipe.getInput() instanceof ChemicalStackIngredient.PigmentStackIngredient pigment ? MekanismEntryTypes.pigments(pigment.getRepresentations()) : // pigment input
                                        recipe.getInput() instanceof ChemicalStackIngredient.SlurryStackIngredient slurry ? MekanismEntryTypes.slurries(slurry.getRepresentations()) : // slurry input
                                                EntryIngredient.empty(),
                        EntryIngredients.ofItemStacks(recipe.getOutputDefinition())), // stacks
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem()))); // output

        this.GASES = recipe.getInput() instanceof ChemicalStackIngredient.GasStackIngredient gas ? gas.getRepresentations() : List.of(GasStack.EMPTY);
        this.INFUSIONS = recipe.getInput() instanceof ChemicalStackIngredient.InfusionStackIngredient infusion ? infusion.getRepresentations() : List.of(InfusionStack.EMPTY);
        this.PIGMENTS = recipe.getInput() instanceof ChemicalStackIngredient.PigmentStackIngredient pigment ? pigment.getRepresentations() : List.of(PigmentStack.EMPTY);
        this.SLURRY = recipe.getInput() instanceof ChemicalStackIngredient.SlurryStackIngredient slurry ? slurry.getRepresentations() : List.of(SlurryStack.EMPTY);

        this.INPUTS = recipe.getOutputDefinition();
        this.OUTPUT = recipe.getResultItem();
        this.RECIPE = recipe;
    }

    public CrystallizerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ChemicalCrystallizerRecipe recipe() {
        return this.RECIPE;
    }

    public List<GasStack> gases() {
        return this.GASES;
    }

    public List<InfusionStack> infusions() {
        return this.INFUSIONS;
    }

    public List<PigmentStack> pigments() {
        return this.PIGMENTS;
    }

    public List<SlurryStack> slurry() {
        return this.SLURRY;
    }

    public List<ItemStack> inputs() {
        return this.INPUTS;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.CRYSTALLIZER;
    }
}
