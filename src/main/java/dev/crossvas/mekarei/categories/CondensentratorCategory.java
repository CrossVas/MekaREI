package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.displays.CondensentratorDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.common.MekanismLang;
import mekanism.common.registries.MekanismBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CondensentratorCategory implements IGuiHelper {

    public static final CondensentratorCategory THIS = new CondensentratorCategory();

    public static final CondensentratingCommonCategory CONDENSENTRATOR = new CondensentratingCommonCategory(Categories.CONDENSENTRATOR, MekanismBlocks.ROTARY_CONDENSENTRATOR.getBlock(), true);
    public static final CondensentratingCommonCategory DECONDENSENTRATOR = new CondensentratingCommonCategory(Categories.DECONDENSENTRATOR, MekanismBlocks.ROTARY_CONDENSENTRATOR.getBlock(), false);

    public List<Widget> getDisplay(BasicDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        boolean gasToFluid = ((CondensentratorDisplay)display).gasToFluid();
        GuiHelper.createRecipeBase(widgets, bounds);
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), MekanismEntryTypes.gases(((CondensentratorDisplay)display).recipe().getGasInput().getRepresentations()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addTank(widgets, point(bounds.getMaxX() - offset - slotSize, bounds.getMinY() + offset), VanillaEntryHelper.fluids(((CondensentratorDisplay)display).recipe().getFluidInput().getRepresentations()), GuiElements.TANK_DEFAULT, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - slotSize - 2, getProgressBarY(bounds)), 2000, gasToFluid ? GuiProgress.ARROW_LARGE_RIGHT : GuiProgress.ARROW_LARGE_LEFT);
        return widgets;
    }

    public static class CondensentratingCommonCategory implements DisplayCategory<CondensentratorDisplay> {

        CategoryIdentifier<CondensentratorDisplay> ID;
        Block STATION;
        boolean GAS_TO_FLUID;

        public CondensentratingCommonCategory(CategoryIdentifier<CondensentratorDisplay> id, Block station, boolean gasToFluid) {
            this.ID = id;
            this.STATION = station;
            this.GAS_TO_FLUID = gasToFluid;
        }

        @Override
        public CategoryIdentifier<CondensentratorDisplay> getCategoryIdentifier() {
            return this.ID;
        }

        @Override
        public Component getTitle() {
            return GAS_TO_FLUID ? MekanismLang.CONDENSENTRATING.translate() : MekanismLang.DECONDENSENTRATING.translate();
        }

        @Override
        public Renderer getIcon() {
            return EntryStacks.of(this.STATION);
        }

        @Override
        public List<Widget> setupDisplay(CondensentratorDisplay display, Rectangle bounds) {
            return CondensentratorCategory.THIS.getDisplay(display, bounds);
        }

        @Override
        public int getDisplayWidth(CondensentratorDisplay display) {
            return 120;
        }

        @Override
        public int getDisplayHeight() {
            return 70;
        }
    }
}
