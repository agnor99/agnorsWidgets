package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.AgnorsWidgets;
import com.agnor99.agnorsWidgets.MathUtil;
import com.agnor99.agnorsWidgets.widgets.AbstractAdvancedWidget;
import com.agnor99.agnorsWidgets.widgets.listener.ScrollBarListener;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ScrollBar extends AbstractAdvancedWidget<ScrollBar> {
    private static final int WIDTH = 13;
    private static final Dimension SCROLLER_DIMENSION_VERTICAL = new Dimension(11,15);
    private static final Point SCROLLER_POSITION_VERTICAL = new Point(1,3);
    private static final Dimension SCROLLER_DIMENSION_HORIZONTAL = new Dimension(15,11);
    private static final Point SCROLLER_POSITION_HORIZONTAL = new Point(17,1);

    private final boolean vertical;

    private float scrollPosition;
    private boolean isScrolling;

    public ScrollBar(Point pos, int length, boolean vertical, Screen screen, WidgetChangeListener<ScrollBar>... listeners) {
        super(pos, dimensionOf(length, vertical), screen);
        this.vertical = vertical;
        for (WidgetChangeListener<ScrollBar> listener : listeners) {
            addListener(listener);
        }
        notifyListener();
        texture = new ResourceLocation(AgnorsWidgets.MOD_ID, "textures/scrollbar.png");
    }

    static Dimension dimensionOf(int length, boolean vertical) {
        if(vertical) return new Dimension(WIDTH, length);
        return new Dimension(length, WIDTH);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        int lenght = vertical ? height : width;
        if(vertical) {
            drawPartOnScreen(matrixStack,new Point(0,0), new Dimension(WIDTH, 1), new Point(0,0));
            for(int y = 1; y < lenght - 1; y++) {
                drawPartOnScreen(matrixStack,new Point(0,y), new Dimension(WIDTH, 1), new Point(0,1));
            }
            drawPartOnScreen(matrixStack,new Point(0,lenght-1), new Dimension(WIDTH, 1), new Point(0,2));

            Point scrollerPosition = new Point(1, (int)((lenght - 17) * scrollPosition) + 1);
            drawPartOnScreen(matrixStack, scrollerPosition, SCROLLER_DIMENSION_VERTICAL,SCROLLER_POSITION_VERTICAL);
        }else{
            drawPartOnScreen(matrixStack,new Point(0,0), new Dimension(1, WIDTH), new Point(14,0));
            for(int x = 1; x < lenght - 1; x++) {
                drawPartOnScreen(matrixStack,new Point(x,0), new Dimension(1, WIDTH), new Point(15,0));
            }
            drawPartOnScreen(matrixStack,new Point(lenght-1,0), new Dimension(1, WIDTH), new Point(16,0));

            Point scrollerPosition = new Point((int)((lenght - 17) * scrollPosition) + 1, 1);
            drawPartOnScreen(matrixStack,scrollerPosition, SCROLLER_DIMENSION_HORIZONTAL, SCROLLER_POSITION_HORIZONTAL);
        }
    }

    public float getScrollPosition() {
        return scrollPosition;
    }

    public void setMappedScrollPosition(int max, int value) {
        scrollPosition = (float) MathUtil.mapValueInRange(value, 0, max, 0,1);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Point relativeMouse = new Point((int) mouseX, (int) mouseY);
        if(isReallyHovered(relativeMouse)) {
            isScrolling = true;
            handleScroll(relativeMouse);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(isScrolling) {
            isScrolling = false;
            for(WidgetChangeListener listener: getListener()) {
                if(listener instanceof ScrollBarListener) {
                    ((ScrollBarListener)listener).stoppedScrolling(this);
                }
                listener.onChange(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {

        Point relativeMouse = new Point((int) (mouseX + dragX), (int) (mouseY + dragY));
        if(isScrolling) {
            handleScroll(relativeMouse);
            return true;
        }
        return false;
    }

    private void handleScroll(Point mouse) {
        handleScroll(vertical ? mouse.y : mouse.x);
    }
    private void handleScroll(int heightOrWidth) {
        double scroll;
        double length;
        double scrollPos;
        if(vertical) {
            length = height -17d;
            scrollPos = heightOrWidth - 7d - y;
        }else {
            length = width - 17d;
            scrollPos = heightOrWidth - 7d - x;
        }

        scroll = MathUtil.mapValue(scrollPos, 0, length);

        scrollPosition = (float) MathHelper.clamp(scroll, 0, 1);
        isScrolling = true;
        for(WidgetChangeListener listener: getListener()) {
            if(listener instanceof ScrollBarListener) {
                ((ScrollBarListener)listener).updateScrolling(this);
            }
            listener.onChange(this);
        }
    }
}
