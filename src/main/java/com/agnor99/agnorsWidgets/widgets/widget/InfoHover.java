package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.AgnorsWidgets;
import com.agnor99.agnorsWidgets.widgets.AbstractAdvancedWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.awt.Point;
import java.awt.Dimension;
import java.util.Arrays;

public class InfoHover extends AbstractAdvancedWidget {

    ITextComponent hoverMessage;
    Point texturePosition = new Point();
    private static final ResourceLocation DEFAUL_TEXTURE = new ResourceLocation(AgnorsWidgets.MOD_ID, "textures/info.png");

    public InfoHover(Point pos, Dimension size, ITextComponent hoverMessage, ResourceLocation texture, Screen addedOn) {
        super(pos, size, addedOn);
        this.texture = texture;
        this.hoverMessage = hoverMessage;
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(stack, mouseX, mouseY, partialTicks);
        blit(stack, x, y, texturePosition.x, texturePosition.y, width, height);
        if(isReallyHovered(new Point(mouseX, mouseY))) {
            GuiUtils.drawHoveringText(stack, Arrays.asList(hoverMessage), mouseX, mouseY, addedOn.width, addedOn.height, 100, Minecraft.getInstance().fontRenderer);
        }
    }

    public static InfoHover createSquare14(Point pos, ITextComponent hoverMessage, Screen screen) {
        InfoHover infoHover = new InfoHover(pos, new Dimension(14,14), hoverMessage, DEFAUL_TEXTURE, screen);
        infoHover.texturePosition = new Point(0,10);
        return infoHover;
    }

    public static InfoHover createWidth18Height10(Point pos, ITextComponent hoverMessage, Screen screen) {
        InfoHover infoHover = new InfoHover(pos, new Dimension(18,10), hoverMessage, DEFAUL_TEXTURE, screen);
        return infoHover;
    }
}
