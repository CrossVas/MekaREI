package dev.crossvas.mekarei.handlers;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.container.tile.FormulaicAssemblicatorContainer;
import mekanism.common.inventory.slot.FormulaicCraftingSlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import net.minecraft.world.inventory.Slot;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class FormulaicAssemblicatorTransferHandler implements SimpleTransferHandler {

    @Override
    public Iterable<SlotAccessor> getInputSlots(Context context) {
        Display display = context.getDisplay();
        List<Slot> slots = new ObjectArrayList<>();
        if (display.getCategoryIdentifier() == BuiltinPlugin.CRAFTING) {
            if (context.getMenu() instanceof FormulaicAssemblicatorContainer container) {
                for (InventoryContainerSlot slot : container.getInventoryContainerSlots()) {
                    if (slot.getInventorySlot() instanceof FormulaicCraftingSlot) {
                        slots.add(slot);
                    }
                }
            }
        }
        return Iterables.transform(slots, SlotAccessor::fromSlot);
    }

    @Override
    public Iterable<SlotAccessor> getInventorySlots(Context context) {
        Display display = context.getDisplay();
        List<Slot> slots = new ObjectArrayList<>();
        if (display.getCategoryIdentifier() == BuiltinPlugin.CRAFTING) {
            if (context.getMenu() instanceof FormulaicAssemblicatorContainer container) {
                slots.addAll(container.getMainInventorySlots());
                slots.addAll(container.getHotBarSlots());
                for (InventoryContainerSlot slot : container.getInventoryContainerSlots()) {
                    if (slot.getInventorySlot() instanceof InputInventorySlot) {
                        slots.add(slot);
                    }
                }
            }
        }
        return Iterables.transform(slots, SlotAccessor::fromSlot);
    }

    @Override
    public Result handle(Context context) {
        Display display = context.getDisplay();
        if (display.getCategoryIdentifier() == BuiltinPlugin.CRAFTING) {
            if (context.getMenu() instanceof FormulaicAssemblicatorContainer) {
                // default handler
                return this.handleSimpleTransfer(context, this.getMissingInputRenderer(), this.getInputsIndexed(context), this.getInputSlots(context), this.getInventorySlots(context));
            }
        }
        // disable handling
        return Result.createNotApplicable();
    }
}
