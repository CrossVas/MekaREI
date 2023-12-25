package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.GasToGasRecipeDisplay;
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

public class IsotopicCentrifugeCategory implements DisplayCategory<GasToGasRecipeDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends GasToGasRecipeDisplay> getCategoryIdentifier() {
        return Categories.ISOTOPIC_CENTRIFUGE;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.ISOTOPIC_CENTRIFUGE.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.ISOTOPIC_CENTRIFUGE.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(GasToGasRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);

        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.recipe().getInput().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);

        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 2 * offset, getProgressBarY(bounds)), 0, GuiProgress.ARROW_NORMAL_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(GasToGasRecipeDisplay display) {
        return 120;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
