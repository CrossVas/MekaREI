package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.PigmentExtractorDisplay;
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

public class PigmentExtractorCategory implements DisplayCategory<PigmentExtractorDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends PigmentExtractorDisplay> getCategoryIdentifier() {
        return Categories.PIGMENT_EXTRACTOR;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.PIGMENT_EXTRACTOR.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.PIGMENT_EXTRACTOR.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(PigmentExtractorDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), MekanismEntryTypes.pigments(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - slotSize + innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(PigmentExtractorDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
