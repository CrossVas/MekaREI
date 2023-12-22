package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.PaintingMachineDisplay;
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

public class PaintingCategory implements DisplayCategory<PaintingMachineDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends PaintingMachineDisplay> getCategoryIdentifier() {
        return Categories.PAINTING;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.PAINTING_MACHINE.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.PAINTING_MACHINE.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(PaintingMachineDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, getCenterY(bounds)), EntryIngredients.ofItemStacks(display.recipe().getItemInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.pigments(display.recipe().getChemicalInput().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 2 * offset - innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(PaintingMachineDisplay display) {
        return 140;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
