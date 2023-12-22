package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ElectrolyticSeparatorDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ElectrolyticSeparatorCategory implements DisplayCategory<ElectrolyticSeparatorDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ElectrolyticSeparatorDisplay> getCategoryIdentifier() {
        return Categories.SEPARATOR;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.ELECTROLYTIC_SEPARATOR.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.ELECTROLYTIC_SEPARATOR.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(ElectrolyticSeparatorDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().getInput().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getCenterX() - slotSize - innerOffset, bounds.getCenterY() - 15), MekanismEntryTypes.gases(display.leftGases()), GuiElements.TANK_SMALL_BLUE, GuiElements.TANK_OVERLAY_SMALL);
        GuiHelper.addTank(widgets, point(bounds.getCenterX() + slotSize + innerOffset, bounds.getCenterY() - 15), MekanismEntryTypes.gases(display.rightGases()), GuiElements.TANK_SMALL_AQUA, GuiElements.TANK_OVERLAY_SMALL);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) + offset, getProgressBarY(bounds) + 1), 0, GuiProgress.BIDIRECTIONAL);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ElectrolyticSeparatorDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
