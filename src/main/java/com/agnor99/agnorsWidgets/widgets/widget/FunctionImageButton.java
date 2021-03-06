package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.IAddButtonWrapper;
import com.agnor99.agnorsWidgets.widgets.IAdvancedWidget;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

public class FunctionImageButton extends ImageButton implements IAdvancedWidget<FunctionImageButton> {

    Action action;
    DefaultAdvancedWidget<FunctionImageButton> impl;

    public FunctionImageButton(Point position, Dimension size, ResourceLocation resourceLocationIn, Action func) {
        this(position, size, resourceLocationIn, new Point(0,0), func);
    }

    public FunctionImageButton(Point position, Dimension size, ResourceLocation resourceLocationIn, Point textureOffset, Action func) {
        super(position.x, position.y, size.width, size.height, textureOffset.x, textureOffset.y, size.height, resourceLocationIn, null);
        action = func;
        impl = new DefaultAdvancedWidget<>(this);
    }

    @Override
    public void onPress() {
        if(action == null) return;
        action.doStuff();
        notifyListener();
    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    public void addListener(WidgetChangeListener<FunctionImageButton> listener) {
        impl.addListener(listener);
    }

    @Override
    public List<WidgetChangeListener<FunctionImageButton>> getListener() {
        return impl.getListener();
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

    @Override
    public int getHeight() {
        return impl.getHeight();
    }

    @Override
    public FunctionImageButton getWidget() {
        return this;
    }

    @Override
    public void addWidget(Widget widget, IAddButtonWrapper screenWrapper) {
        impl.addWidget(widget, screenWrapper);
    }

    public interface Action {
        void doStuff();
    }

    @Override
    public void removeFromChildren(Screen screen) {
        impl.removeFromChildren(screen);
    }
}