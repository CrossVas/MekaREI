package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ItemStackToItemStackRecipeDisplay;
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

public class EnricherCategory implements DisplayCategory<ItemStackToItemStackRecipeDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ItemStackToItemStackRecipeDisplay> getCategoryIdentifier() {
        return Categories.ENRICHMENT;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.ENRICHMENT_CHAMBER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.ENRICHMENT_CHAMBER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(ItemStackToItemStackRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getOutputDefinition()), GuiElements.OUTPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - offset - innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.BAR);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ItemStackToItemStackRecipeDisplay display) {
        return defaultWidth;
    }

    @Override
    public int getDisplayHeight() {
        return defaultHeight;
    }
}
