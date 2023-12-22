package dev.crossvas.mekarei;

import dev.crossvas.mekarei.entries.GasEntryType;
import dev.crossvas.mekarei.entries.InfusionEntryType;
import dev.crossvas.mekarei.entries.PigmentEntryType;
import dev.crossvas.mekarei.entries.SlurryEntryType;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;

@REIPluginCommon
public class MekaREIPluginCommon implements REIServerPlugin {

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry) {
        registry.register(MekanismEntryTypes.INFUSION_ENTRY, new InfusionEntryType());
        registry.register(MekanismEntryTypes.GAS_ENTRY, new GasEntryType());
        registry.register(MekanismEntryTypes.PIGMENT_ENTRY, new PigmentEntryType());
        registry.register(MekanismEntryTypes.SLURRY_ENTRY, new SlurryEntryType());
    }
}
