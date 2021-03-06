package com.agnor99.agnorsWidgets.widgets;

import com.agnor99.agnorsWidgets.IAddButtonWrapper;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;

import com.agnor99.agnorsWidgets.util.Point;
import java.util.List;

public interface IAdvancedWidget<RealWidget extends Widget> {

    default boolean isReallyHovered(Point mousePosition) {
        return mousePosition.x >= getX() && mousePosition.x <= getX() + getWidth() && mousePosition.y >= getY() && mousePosition.y <= getY() + getHeight();
    }
    ResourceLocation getTexture();
    void addListener(WidgetChangeListener<RealWidget> listener);
    List<WidgetChangeListener<RealWidget>> getListener();
    default void notifyListener() {
        getListener().forEach(listener -> listener.onChange(getWidget()));
    }
    default void remove(Screen screen) {
        screen.buttons.remove(this);
        onRemove(screen);
    }
    void removeFromChildren(Screen screen);
    void onRemove(Screen screen);
    default void bindTexture(ResourceLocation texture) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture);
    }
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    RealWidget getWidget();
    void addWidget(Widget widget, IAddButtonWrapper screenWrapper);
}
