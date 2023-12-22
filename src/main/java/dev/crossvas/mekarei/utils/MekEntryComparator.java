package dev.crossvas.mekarei.utils;

import me.shedaniel.rei.api.common.entry.comparison.EntryComparator;
import me.shedaniel.rei.impl.Internals;
import me.shedaniel.rei.impl.common.entry.comparison.EntryComparatorRegistryImpl;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

@SuppressWarnings({"UnstableApiUsage", "unchecked"})
public class MekEntryComparator<STACK extends ChemicalStack<?>, CHEMICAL extends Chemical<?>> extends EntryComparatorRegistryImpl<STACK, CHEMICAL> {

    final EntryComparator<STACK> chemicalTag = chemicalTag();

    final EntryComparator<STACK> defaultComparator = (context, stack) -> context.isExact() ? this.chemicalTag.hash(context, stack) : 1L;

    @Override
    public CHEMICAL getEntry(STACK t) {
        return (CHEMICAL) t.getType();
    }

    @Override
    public EntryComparator<STACK> defaultComparator() {
        return this.defaultComparator;
    }

    private EntryComparator<STACK> chemicalTag() {
        EntryComparator<Tag> tagHashProvider = getTag("amount");
        return (context, stack) -> {
            CompoundTag tag = stack.write(new CompoundTag());
            return tag.isEmpty() ? 0L : tagHashProvider.hash(context, tag);
        };
    }

    private EntryComparator<Tag> getTag(String... ignoredKeys) {
        return Internals.getNbtHasher(ignoredKeys);
    }
}
