package dev.crossvas.mekarei.handlers;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.container.tile.FormulaicAssemblicatorContainer;
import mekanism.common.inventory.slot.FormulaicCraftingSlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class FormulaicAssemblicatorTransferHandler implements SimpleTransferHandler {

    @Override
    public Iterable<SlotAccessor> getInputSlots(Context context) {
        List<Slot> slots = new ObjectArrayList<>();
        if (context.getMenu() instanceof FormulaicAssemblicatorContainer container) {
            for (InventoryContainerSlot slot : container.getInventoryContainerSlots()) {
                if (slot.getInventorySlot() instanceof FormulaicCraftingSlot) {
                    slots.add(slot);
                }
            }
        }
        return Iterables.transform(slots, SlotAccessor::fromSlot);
    }

    @Override
    public Iterable<SlotAccessor> getInventorySlots(Context context) {
        List<Slot> slots = new ObjectArrayList<>();
        if (context.getMenu() instanceof FormulaicAssemblicatorContainer container) {{
            slots.addAll(container.getMainInventorySlots());
            slots.addAll(container.getHotBarSlots());
            for (InventoryContainerSlot slot : container.getInventoryContainerSlots()) {
                if (slot.getInventorySlot() instanceof InputInventorySlot) {
                    slots.add(slot);
                }
            }
        }}
        return Iterables.transform(slots, SlotAccessor::fromSlot);
    }
}
