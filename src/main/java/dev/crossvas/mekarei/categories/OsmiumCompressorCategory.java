package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.MekaREI;
import dev.crossvas.mekarei.displays.OsmiumCompressorDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class OsmiumCompressorCategory implements DisplayCategory<OsmiumCompressorDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends OsmiumCompressorDisplay> getCategoryIdentifier() {
        return Categories.OSMIUM_COMPRESSOR;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.OSMIUM_COMPRESSOR.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.OSMIUM_COMPRESSOR.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(OsmiumCompressorDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        // chemical input
        List<ItemStack> chemicalProviders = new ObjectArrayList<>();
        List<GasStack> scaledGases = new ObjectArrayList<>();
        for (GasStack gas : display.recipe().getChemicalInput().getRepresentations()) {
            chemicalProviders.addAll(MekaREI.GAS_STACK_HELPER.getStacksFor(gas.getType(), true));
            scaledGases.add(new GasStack(gas, gas.getAmount() * 200));
        }
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, point(adjustedInputPoint(bounds).x, adjustedInputPoint(bounds).y - slotSize), EntryIngredients.ofItemStacks(display.recipe().getItemInput().getRepresentations()), GuiElements.INPUT);
        Point tankPoint = point(adjustedInputPoint(bounds).x + innerOffset + 1, adjustedInputPoint(bounds).y + 1);
        GuiHelper.addGuiElement(widgets, GuiElements.CHEMICAL_INFUSION_TANK, point(adjustedInputPoint(bounds).x + innerOffset + 1, adjustedInputPoint(bounds).y + 1));
        GuiHelper.addEntryWidget(widgets, tankPoint, MekanismEntryTypes.gases(scaledGases), GuiElements.CHEMICAL_INFUSION_TANK);
        GuiHelper.addInputSlotElement(widgets, point(adjustedInputPoint(bounds).x, adjustedInputPoint(bounds).y + slotSize), EntryIngredients.ofItemStacks(chemicalProviders), GuiElements.INPUT_EXTRA);
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - offset - innerOffset - 1, getProgressBarY(bounds)), 2000, GuiProgress.BAR);
        return widgets;
    }

    @Override
    public int getDisplayWidth(OsmiumCompressorDisplay display) {
        return defaultWidth;
    }

    @Override
    public int getDisplayHeight() {
        return defaultHeight;
    }
}
