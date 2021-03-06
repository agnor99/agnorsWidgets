package com.agnor99.agnorsWidgets.widgets.listener;

import com.agnor99.agnorsWidgets.widgets.widget.ScrollBar;

public interface ScrollBarListener extends WidgetChangeListener<ScrollBar> {

    default void updateScrolling(ScrollBar scrollBar) {}
    default void stoppedScrolling(ScrollBar scrollBar) {}
}
