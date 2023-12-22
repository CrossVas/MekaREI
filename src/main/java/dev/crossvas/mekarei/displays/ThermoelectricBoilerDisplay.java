package dev.crossvas.mekarei.displays;

import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.math.MathUtils;
import mekanism.client.jei.recipe.BoilerJEIRecipe;

import java.util.List;

public class ThermoelectricBoilerDisplay extends BasicDisplay {

    List<GasStack> SUPERHEATED_COOLANT;
    GasStack COOLED_COOLANT;
    double TEMPERATURE;
    int RATE;

    BoilerJEIRecipe RECIPE;

    public ThermoelectricBoilerDisplay(BoilerJEIRecipe recipe) {
        this(List.of(MekanismEntryTypes.gases(recipe.superHeatedCoolant() == null ? List.of(GasStack.EMPTY) : recipe.superHeatedCoolant().getRepresentations()),
                        VanillaEntryHelper.fluids(recipe.water().getRepresentations())),
                List.of(MekanismEntryTypes.gases(List.of(recipe.steam())), MekanismEntryTypes.gases(List.of(recipe.cooledCoolant()))));
        this.SUPERHEATED_COOLANT = recipe.superHeatedCoolant() == null ? List.of(GasStack.EMPTY) : recipe.superHeatedCoolant().getRepresentations();
        this.COOLED_COOLANT = recipe.cooledCoolant();
        this.TEMPERATURE = recipe.temperature();
        this.RATE = MathUtils.clampToInt(recipe.steam().getAmount());
        this.RECIPE = recipe;
    }

    public ThermoelectricBoilerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public BoilerJEIRecipe recipe() {
        return this.RECIPE;
    }

    public int rate() {
        return this.RATE;
    }

    public double temperature() {
        return this.TEMPERATURE;
    }

    public List<GasStack> superheatedCoolant() {
        return this.SUPERHEATED_COOLANT;
    }

    public GasStack cooledCoolant() {
        return this.COOLED_COOLANT;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return Categories.THERMOELECTRIC_BOILER;
    }
}
