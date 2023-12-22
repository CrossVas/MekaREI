package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.SawmillDisplay;
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
import mekanism.common.util.text.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SawmillCategory implements DisplayCategory<SawmillDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends SawmillDisplay> getCategoryIdentifier() {
        return Categories.SAWMILL;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.PRECISION_SAWMILL.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.PRECISION_SAWMILL.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(SawmillDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addOutputSlotElement(widgets, point(adjustedOutputPoint(bounds).x - slotSize - innerOffset, adjustedOutputPoint(bounds).y), EntryIngredients.ofItemStacks(display.recipe().getMainOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getSecondaryOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 3 * offset - 2, getProgressBarY(bounds)), 2000, GuiProgress.BAR);

        double secondaryChance = display.recipe().getSecondaryChance();
        if (secondaryChance > 0.0) {
            GuiHelper.addLabelRight(widgets, point(bounds.getMaxX() - offset, bounds.getMaxY() - offset - lineHeight), TextUtils.getPercent(secondaryChance).copy().withStyle(ChatFormatting.BLACK));
        }
        return widgets;
    }

    @Override
    public int getDisplayWidth(SawmillDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return defaultHeight;
    }
}
