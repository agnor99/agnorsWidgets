package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.IAddButtonWrapper;
import com.agnor99.agnorsWidgets.widgets.IAdvancedWidget;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DefaultAdvancedWidget<RealWidget extends Widget> implements IAdvancedWidget<RealWidget> {

    RealWidget widget;
    List<WidgetChangeListener<RealWidget>> listener = new ArrayList();
    List<Widget> addedWidgets = new ArrayList();

    public DefaultAdvancedWidget(RealWidget widget) {
        this.widget = widget;
    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }


    @Override
    public void removeFromChildren(Screen screen) {
        screen.children.remove(getWidget());
        addedWidgets.forEach(widget -> {
            if(widget instanceof IAdvancedWidget) {
                ((IAdvancedWidget<?>) widget).removeFromChildren(screen);
            }else {
                screen.children.remove(widget);
            }
        });
    }

    @Override
    public void addListener(WidgetChangeListener<RealWidget> listener) {
        this.listener.add(listener);
    }

    @Override
    public List<WidgetChangeListener<RealWidget>> getListener() {
        return listener;
    }

    @Override
    public final void onRemove(Screen screen) {
        addedWidgets.forEach(widget -> removeSubWidget(widget, screen));
    }
    public void removeSubWidget(Widget widget, Screen screen) {
        screen.buttons.remove(widget);

        if(widget instanceof IAdvancedWidget) {
            ((IAdvancedWidget) widget).onRemove(screen);
        }
    }

    @Override
    public int getX() {
        return widget.x;
    }

    @Override
    public int getY() {
        return widget.y;
    }

    @Override
    public int getWidth() {
        return widget.getWidth();
    }

    @Override
    public int getHeight() {
        return widget.getHeightRealms();
    }

    @Override
    public void addWidget(Widget widget, IAddButtonWrapper screenWrapper) {
        addedWidgets.add(widget);
        screenWrapper.addButtonToScreen(widget);
    }

    @Override
    public RealWidget getWidget() {
        return widget;
    }
}
