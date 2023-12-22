package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ChemicalOxidizerDisplay;
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

public class ChemicalOxidizerCategory implements DisplayCategory<ChemicalOxidizerDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ChemicalOxidizerDisplay> getCategoryIdentifier() {
        return Categories.CHEMICAL_OXIDIZER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.CHEMICAL_OXIDIZER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.CHEMICAL_OXIDIZER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(ChemicalOxidizerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addInputSlotElement(widgets, adjustedInputPoint(bounds), EntryIngredients.ofItemStacks(display.recipe().getInput().getRepresentations()), GuiElements.INPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - offset - innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_NORMAL_RIGHT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), MekanismEntryTypes.gases(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ChemicalOxidizerDisplay display) {
        return 96;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
