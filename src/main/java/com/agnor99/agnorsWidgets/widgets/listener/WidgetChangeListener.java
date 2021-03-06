package com.agnor99.agnorsWidgets.widgets.listener;

import com.agnor99.agnorsWidgets.widgets.AbstractAdvancedWidget;
import net.minecraft.client.gui.widget.Widget;

public interface WidgetChangeListener<RealWidget extends Widget> {
    void onChange(RealWidget widget);
}
