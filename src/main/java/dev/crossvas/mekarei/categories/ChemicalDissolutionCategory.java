package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.displays.ChemicalDissolutionDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.chemical.ChemicalType;
import mekanism.api.chemical.gas.GasStack;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ChemicalDissolutionCategory implements DisplayCategory<ChemicalDissolutionDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ChemicalDissolutionDisplay> getCategoryIdentifier() {
        return Categories.CHEMICAL_DISSOLUTION;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.CHEMICAL_DISSOLUTION_CHAMBER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.CHEMICAL_DISSOLUTION_CHAMBER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(ChemicalDissolutionDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        List<GasStack> gases = display.recipe().getGasInput().getRepresentations().stream().map(gasStack -> new GasStack(gasStack, gasStack.getAmount() * 100)).toList();
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.gases(gases), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addInputSlotElement(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, adjustedInputPoint(bounds).y), EntryIngredients.ofItemStacks(display.recipe().getItemInput().getRepresentations()), GuiElements.INPUT);
        EntryIngredient liquidOutput;
        if (display.recipe().getOutputDefinition().get(0).getChemicalType().equals(ChemicalType.GAS)) {
            liquidOutput = MekanismEntryTypes.gases(display.gases());
        } else if (display.recipe().getOutputDefinition().get(0).getChemicalType().equals(ChemicalType.SLURRY)) {
            liquidOutput = MekanismEntryTypes.slurries(display.slurry());
        } else {
            liquidOutput = EntryIngredient.empty();
        }

        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), liquidOutput, GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 2 * offset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ChemicalDissolutionDisplay display) {
        return 128;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
