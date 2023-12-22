package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ItemStackToInfusingDisplay;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.common.MekanismLang;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.util.MekanismUtils;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ItemStackToInfusingCategory implements DisplayCategory<ItemStackToInfusingDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ItemStackToInfusingDisplay> getCategoryIdentifier() {
        return Categories.ITEM_INFUSING;
    }

    @Override
    public Component getTitle() {
        return MekanismLang.CONVERSION_INFUSION.translate();
    }

    @Override
    public Renderer getIcon() {
        return new IconRenderer(MekanismUtils.getResource(MekanismUtils.ResourceType.GUI, "infuse_types.png"), 18, 18, 18, 18);
    }

    @Override
    public List<Widget> setupDisplay(ItemStackToInfusingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        // input
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 2 * offset - 1, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_NORMAL_RIGHT);
        GuiHelper.addTank(widgets, point(adjustedOutputPoint(bounds).x, bounds.getMinY() + offset), MekanismEntryTypes.infusions(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ItemStackToInfusingDisplay display) {
        return 100;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
