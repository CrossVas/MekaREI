package dev.crossvas.mekarei.utils;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface IGuiHelper {

    Font font = Minecraft.getInstance().font;
    int lineHeight = font.lineHeight;
    int offset = 5;
    int innerOffset = 3;
    int slotSize = 18;
    int defaultWidth = 96;
    int defaultHeight = 64;

    default int getProgressBarX(Rectangle bounds) {
        return getCenterX(bounds) + innerOffset + 1;
    }

    default int getProgressBarY(Rectangle bounds) {
        return getCenterY(bounds) + innerOffset + 1;
    }

    default Point adjustedInputPoint(Rectangle bounds) {
        return point(bounds.getMinX() + offset + lineHeight, getCenterY(bounds));
    }

    default Point adjustedOutputPoint(Rectangle bounds) {
        return point(bounds.getMaxX() - innerOffset - slotSize - lineHeight, getCenterY(bounds));
    }

    default Point adjustedOutputBackPoint(Rectangle bounds) {
        return point(bounds.getMaxX() - slotSize - lineHeight - innerOffset, getCenterY(bounds));
    }

    default int getCenterX(Rectangle bounds) {
        return bounds.getCenterX() - slotSize / 2 + 1;
    }

    default int getCenterY(Rectangle bounds) {
        return bounds.getCenterY() - slotSize / 2 + 1;
    }

    default Point point(int x, int y) {
        return new Point(x, y);
    }

    default MutableComponent format(String text) {
        return Component.translatable(text).withStyle(ChatFormatting.BLACK);
    }

    default MutableComponent format(String text, Object... pArgs) {
        return Component.translatable(text, pArgs).withStyle(ChatFormatting.BLACK);
    }

    default MutableComponent literal(String text) {
        return Component.literal(text).withStyle(ChatFormatting.BLACK);
    }
}
