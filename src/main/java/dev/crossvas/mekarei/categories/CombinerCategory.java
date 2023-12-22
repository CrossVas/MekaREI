package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.CombinerDisplay;
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

public class CombinerCategory implements DisplayCategory<CombinerDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends CombinerDisplay> getCategoryIdentifier() {
        return Categories.COMBINER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.COMBINER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.COMBINER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(CombinerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, point(adjustedInputPoint(bounds).x, adjustedInputPoint(bounds).y - slotSize), EntryIngredients.ofItemStacks(display.recipe().getMainInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addInputSlotElement(widgets, point(adjustedInputPoint(bounds).x, adjustedInputPoint(bounds).y + slotSize), EntryIngredients.ofItemStacks(display.recipe().getExtraInput().getRepresentations()), GuiElements.INPUT_EXTRA);
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addGuiElement(widgets, GuiElements.COMPONENT_ARROW_UP, point(adjustedInputPoint(bounds).x + innerOffset + 1, adjustedInputPoint(bounds).y + innerOffset));
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - offset - innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.BAR);
        return widgets;
    }

    @Override
    public int getDisplayWidth(CombinerDisplay display) {
        return defaultWidth;
    }

    @Override
    public int getDisplayHeight() {
        return defaultHeight;
    }
}
