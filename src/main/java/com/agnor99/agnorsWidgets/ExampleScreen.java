package com.agnor99.agnorsWidgets;

import com.agnor99.agnorsWidgets.widgets.widget.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

public class ExampleScreen extends Screen implements IAddButtonWrapper{
    protected ExampleScreen() {
        super(StringTextComponent.EMPTY);
    }

    @Override
    protected void init() {
        super.init();
        addButton(InfoHover.createWidth18Height10(new Point(100, 100), new StringTextComponent("test"),this));
        addButton(InfoHover.createSquare14(new Point(120, 100), new StringTextComponent("test2"),this));
        addButton(new FunctionImageButton(new Point(140, 100), new Dimension(10,10), new ResourceLocation(AgnorsWidgets.MOD_ID, ""), () -> System.out.println("testFunction")));
        addButton(new ScrollBar(new Point(160,100), 100, false, this));
        addButton(new ScrollBar(new Point(160,120), 100, true, this));
        addButton(new ColorPreviewWidget(new Point(280,100), new Dimension(20,20), this));
        try {
            addButton(NumberInput.createPositive(new Point(100, 120), 100, this));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
    }

    @Override
    public void addButtonToScreen(Widget widget) {
        addButton(widget);
    }
}
