package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.ChemicalWasherDisplay;
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

public class ChemicalWasherCategory implements DisplayCategory<ChemicalWasherDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends ChemicalWasherDisplay> getCategoryIdentifier() {
        return Categories.CHEMICAL_WASHER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.CHEMICAL_WASHER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.CHEMICAL_WASHER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(ChemicalWasherDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), VanillaEntryHelper.fluids(display.recipe().getFluidInput().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, bounds.getMinY() + offset), MekanismEntryTypes.slurries(display.recipe().getChemicalInput().getRepresentations()), GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), MekanismEntryTypes.slurries(display.recipe().getOutputDefinition()), GuiElements.TANK_DEFAULT_BLUE, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds), getProgressBarY(bounds)), 2000, GuiProgress.ARROW_NORMAL_RIGHT);
        return widgets;
    }

    @Override
    public int getDisplayWidth(ChemicalWasherDisplay display) {
        return 128;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
