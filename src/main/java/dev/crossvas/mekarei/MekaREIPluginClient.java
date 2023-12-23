package dev.crossvas.mekarei;

import dev.architectury.fluid.FluidStack;
import dev.crossvas.mekarei.categories.*;
import dev.crossvas.mekarei.displays.*;
import dev.crossvas.mekarei.handlers.ExclusionZoneHandler;
import dev.crossvas.mekarei.handlers.FormulaicAssemblicatorTransferHandler;
import dev.crossvas.mekarei.handlers.QIODashboardTransferHandler;
import dev.crossvas.mekarei.utils.Categories;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import mekanism.api.MekanismAPI;
import mekanism.client.gui.GuiMekanism;
import mekanism.common.content.blocktype.FactoryType;
import mekanism.common.inventory.container.entity.robit.RobitContainer;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.registration.impl.FluidDeferredRegister;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import mekanism.common.tier.FactoryTier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

@REIPluginClient
public class MekaREIPluginClient implements REIClientPlugin {

    @Override
    public void registerEntries(EntryRegistry registry) {
        MekanismAPI.infuseTypeRegistry().forEach(infusion -> {
            if (!infusion.isEmptyType()) {
                registry.addEntry(MekanismEntryTypes.infusion(infusion.getStack(1000)));
            }
        });
        MekanismAPI.gasRegistry().forEach(gas -> {
            if (!gas.isEmptyType()) {
                registry.addEntry(MekanismEntryTypes.gas(gas.getStack(1000)));
            }
        });
        MekanismAPI.pigmentRegistry().forEach(pigment -> {
            if (!pigment.isEmptyType()) {
                registry.addEntry(MekanismEntryTypes.pigment(pigment.getStack(1000)));
            }
        });
        MekanismAPI.slurryRegistry().forEach(slurry -> {
            if (!slurry.isEmptyType()) {
                registry.addEntry(MekanismEntryTypes.slurry(slurry.getStack(1000)));
            }
        });

        ForgeRegistries.FLUIDS.forEach(fluid -> {
            if (fluid.isSource(fluid.defaultFluidState()) && fluid.getFluidType() instanceof FluidDeferredRegister.MekanismFluidType) {
                registry.removeEntry(EntryStacks.of(fluid));
                FluidStack fluidStack = FluidStack.create(fluid, FluidStack.bucketAmount());
                registry.addEntry(EntryStack.of(VanillaEntryTypes.FLUID, fluidStack));
            }
        });
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        addWorkstations(registry, BuiltinPlugin.SMELTING,
                MekanismBlocks.ENERGIZED_SMELTER.getBlock(),
                FactoryType.SMELTING
        );
        addWorkstations(registry, BuiltinPlugin.CRAFTING, MekanismBlocks.FORMULAIC_ASSEMBLICATOR.getBlock());
        addWorkstations(registry, BuiltinPlugin.CRAFTING, MekanismItems.ROBIT.asItem());
        addWorkstations(registry, BuiltinPlugin.SMELTING, MekanismItems.ROBIT.asItem());
        addCat(registry, Categories.ANTIPROTONIC_SYNTH, new AntiprotonicSynthesizerCategory(), MekanismBlocks.ANTIPROTONIC_NUCLEOSYNTHESIZER.getBlock());
        addCat(registry, Categories.METAL_INFUSING, new MetallurgicInfuserCategory(),
                MekanismBlocks.METALLURGIC_INFUSER.getBlock(),
                FactoryType.INFUSING
        );
        addCat(registry, Categories.CRYSTALLIZER, new CrystallizerCategory(),
                MekanismBlocks.CHEMICAL_CRYSTALLIZER.getBlock());
        addCat(registry, Categories.CHEMICAL_DISSOLUTION, new ChemicalDissolutionCategory(),
                MekanismBlocks.CHEMICAL_DISSOLUTION_CHAMBER.getBlock());
        addCat(registry, Categories.CHEMICAL_INFUSER, new ChemicalInfuserCategory(),
                MekanismBlocks.CHEMICAL_INFUSER.getBlock());
        addCat(registry, Categories.CHEMICAL_INJECTOR, new ChemicalInjectorCategory(),
                MekanismBlocks.CHEMICAL_INJECTION_CHAMBER.getBlock(),
                FactoryType.INJECTING);
        addCat(registry, Categories.CHEMICAL_OXIDIZER, new ChemicalOxidizerCategory(),
                MekanismBlocks.CHEMICAL_OXIDIZER.getBlock());
        addCat(registry, Categories.CHEMICAL_WASHER, new ChemicalWasherCategory(),
                MekanismBlocks.CHEMICAL_WASHER.getBlock());
        addCat(registry, Categories.COMBINER, new CombinerCategory(),
                MekanismBlocks.COMBINER.getBlock(),
                FactoryType.COMBINING);
        addCat(registry, Categories.CONDENSENTRATOR, CondensentratorCategory.CONDENSENTRATOR,
                MekanismBlocks.ROTARY_CONDENSENTRATOR.getBlock());
        addCat(registry, Categories.CRUSHER, new CrusherCategory(),
                MekanismBlocks.CRUSHER.getBlock(),
                FactoryType.CRUSHING);
        addCat(registry, Categories.DECONDENSENTRATOR, CondensentratorCategory.DECONDENSENTRATOR,
                MekanismBlocks.ROTARY_CONDENSENTRATOR.getBlock());
        addCat(registry, Categories.SEPARATOR, new ElectrolyticSeparatorCategory(),
                MekanismBlocks.ELECTROLYTIC_SEPARATOR.getBlock());
        addCat(registry, Categories.ENRICHMENT, new EnricherCategory(),
                MekanismBlocks.ENRICHMENT_CHAMBER.getBlock(),
                FactoryType.ENRICHING);
        addCat(registry, Categories.ISOTOPIC_CENTRIFUGE, new IsotopicCentrifugeCategory(), MekanismBlocks.ISOTOPIC_CENTRIFUGE.getBlock());
        addCat(registry, Categories.NUTRITIONAL_LIQUIFICATION, new NutritionalLiquificationCategory(), MekanismBlocks.NUTRITIONAL_LIQUIFIER.getBlock());
        addCat(registry, Categories.OSMIUM_COMPRESSOR, new OsmiumCompressorCategory(), MekanismBlocks.OSMIUM_COMPRESSOR.getBlock(), FactoryType.COMPRESSING);
        addCat(registry, Categories.PAINTING, new PaintingCategory(), MekanismBlocks.PAINTING_MACHINE.getBlock());
        addCat(registry, Categories.PIGMENT_EXTRACTOR, new PigmentExtractorCategory(), MekanismBlocks.PIGMENT_EXTRACTOR.getBlock());
        addCat(registry, Categories.PIGMENT_MIXER, new PigmentMixerCategory(), MekanismBlocks.PIGMENT_MIXER.getBlock());
        addCat(registry, Categories.SAWMILL, new SawmillCategory(), MekanismBlocks.PRECISION_SAWMILL.getBlock(), FactoryType.SAWING);
        addCat(registry, Categories.PRESSURIZED_CHAMBER, new PressurizedChamberCategory(), MekanismBlocks.PRESSURIZED_REACTION_CHAMBER.getBlock());
        addCat(registry, Categories.PURIFICATION_CHAMBER, new PurificationChamberCategory(), MekanismBlocks.PURIFICATION_CHAMBER.getBlock(), FactoryType.PURIFYING);
        addCat(registry, Categories.SOLAR_NEUTRON, new SolarNeutronCategory(), MekanismBlocks.SOLAR_NEUTRON_ACTIVATOR.getBlock());
        addCat(registry, Categories.SPS, new SPSCategory(), MekanismBlocks.SPS_CASING.getBlock(), MekanismBlocks.SPS_PORT.getBlock(), MekanismBlocks.SUPERCHARGED_COIL.getBlock());
        addCat(registry, Categories.EVAPORATION_TOWER, new ThermalEvaporationCategory(), MekanismBlocks.THERMAL_EVAPORATION_CONTROLLER.getBlock(), MekanismBlocks.THERMAL_EVAPORATION_VALVE.getBlock(), MekanismBlocks.THERMAL_EVAPORATION_BLOCK.getBlock());

        addCat(registry, Categories.ITEM_TO_ENERGY, new ItemStackToEnergyCategory(),
                MekanismBlocks.BASIC_ENERGY_CUBE.getBlock(),
                MekanismBlocks.ADVANCED_ENERGY_CUBE.getBlock(),
                MekanismBlocks.ELITE_ENERGY_CUBE.getBlock(),
                MekanismBlocks.ULTIMATE_ENERGY_CUBE.getBlock());
        addCat(registry, Categories.ITEM_INFUSING, new ItemStackToInfusingCategory(),
                MekanismBlocks.METALLURGIC_INFUSER.getBlock(),
                FactoryType.INFUSING
        );
        addCat(registry, Categories.THERMOELECTRIC_BOILER, new ThermoelectricBoilerCategory(),
                MekanismBlocks.BOILER_CASING.getBlock(),
                MekanismBlocks.BOILER_VALVE.getBlock(),
                MekanismBlocks.PRESSURE_DISPERSER.getBlock(),
                MekanismBlocks.SUPERHEATING_ELEMENT.getBlock());
        registry.add(new ItemStackToGasCategory());
        addWorkstations(registry, Categories.ITEM_TO_GAS,
                MekanismBlocks.CHEMICAL_DISSOLUTION_CHAMBER.getBlock(),
                MekanismBlocks.ANTIPROTONIC_NUCLEOSYNTHESIZER.getBlock());
        addWorkstations(registry, Categories.ITEM_TO_GAS, MekanismBlocks.PURIFICATION_CHAMBER.getBlock(), FactoryType.PURIFYING);
        addWorkstations(registry, Categories.ITEM_TO_GAS, MekanismBlocks.OSMIUM_COMPRESSOR.getBlock(), FactoryType.COMPRESSING);
        addWorkstations(registry, Categories.ITEM_TO_GAS, MekanismBlocks.CHEMICAL_INJECTION_CHAMBER.getBlock(), FactoryType.INJECTING);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        RecipeManager RECIPES = Minecraft.getInstance().level.getRecipeManager();
        RECIPES.getAllRecipesFor(MekanismRecipeType.METALLURGIC_INFUSING.get()).forEach(recipe -> registry.add(new MetallurgicInfuserDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.INFUSION_CONVERSION.get()).forEach(recipe -> registry.add(new ItemStackToInfusingDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.NUCLEOSYNTHESIZING.get()).forEach(recipe -> registry.add(new AntiprotonicSynthesizerDisplay(recipe)));
        ThermoelectricBoilerCategory.getBoilerRecipes().forEach(recipe -> registry.add(new ThermoelectricBoilerDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.CRYSTALLIZING.get()).forEach(recipe -> registry.add(new CrystallizerDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.DISSOLUTION.get()).forEach(recipe -> registry.add(new ChemicalDissolutionDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.CHEMICAL_INFUSING.get()).forEach(recipe -> registry.add(new ChemicalInfuserDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.INJECTING.get()).forEach(recipe -> registry.add(new ChemicalInjectorDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.OXIDIZING.get()).forEach(recipe -> registry.add(new ChemicalOxidizerDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.WASHING.get()).forEach(recipe -> registry.add(new ChemicalWasherDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.COMBINING.get()).forEach(recipe -> registry.add(new CombinerDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.ROTARY.get()).forEach(recipe -> registry.add(new CondensentratorDisplay(recipe, true)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.ROTARY.get()).forEach(recipe -> registry.add(new CondensentratorDisplay(recipe, false)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.CRUSHING.get()).forEach(recipe -> registry.add(new CrusherDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.SEPARATING.get()).forEach(recipe -> registry.add(new ElectrolyticSeparatorDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.ENERGY_CONVERSION.get()).forEach(recipe -> registry.add(new ItemStackToEnergyDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.ENRICHING.get()).forEach(recipe -> registry.add(new EnricherDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.GAS_CONVERSION.get()).forEach(recipe -> registry.add(new ItemStackToGasDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.CENTRIFUGING.get()).forEach(recipe -> registry.add(new IsotopicCentrifugeDisplay(recipe)));
        NutritionalLiquificationCategory.getRecipes().forEach(recipe -> registry.add(new NutritionalLiquificationDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.COMPRESSING.get()).forEach(recipe -> registry.add(new OsmiumCompressorDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.PAINTING.get()).forEach(recipe -> registry.add(new PaintingMachineDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.PIGMENT_EXTRACTING.get()).forEach(recipe -> registry.add(new PigmentExtractorDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.PIGMENT_MIXING.get()).forEach(recipe -> registry.add(new PigmentMixerDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.SAWING.get()).forEach(recipe -> registry.add(new SawmillDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.REACTION.get()).forEach(recipe -> registry.add(new PressurizedChamberDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.PURIFYING.get()).forEach(recipe -> registry.add(new PurificationChamberDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.ACTIVATING.get()).forEach(recipe -> registry.add(new SolarNeutronDisplay(recipe)));
        SPSCategory.getSPSRecipes().forEach(recipe -> registry.add(new SPSDisplay(recipe)));
        RECIPES.getAllRecipesFor(MekanismRecipeType.EVAPORATING.get()).forEach(recipe -> registry.add(new ThermalEvaporationDisplay(recipe)));
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        registry.register(new FormulaicAssemblicatorTransferHandler());
        registry.register(new QIODashboardTransferHandler());
        SimpleTransferHandler simpleTransferHandler = SimpleTransferHandler.create(RobitContainer.class, BuiltinPlugin.CRAFTING, new SimpleTransferHandler.IntRange(1, 10));
        registry.register(simpleTransferHandler);
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        zones.register(GuiMekanism.class, new ExclusionZoneHandler());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addCat(CategoryRegistry r, CategoryIdentifier id, DisplayCategory displayCat, Block main, FactoryType factory) {
        r.add(displayCat);
        r.addWorkstations(id, EntryIngredients.of(main));
        FactoryTier[] tiers = FactoryTier.values();
        for (FactoryTier tier : tiers) {
            r.addWorkstations(id, EntryStacks.of(MekanismBlocks.getFactory(tier, factory)));
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addCat(CategoryRegistry r, CategoryIdentifier id, DisplayCategory displayCat, Block... mains) {
        r.add(displayCat);
        Arrays.stream(mains).forEach(main -> r.addWorkstations(id, EntryIngredients.of(main)));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addWorkstations(CategoryRegistry r, CategoryIdentifier id, Block main, FactoryType factory) {
        r.addWorkstations(id, EntryIngredients.of(main));
        FactoryTier[] tiers = FactoryTier.values();
        for (FactoryTier tier : tiers) {
            r.addWorkstations(id, EntryStacks.of(MekanismBlocks.getFactory(tier, factory)));
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addWorkstations(CategoryRegistry r, CategoryIdentifier id, Block... stations) {
        Arrays.stream(stations).forEach(station -> r.addWorkstations(id, EntryStacks.of(station)));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void addWorkstations(CategoryRegistry r, CategoryIdentifier id, Item... stations) {
        Arrays.stream(stations).forEach(station -> r.addWorkstations(id, EntryStacks.of(station)));
    }
}
