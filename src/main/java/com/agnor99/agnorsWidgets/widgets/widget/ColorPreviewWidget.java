package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.util.CustomColor;
import com.agnor99.agnorsWidgets.widgets.AbstractAdvancedWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;

import com.agnor99.agnorsWidgets.util.Point;
import com.agnor99.agnorsWidgets.util.Dimension;

public class ColorPreviewWidget extends AbstractAdvancedWidget<ColorPreviewWidget> {
    CustomColor color = new CustomColor(25,0,0);
    boolean hasPicker = false;
    ColorPickerWidget widget;
    public ColorPreviewWidget(Point pos, Dimension size, Screen addedOn) {
        super(pos, size, addedOn);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if(widget != null && !hasPicker) {
            widget.removeFromChildren(addedOn);
            widget = null;
        }
        fill(matrixStack, x, y,x+width, y+height, isReallyHovered(new Point(mouseX, mouseY)) ? -1 : -6250336);
        fill(matrixStack, x+1, y+1, x+width-1, y+height-1,color.getRGB());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if(hasPicker) return;
        hasPicker = true;
        new ColorPickerWidget(new Point((int)mouseX, (int)mouseY), addedOn, color, this);
    }

    public void removePicker(ColorPickerWidget picker) {
        if(!hasPicker) return;
        hasPicker = false;
        picker.remove(addedOn);
        widget = picker;
    }
}
