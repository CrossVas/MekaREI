package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ThermalEvaporationDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.common.MekanismLang;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ThermalEvaporationCategory implements DisplayCategory<ThermalEvaporationDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ThermalEvaporationDisplay> getCategoryIdentifier() {
        return Categories.EVAPORATION_TOWER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.THERMAL_EVAPORATION_CONTROLLER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.THERMAL_EVAPORATION_CONTROLLER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(ThermalEvaporationDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().getInput().getRepresentations()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - slotSize - offset, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - slotSize - innerOffset + 1, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);

        GuiHelper.addLabel(widgets, point(bounds.getCenterX(), bounds.getMinY() + slotSize), MekanismLang.EVAPORATION_HEIGHT.translate(18).withStyle(ChatFormatting.BLACK));
        GuiHelper.addLabel(widgets, point(bounds.getCenterX(), bounds.getMaxY() - slotSize - lineHeight), MekanismLang.TEMPERATURE.translate(MekanismUtils.getTemperatureDisplay(300.0, UnitDisplayUtils.TemperatureUnit.KELVIN, true)).withStyle(ChatFormatting.BLACK));
        return widgets;
    }

    @Override
    public int getDisplayWidth(ThermalEvaporationDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
