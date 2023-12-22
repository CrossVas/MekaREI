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
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
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

public class PigmentEntryType implements EntryDefinition<PigmentStack>, EntrySerializer<PigmentStack> {

    @Override
    public boolean supportSaving() {
        return true;
    }

    @Override
    public boolean supportReading() {
        return true;
    }

    @Override
    public CompoundTag save(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return pigmentStack.write(new CompoundTag());
    }

    @Override
    public PigmentStack read(CompoundTag compoundTag) {
        return PigmentStack.readFromNBT(compoundTag);
    }

    @Override
    public Class<PigmentStack> getValueType() {
        return PigmentStack.class;
    }

    @Override
    public EntryType<PigmentStack> getType() {
        return MekanismEntryTypes.PIGMENT_ENTRY;
    }

    @Override
    public EntryRenderer<PigmentStack> getRenderer() {
        return new AbstractEntryRenderer<>() {
            @Override
            public void render(EntryStack<PigmentStack> entryStack, PoseStack poseStack, Rectangle rectangle, int mouseX, int mouseY, float delta) {
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
            public @Nullable Tooltip getTooltip(EntryStack<PigmentStack> entryStack, TooltipContext tooltipContext) {
                Chemical<?> chemical = entryStack.getValue().getType();
                return Tooltip.create(TextComponentUtil.build(chemical), MekanismLang.GENERIC_MB.translateColored(EnumColor.GRAY, TextUtils.format(entryStack.getValue().getAmount())));
            }
        };
    }

    @Override
    public @Nullable ResourceLocation getIdentifier(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return MekanismEntryTypes.PIGMENT_ENTRY.getIdentifier();
    }

    @Override
    public boolean isEmpty(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return pigmentStack.isEmpty();
    }

    @Override
    public PigmentStack copy(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return pigmentStack.copy();
    }

    @Override
    public PigmentStack normalize(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return new PigmentStack(pigmentStack.getType(), pigmentStack.getAmount());
    }

    @Override
    public PigmentStack wildcard(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return new PigmentStack(pigmentStack.getType(), pigmentStack.getAmount());
    }

    @Override
    public long hash(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack, ComparisonContext comparisonContext) {
        EntryComparatorRegistry<PigmentStack, Pigment> REGISTRY = new MekEntryComparator<>();
        int code = 1;
        code = 31 * code + pigmentStack.getType().hashCode();
        code = 31 * code + Long.hashCode(REGISTRY.hashOf(comparisonContext, pigmentStack));
        return code;
    }

    @Override
    public boolean equals(PigmentStack stack, PigmentStack stack1, ComparisonContext comparisonContext) {
        return stack.getType().equals(stack1.getType());
    }

    @Override
    public @Nullable EntrySerializer<PigmentStack> getSerializer() {
        return this;
    }

    @Override
    public Component asFormattedText(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return TextComponentUtil.build(pigmentStack);
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<PigmentStack> entryStack, PigmentStack pigmentStack) {
        return null;
    }
}
