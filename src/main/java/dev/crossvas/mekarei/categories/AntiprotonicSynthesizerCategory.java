package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.AntiprotonicSynthesizerDisplay;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
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
import mekanism.common.MekanismLang;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class AntiprotonicSynthesizerCategory implements DisplayCategory<AntiprotonicSynthesizerDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends AntiprotonicSynthesizerDisplay> getCategoryIdentifier() {
        return Categories.ANTIPROTONIC_SYNTH;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.ANTIPROTONIC_NUCLEOSYNTHESIZER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.ANTIPROTONIC_NUCLEOSYNTHESIZER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(AntiprotonicSynthesizerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        Point adjustedInput = adjustedInputPoint(bounds);
        Point progressBar = point(getProgressBarX(bounds) - innerOffset, getProgressBarY(bounds));
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset + innerOffset), MekanismEntryTypes.gases(display.recipe().getChemicalInput().getRepresentations()), GuiElements.TANK_MIDDLE_RED, GuiElements.TANK_OVERLAY_MIDDLE);
        GuiHelper.addInputSlotElement(widgets, point(adjustedInput.x + slotSize, adjustedInput.y), EntryIngredients.ofItemStacks(display.recipe().getItemInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addProgressBar(widgets, progressBar, 2000, GuiProgress.ARROW_NORMAL_RIGHT);
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.of(display.recipe().getResultItem()), GuiElements.OUTPUT);
        GuiHelper.addLabelRight(widgets, point(bounds.getMaxX() - offset, bounds.getMaxY() - offset - lineHeight), MekanismLang.TICKS_REQUIRED.translate(display.recipe().getDuration()).withStyle(ChatFormatting.BLACK));
        return widgets;
    }

    @Override
    public int getDisplayWidth(AntiprotonicSynthesizerDisplay display) {
        return 150;
    }

    @Override
    public int getDisplayHeight() {
        return 64;
    }
}
