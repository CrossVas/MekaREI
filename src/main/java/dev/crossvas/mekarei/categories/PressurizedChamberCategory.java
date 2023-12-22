package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.PressurizedChamberDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;

import java.util.List;

public class PressurizedChamberCategory implements DisplayCategory<PressurizedChamberDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends PressurizedChamberDisplay> getCategoryIdentifier() {
        return Categories.PRESSURIZED_CHAMBER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.PRESSURIZED_REACTION_CHAMBER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.PRESSURIZED_REACTION_CHAMBER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(PressurizedChamberDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().getInputFluid().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.recipe().getInputGas().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addInputSlotElement(widgets, point(bounds.getMinX() + 2 * slotSize + offset + 2 * innerOffset, adjustedInputPoint(bounds).y), EntryIngredients.ofItemStacks(display.recipe().getInputSolid().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addOutputSlotElement(widgets, point(bounds.getMaxX() - offset - 2 * slotSize - innerOffset, adjustedOutputPoint(bounds).y), EntryIngredients.of(display.recipe().getOutputDefinition().get(0).item()), GuiElements.OUTPUT);

        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getCenterY() - GuiElements.TANK_SMALL.height / 2), MekanismEntryTypes.gases(List.of(display.gas())), GuiElements.TANK_SMALL_BLUE, GuiElements.TANK_OVERLAY_SMALL);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_NORMAL_RIGHT);

        return widgets;
    }

    @Override
    public int getDisplayWidth(PressurizedChamberDisplay display) {
        return 160;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
