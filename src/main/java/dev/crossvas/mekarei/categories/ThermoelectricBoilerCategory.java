package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ThermoelectricBoilerDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.client.jei.recipe.BoilerJEIRecipe;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.registries.MekanismGases;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils;
import mekanism.common.util.text.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;

import java.util.ArrayList;
import java.util.List;

public class ThermoelectricBoilerCategory implements DisplayCategory<ThermoelectricBoilerDisplay>, IGuiHelper {
    @Override
    public CategoryIdentifier<? extends ThermoelectricBoilerDisplay> getCategoryIdentifier() {
        return Categories.THERMOELECTRIC_BOILER;
    }

    @Override
    public Component getTitle() {
        return MekanismLang.BOILER.translate();
    }

    @Override
    public Renderer getIcon() {
        return new IconRenderer(MekanismUtils.getResource(MekanismUtils.ResourceType.GUI, "heat.png"), 18, 18, 18, 18);
    }

    @Override
    public List<Widget> setupDisplay(ThermoelectricBoilerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);

        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.superheatedCoolant()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().water().getRepresentations()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);

        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - 2 * slotSize - innerOffset, bounds.getMinY() + offset), MekanismEntryTypes.gases(List.of(display.recipe().steam())), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), MekanismEntryTypes.gases(List.of(display.cooledCoolant())), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);

        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 2 * offset, getProgressBarY(bounds)), 0, GuiProgress.ARROW_NORMAL_RIGHT);
        GuiHelper.addLabel(widgets, point(bounds.getCenterX(), bounds.getMinY() + slotSize), MekanismLang.TEMPERATURE.translate(MekanismUtils.getTemperatureDisplay(display.temperature(), UnitDisplayUtils.TemperatureUnit.KELVIN, true)).withStyle(ChatFormatting.BLACK));
        GuiHelper.addLabel(widgets, point(bounds.getCenterX(), bounds.getMaxY() - slotSize - lineHeight), MekanismLang.BOIL_RATE.translate(TextUtils.format(display.rate())).withStyle(ChatFormatting.BLACK));
        return widgets;
    }

    @Override
    public int getDisplayWidth(ThermoelectricBoilerDisplay display) {
        return 175;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    public static List<BoilerJEIRecipe> getBoilerRecipes() {
        int waterAmount = 1;
        double waterToSteamEfficiency = HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency();
        List<BoilerJEIRecipe> recipes = new ArrayList<>();
        double temperature = (double)waterAmount * waterToSteamEfficiency / (50.0 * MekanismConfig.general.boilerWaterConductivity.get()) + HeatUtils.BASE_BOIL_TEMP;
        recipes.add(new BoilerJEIRecipe(null, IngredientCreatorAccess.fluid().from(FluidTags.WATER, waterAmount), MekanismGases.STEAM.getStack((long)waterAmount), GasStack.EMPTY, temperature));

        for (Gas gas : MekanismAPI.gasRegistry()) {
            gas.ifAttributePresent(GasAttributes.HeatedCoolant.class, (heatedCoolant) -> {
                Gas cooledCoolant = heatedCoolant.getCooledGas();
                long coolantAmount = Math.round((double) waterAmount * waterToSteamEfficiency / heatedCoolant.getThermalEnthalpy());
                recipes.add(new BoilerJEIRecipe(IngredientCreatorAccess.gas().from(gas, coolantAmount), IngredientCreatorAccess.fluid().from(FluidTags.WATER, waterAmount), MekanismGases.STEAM.getStack((long) waterAmount), cooledCoolant.getStack(coolantAmount), HeatUtils.BASE_BOIL_TEMP));
            });
        }

        return recipes;
    }
}
