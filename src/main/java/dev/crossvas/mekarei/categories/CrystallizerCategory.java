package dev.crossvas.mekarei.categories;

import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.displays.CrystallizerDisplay;
import dev.crossvas.mekarei.utils.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.tags.MekanismTags;
import mekanism.common.tags.TagUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrystallizerCategory implements DisplayCategory<CrystallizerDisplay>, IGuiHelper {

    @Override
    public CategoryIdentifier<? extends CrystallizerDisplay> getCategoryIdentifier() {
        return Categories.CRYSTALLIZER;
    }

    @Override
    public Component getTitle() {
        return MekanismBlocks.CHEMICAL_CRYSTALLIZER.getTextComponent();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MekanismBlocks.CHEMICAL_CRYSTALLIZER.getBlock());
    }

    @Override
    public List<Widget> setupDisplay(CrystallizerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ObjectArrayList<>();
        GuiHelper.createRecipeBase(widgets, bounds);
        EntryIngredient liquidEntry;
        Set<ITag<Item>> mappedTags = new HashSet<>();

        if (display.recipe().getInput() instanceof ChemicalStackIngredient.GasStackIngredient) {
            liquidEntry = MekanismEntryTypes.gases(display.gases());
        } else if (display.recipe().getInput() instanceof ChemicalStackIngredient.InfusionStackIngredient) {
            liquidEntry = MekanismEntryTypes.infusions(display.infusions());
        } else if (display.recipe().getInput() instanceof ChemicalStackIngredient.PigmentStackIngredient) {
            liquidEntry = MekanismEntryTypes.pigments(display.pigments());
        } else if (display.recipe().getInput() instanceof ChemicalStackIngredient.SlurryStackIngredient slurryStackIngredient) {
            liquidEntry = MekanismEntryTypes.slurries(display.slurry());
            for (SlurryStack slurryStack : slurryStackIngredient.getRepresentations()) {
                Slurry slurryType = slurryStack.getType();
                if (!MekanismTags.Slurries.DIRTY_LOOKUP.contains(slurryType)) {
                    TagKey<Item> oreTag = slurryType.getOreTag();
                    if (oreTag != null) {
                        mappedTags.add(TagUtils.tag(ForgeRegistries.ITEMS, oreTag));
                    }
                }
            }

            if (mappedTags.size() == 1) {
                mappedTags.stream().findFirst().ifPresent((tag) -> {
                    GuiHelper.addOutputSlotElement(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, getCenterY(bounds)), EntryIngredients.ofItemStacks(tag.stream().map(ItemStack::new).toList()), GuiElements.DEFAULT);
                });
            }
        } else {
            liquidEntry = EntryIngredient.empty();
        }
        GuiHelper.addTank(widgets, point(bounds.getMinX() + offset, bounds.getMinY() + offset), liquidEntry, GuiElements.TANK_DEFAULT_RED, GuiElements.TANK_OVERLAY_DEFAULT);
        GuiHelper.addLabelLeft(widgets, point(bounds.getMinX() + offset + slotSize + innerOffset, bounds.getMaxY() - offset - lineHeight), liquidEntry.get(0).asFormattedText().copy().withStyle(ChatFormatting.BLACK));
        GuiHelper.addOutputSlotElement(widgets, adjustedOutputPoint(bounds), EntryIngredients.ofItemStacks(display.inputs()), GuiElements.OUTPUT);
        GuiHelper.addProgressBar(widgets, point(getProgressBarX(bounds) - 2 * offset - innerOffset, getProgressBarY(bounds)), 2000, GuiProgress.ARROW_LARGE_RIGHT);

        return widgets;
    }

    @Override
    public int getDisplayWidth(CrystallizerDisplay display) {
        return 140;
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
