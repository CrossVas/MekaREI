package dev.crossvas.mekarei.handlers;

import dev.crossvas.mekarei.utils.VanillaEntryHelper;
import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.bytes.*;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandler;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.math.MathUtils;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.content.qio.QIOCraftingTransferHelper;
import mekanism.common.content.qio.QIOCraftingWindow;
import mekanism.common.content.qio.QIOFrequency;
import mekanism.common.inventory.container.QIOItemViewerContainer;
import mekanism.common.inventory.container.slot.HotBarSlot;
import mekanism.common.inventory.container.slot.MainInventorySlot;
import mekanism.common.inventory.slot.CraftingWindowInventorySlot;
import mekanism.common.lib.inventory.HashedItem;
import mekanism.common.network.to_server.PacketQIOFillCraftingWindow;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StackUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class QIODashboardTransferHandler implements TransferHandler {

    private final Function<HashedItem, ItemStack> recipeUUIDFunction;

    public QIODashboardTransferHandler() {
        this.recipeUUIDFunction = hashed -> ((HashedItem) hashed).getInternalStack();
    }

    @Override
    public Result handle(Context context) {
        Display display = context.getDisplay();
        if (display.getCategoryIdentifier() == BuiltinPlugin.CRAFTING) {
            if (display instanceof DefaultCraftingDisplay<?> craftingDisplay) {
                if (craftingDisplay.getOptionalRecipe().isPresent()) {
                    CraftingRecipe recipe = (CraftingRecipe) craftingDisplay.getOptionalRecipe().get();
                    if (context.getMenu() instanceof QIOItemViewerContainer container) {
                        byte selectedCraftingGrid = container.getSelectedCraftingGrid();
                        if (selectedCraftingGrid >= 0) {
                            return this.transferRecipe(craftingDisplay, container, recipe, context.getMinecraft().player, context.isStackedCrafting(), context.isActuallyCrafting());
                        }
                    }
                }
            }
        }
        return Result.createNotApplicable();
    }

    // Copied from JEI
    // TODO: Might have to revisit this, but for now it works
    public Result transferRecipe(Display display, QIOItemViewerContainer container, CraftingRecipe recipe, Player player, boolean maxTransfer, boolean doTransfer) {
        byte selectedCraftingGrid = container.getSelectedCraftingGrid();
        if (selectedCraftingGrid == -1) {
            return Result.createNotApplicable();
        }
        QIOCraftingWindow craftingWindow = container.getCraftingWindow(selectedCraftingGrid);
        byte nonEmptyCraftingSlots = 0;
        if (!doTransfer) {
            CraftingContainer dummy = MekanismUtils.getDummyCraftingInv();
            for (int slot = 0; slot < 9; slot++) {
                CraftingWindowInventorySlot inputSlot = craftingWindow.getInputSlot(slot);
                if (!inputSlot.isEmpty()) {
                    dummy.setItem(slot, StackUtils.size(inputSlot.getStack(), 1));
                    nonEmptyCraftingSlots++;
                }
            }
            if (recipe.matches(dummy, player.level)) {
                return Result.createNotApplicable();
            }
        }
        final List<EntryIngredient> ingredients = ((DefaultCraftingDisplay<?>) display).getOrganisedInputEntries(3, 3);
        int maxInputCount = ingredients.size();
        if (maxInputCount > 9) {
            Mekanism.logger.warn("Error evaluating recipe transfer handler for recipe: {}, had more than 9 inputs: {}", recipe.getId(), maxInputCount);
            return Result.createNotApplicable();
        }
        int inputCount = 0;
        record TrackedIngredients(EntryIngredient view, Set<HashedItem> representations) {}
        Byte2ObjectMap<TrackedIngredients> hashedIngredients = new Byte2ObjectArrayMap<>(maxInputCount);
        for (int index = 0; index < maxInputCount; index++) {
            EntryIngredient slotIngredient = ingredients.get(index);
            List<ItemStack> validIngredients = VanillaEntryHelper.convertIngredientToItemStacks(slotIngredient);
            if (!validIngredients.isEmpty()) {
                inputCount++;
                LinkedHashSet<HashedItem> representations = new LinkedHashSet<>(validIngredients.size());
                ItemStack displayed = validIngredients.get(0);
                if (!displayed.isEmpty()) {
                    representations.add(HashedItem.raw(displayed));
                }
                for (ItemStack validIngredient : validIngredients) {
                    if (!validIngredient.isEmpty()) {
                        representations.add(HashedItem.raw(validIngredient));
                    }
                }
                hashedIngredients.put((byte) index, new TrackedIngredients(slotIngredient, representations));
            }
        }

        QIOCraftingTransferHelper qioTransferHelper = container.getTransferHelper(player, craftingWindow);
        if (qioTransferHelper.isInvalid()) {
            Mekanism.logger.warn("Error initializing QIO transfer handler for crafting window: {}", selectedCraftingGrid);
            return Result.createNotApplicable();
        }

        Map<HashedItem, ByteList> matchedItems = new HashMap<>(inputCount);
        ByteSet missingSlots = new ByteArraySet(inputCount);
        for (Byte2ObjectMap.Entry<TrackedIngredients> entry : hashedIngredients.byte2ObjectEntrySet()) {
            boolean matchFound = false;
            for (HashedItem validInput : entry.getValue().representations()) {
                QIOCraftingTransferHelper.HashedItemSource source = qioTransferHelper.getSource(validInput);
                if (source != null && source.hasMoreRemaining()) {
                    source.matchFound();
                    matchFound = true;
                    matchedItems.computeIfAbsent(validInput, item -> new ByteArrayList()).add(entry.getByteKey());
                    break;
                }
            }
            if (!matchFound) {
                missingSlots.add(entry.getByteKey());
            }
        }
        if (!missingSlots.isEmpty()) {
            Map<HashedItem, ItemStack> cachedIngredientUUIDs = new HashMap<>();
            for (Map.Entry<HashedItem, QIOCraftingTransferHelper.HashedItemSource> entry : qioTransferHelper.reverseLookup.entrySet()) {
                QIOCraftingTransferHelper.HashedItemSource source = entry.getValue();
                if (source.hasMoreRemaining()) {
                    HashedItem storedHashedItem = entry.getKey();
                    Item storedItemType = storedHashedItem.getItem();
                    ItemStack storedItem = ItemStack.EMPTY;
                    for (ByteIterator missingIterator = missingSlots.iterator(); missingIterator.hasNext();) {
                        byte index = missingIterator.nextByte();
                        for (HashedItem validIngredient : hashedIngredients.get(index).representations()) {
                            if (storedItemType == validIngredient.getItem()) {
                                if (storedItem.isEmpty()) {
                                    storedItem = new ItemStack(storedItemType.asItem());
                                }
                                ItemStack ingredient = cachedIngredientUUIDs.computeIfAbsent(validIngredient, recipeUUIDFunction);
                                if (storedItem.equals(ingredient)) {
                                    source.matchFound();
                                    missingIterator.remove();
                                    matchedItems.computeIfAbsent(storedHashedItem, item -> new ByteArrayList()).add(index);
                                    break;
                                }
                            }
                        }
                        if (!source.hasMoreRemaining()) {
                            break;
                        }
                    }
                    if (missingSlots.isEmpty()) {
                        break;
                    }
                }
            }
            if (!missingSlots.isEmpty()) {
                List<EntryIngredient> missing = missingSlots.intStream().mapToObj(slot -> hashedIngredients.get((byte) slot).view()).toList();
                return Result.createNotApplicable().tooltipMissing(missing);
            }
        }
        if (doTransfer || (nonEmptyCraftingSlots > 0 && nonEmptyCraftingSlots >= qioTransferHelper.getEmptyInventorySlots())) {
            int toTransfer;
            if (maxTransfer) {
                long maxToTransfer = Long.MAX_VALUE;
                for (Map.Entry<HashedItem, ByteList> entry : matchedItems.entrySet()) {
                    HashedItem hashedItem = entry.getKey();
                    QIOCraftingTransferHelper.HashedItemSource source = qioTransferHelper.getSource(hashedItem);
                    if (source == null) {
                        return invalidSource(hashedItem);
                    }
                    int maxStack = hashedItem.getMaxStackSize();
                    long max = maxStack == 1 ? maxToTransfer : Math.min(maxToTransfer, maxStack);
                    maxToTransfer = Math.min(max, source.getAvailable() / entry.getValue().size());
                }
                toTransfer = MathUtils.clampToInt(maxToTransfer);
            } else {
                toTransfer = 1;
            }
            QIOFrequency frequency = container.getFrequency();
            Byte2ObjectMap<List<QIOCraftingTransferHelper.SingularHashedItemSource>> sources = new Byte2ObjectArrayMap<>(inputCount);
            Map<QIOCraftingTransferHelper.HashedItemSource, List<List<QIOCraftingTransferHelper.SingularHashedItemSource>>> shuffleLookup = frequency == null ? Collections.emptyMap() : new HashMap<>(inputCount);
            for (Map.Entry<HashedItem, ByteList> entry : matchedItems.entrySet()) {
                HashedItem hashedItem = entry.getKey();
                QIOCraftingTransferHelper.HashedItemSource source = qioTransferHelper.getSource(hashedItem);
                if (source == null) {
                    return invalidSource(hashedItem);
                }
                int transferAmount = Math.min(toTransfer, hashedItem.getMaxStackSize());
                for (byte slot : entry.getValue()) {
                    List<QIOCraftingTransferHelper.SingularHashedItemSource> actualSources = source.use(transferAmount);
                    if (actualSources.isEmpty()) {
                        return invalidSource(hashedItem);
                    }
                    sources.put(slot, actualSources);
                    if (frequency != null) {
                        int elements = entry.getValue().size();
                        if (elements == 1) {
                            shuffleLookup.put(source, Collections.singletonList(actualSources));
                        } else {
                            shuffleLookup.computeIfAbsent(source, s -> new ArrayList<>(elements)).add(actualSources);
                        }
                    }
                }
            }
            if (!hasRoomToShuffle(qioTransferHelper, frequency, craftingWindow, container.getHotBarSlots(), container.getMainInventorySlots(), shuffleLookup)) {
                return Result.createFailed(MekanismLang.JEI_INVENTORY_FULL.translate());
            }
            if (doTransfer) {
                Mekanism.packetHandler().sendToServer(new PacketQIOFillCraftingWindow(recipe.getId(), maxTransfer, sources));
                return Result.createSuccessful();
            }
        }
        return Result.createSuccessful();
    }

    private static boolean hasRoomToShuffle(QIOCraftingTransferHelper qioTransferHelper, @Nullable QIOFrequency frequency, QIOCraftingWindow craftingWindow,
                                            List<HotBarSlot> hotBarSlots, List<MainInventorySlot> mainInventorySlots, Map<QIOCraftingTransferHelper.HashedItemSource, List<List<QIOCraftingTransferHelper.SingularHashedItemSource>>> shuffleLookup) {
        Object2IntMap<HashedItem> leftOverInput = new Object2IntArrayMap<>(9);
        for (byte slotIndex = 0; slotIndex < 9; slotIndex++) {
            IInventorySlot slot = craftingWindow.getInputSlot(slotIndex);
            if (!slot.isEmpty()) {
                HashedItem type = HashedItem.raw(slot.getStack());
                QIOCraftingTransferHelper.HashedItemSource source = qioTransferHelper.getSource(type);
                if (source == null) {
                    return false;
                }
                int remaining = source.getSlotRemaining(slotIndex);
                if (remaining > 0) {
                    leftOverInput.mergeInt(type, remaining, Integer::sum);
                }
            }
        }
        if (!leftOverInput.isEmpty()) {
            QIOCraftingTransferHelper.BaseSimulatedInventory simulatedInventory = new QIOCraftingTransferHelper.BaseSimulatedInventory(hotBarSlots, mainInventorySlots) {
                @Override
                protected int getRemaining(int slot, ItemStack currentStored) {
                    QIOCraftingTransferHelper.HashedItemSource source = qioTransferHelper.getSource(HashedItem.raw(currentStored));
                    if (source == null) {
                        return currentStored.getCount();
                    }
                    return source.getSlotRemaining((byte) (slot + 9));
                }
            };
            Object2IntMap<HashedItem> stillLeftOver = simulatedInventory.shuffleInputs(leftOverInput, frequency != null);
            if (stillLeftOver == null) {
                return false;
            }
            if (!stillLeftOver.isEmpty() && frequency != null) {
                int availableItemTypes = frequency.getTotalItemTypeCapacity() - frequency.getTotalItemTypes(true);
                long availableItemSpace = frequency.getTotalItemCountCapacity() - frequency.getTotalItemCount();
                Object2BooleanMap<QIOCraftingTransferHelper.HashedItemSource> usedQIOSource = new Object2BooleanArrayMap<>(shuffleLookup.size());
                for (Map.Entry<QIOCraftingTransferHelper.HashedItemSource, List<List<QIOCraftingTransferHelper.SingularHashedItemSource>>> entry : shuffleLookup.entrySet()) {
                    QIOCraftingTransferHelper.HashedItemSource source = entry.getKey();
                    boolean usedQIO = false;
                    for (List<QIOCraftingTransferHelper.SingularHashedItemSource> usedSources : entry.getValue()) {
                        for (QIOCraftingTransferHelper.SingularHashedItemSource usedSource : usedSources) {
                            UUID qioSource = usedSource.getQioSource();
                            if (qioSource != null) {
                                availableItemSpace += usedSource.getUsed();
                                if (source.getQIORemaining(qioSource) == 0) {
                                    availableItemTypes++;
                                    usedQIO = true;
                                }
                            }
                        }
                    }
                    usedQIOSource.put(source, usedQIO);
                }
                for (Object2IntMap.Entry<HashedItem> entry : stillLeftOver.object2IntEntrySet()) {
                    availableItemSpace -= entry.getIntValue();
                    if (availableItemSpace <= 0) {
                        return false;
                    }
                    QIOCraftingTransferHelper.HashedItemSource source = qioTransferHelper.getSource(entry.getKey());
                    if (source == null) {
                        return false;
                    } else if (source.hasQIOSources()) {
                        if (usedQIOSource.containsKey(source) && usedQIOSource.getBoolean(source)) {
                            availableItemTypes--;
                            if (availableItemTypes <= 0) {
                                return false;
                            }
                        }
                    } else {
                        availableItemTypes--;
                        if (availableItemTypes <= 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private Result invalidSource(@NotNull HashedItem type) {
        Mekanism.logger.warn("Error finding source for: {} with nbt: {}. This should not be possible.", type.getItem(), type.getInternalTag());
        return Result.createFailed(Component.literal("Oops! Something wrong happened!"));
    }
}
