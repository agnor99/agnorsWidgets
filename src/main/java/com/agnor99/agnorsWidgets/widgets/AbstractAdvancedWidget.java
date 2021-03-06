package com.agnor99.agnorsWidgets.widgets;

import com.agnor99.agnorsWidgets.IAddButtonWrapper;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import com.agnor99.agnorsWidgets.widgets.widget.DefaultAdvancedWidget;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.awt.Point;
import java.awt.Dimension;
import java.util.List;

public class AbstractAdvancedWidget<RealWidget extends Widget> extends Widget implements IAdvancedWidget<RealWidget>{

    DefaultAdvancedWidget<RealWidget> impl;
    public ResourceLocation texture = new ResourceLocation("","");
    protected Screen addedOn;
    public AbstractAdvancedWidget(Point pos, Dimension size, Screen addedOn) {
        super(pos.x, pos.y, size.width, size.height, StringTextComponent.EMPTY);
        this.addedOn = addedOn;
        impl = new DefaultAdvancedWidget(this);
    }

    @Override
    public void removeFromChildren(Screen screen) {
        impl.removeFromChildren(screen);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        bindTexture(texture);
    }

    @Override
    public void addListener(WidgetChangeListener<RealWidget> listener) {
        impl.getListener().add(listener);
    }

    public final void notifyListener() {
        impl.getListener().forEach(listener -> listener.onChange((RealWidget)this));
    }

    @Override
    public void onRemove(Screen screen) {
        impl.onRemove(screen);
    }

    @Override
    public int getX() {
        return impl.getX();
    }

    @Override
    public int getY() {
        return impl.getY();
    }

    protected void drawPartOnScreen(MatrixStack stack, Point drawPos, Dimension size, Point texturePos) {
        blit(stack, drawPos.x + getX(), drawPos.y + getY(),texturePos.x, texturePos.y, size.width, size.height);
    }

    @Override
    public int getHeight() {
        return impl.getHeight();
    }

    @Override
    public List<WidgetChangeListener<RealWidget>> getListener() {
        return impl.getListener();
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }
    @Override
    public RealWidget getWidget() {
        return (RealWidget)this;
    }

    @Override
    public void addWidget(Widget widget, IAddButtonWrapper screenWrapper) {
        impl.addWidget(widget, screenWrapper);
    }
}
