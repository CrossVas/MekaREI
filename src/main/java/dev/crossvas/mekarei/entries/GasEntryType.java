package dev.crossvas.mekarei.entries;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.crossvas.mekarei.utils.MekanismEntryTypes;
import dev.crossvas.mekarei.utils.MekEntryComparator;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.AbstractEntryRenderer;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.client.util.SpriteRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.comparison.EntryComparatorRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.text.EnumColor;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.MekanismLang;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class GasEntryType implements EntryDefinition<GasStack>, EntrySerializer<GasStack> {

    @Override
    public boolean supportSaving() {
        return true;
    }

    @Override
    public boolean supportReading() {
        return true;
    }

    @Override
    public CompoundTag save(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return gasStack.write(new CompoundTag());
    }

    @Override
    public GasStack read(CompoundTag compoundTag) {
        return GasStack.readFromNBT(compoundTag);
    }

    @Override
    public Class<GasStack> getValueType() {
        return GasStack.class;
    }

    @Override
    public EntryType<GasStack> getType() {
        return MekanismEntryTypes.GAS_ENTRY;
    }

    @Override
    public EntryRenderer<GasStack> getRenderer() {
        return new AbstractEntryRenderer<>() {
            @Override
            public void render(EntryStack<GasStack> entryStack, PoseStack poseStack, Rectangle rectangle, int mouseX, int mouseY, float delta) {
                if (!entryStack.isEmpty()) {
                    Chemical<?> chemical = entryStack.getValue().getType();
                    TextureAtlasSprite sprite = MekanismRenderer.getSprite(chemical.getIcon());
                    int color = chemical.getTint();
                    RenderSystem.setShaderColor(MekanismRenderer.getRed(color), MekanismRenderer.getGreen(color), MekanismRenderer.getBlue(color), 1.0F);
                    MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();
                    SpriteRenderer.beginPass().setup(immediate, RenderType.solid()).sprite(sprite).color(color).light(15728880).overlay(OverlayTexture.NO_OVERLAY).alpha(255).normal(poseStack.last().normal(), 0.0F, 0.0F, 0.0F).position(poseStack.last().pose(), (float) rectangle.x, (float) rectangle.getMaxY() - (float) rectangle.height * Mth.clamp(entryStack.get(EntryStack.Settings.FLUID_RENDER_RATIO), 0.0F, 1.0F), (float) rectangle.getMaxX(), (float) rectangle.getMaxY(), (float) entryStack.getZ()).next(InventoryMenu.BLOCK_ATLAS);
                    immediate.endBatch();
                }
            }

            @Override
            public @Nullable Tooltip getTooltip(EntryStack<GasStack> entryStack, TooltipContext tooltipContext) {
                Chemical<?> chemical = entryStack.getValue().getType();
                return Tooltip.create(TextComponentUtil.build(chemical), MekanismLang.GENERIC_MB.translateColored(EnumColor.GRAY, TextUtils.format(entryStack.getValue().getAmount())));
            }
        };
    }

    @Override
    public @Nullable ResourceLocation getIdentifier(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return MekanismEntryTypes.GAS_ENTRY.getIdentifier();
    }

    @Override
    public boolean isEmpty(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return gasStack.isEmpty();
    }

    @Override
    public GasStack copy(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return gasStack.copy();
    }

    @Override
    public GasStack normalize(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return new GasStack(gasStack.getType(), gasStack.getAmount());
    }

    @Override
    public GasStack wildcard(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return new GasStack(gasStack.getType(), gasStack.getAmount());
    }

    @Override
    public long hash(EntryStack<GasStack> entryStack, GasStack gasStack, ComparisonContext comparisonContext) {
        EntryComparatorRegistry<GasStack, Gas> REGISTRY = new MekEntryComparator<>();
        int code = 1;
        code = 31 * code + gasStack.getType().hashCode();
        code = 31 * code + Long.hashCode(REGISTRY.hashOf(comparisonContext, gasStack));
        return code;
    }

    @Override
    public boolean equals(GasStack stack1, GasStack stack2, ComparisonContext comparisonContext) {
        return stack1.getType().equals(stack2.getType());
    }

    @Override
    public @Nullable EntrySerializer<GasStack> getSerializer() {
        return this;
    }

    @Override
    public Component asFormattedText(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return TextComponentUtil.build(gasStack);
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<GasStack> entryStack, GasStack gasStack) {
        return null;
    }
}
