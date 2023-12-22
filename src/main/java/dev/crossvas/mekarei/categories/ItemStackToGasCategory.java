package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ItemStackToGasDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mekanism.common.MekanismLang;
import mekanism.common.util.MekanismUtils;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ItemStackToGasCategory implements DisplayCategory<ItemStackToGasDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ItemStackToGasDisplay> getCategoryIdentifier() {
        return Categories.ITEM_TO_GAS;
    }

    @Override
    public Component getTitle() {
        return MekanismLang.CONVERSION_GAS.translate();
    }

    @Override
    public Renderer getIcon() {
        return new IconRenderer(MekanismUtils.getResource(MekanismUtils.ResourceType.GUI, "gases.png"), 18, 18, 18, 18);
    }

    @Override
    public List<Widget> setupDisplay(ItemStackToGasDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - slotSize + innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ItemStackToGasDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
