package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.AgnorsWidgets;
import com.agnor99.agnorsWidgets.IAddButtonWrapper;
import com.agnor99.agnorsWidgets.widgets.AbstractAdvancedWidget;
import com.agnor99.agnorsWidgets.widgets.IAdvancedWidget;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import com.agnor99.agnorsWidgets.util.Point;
import com.agnor99.agnorsWidgets.util.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class NumberInput extends TextFieldWidget implements IAdvancedWidget<NumberInput> {
    private DefaultAdvancedWidget<NumberInput> impl;
    private Screen addedOn;
    private static final Dimension BUTTON_SIZE = new Dimension(7,7);
    private static final ResourceLocation BUTTON_RESOURCE = new ResourceLocation(AgnorsWidgets.MOD_ID, "textures/number_button.png");
    private NumberPredicate numberPredicate;


    public static NumberInput createPositive(Point pos, int width, Screen screen, WidgetChangeListener... listener) throws IllegalAccessException {
        return createPositive(Minecraft.getInstance().fontRenderer, pos, width, screen, listener);
    }
    public static NumberInput createPositive(FontRenderer font, Point pos, int width, Screen screen, WidgetChangeListener... listener) throws IllegalAccessException {
        return new NumberInput(font, pos, width, screen, 0, Integer.MAX_VALUE, 0, listener);
    }

    public static NumberInput create(Point pos, int width, Screen screen, int min, int max, int defaultValue, WidgetChangeListener... listeners) throws IllegalAccessException {
        return new NumberInput(Minecraft.getInstance().fontRenderer, pos, width, screen, min, max, defaultValue, listeners);
    }

    public NumberInput(FontRenderer font, Point pos, int width, Screen screen, int minValue, int maxValue, int defaultValue, WidgetChangeListener... listener) throws IllegalAccessException {
        super(font, pos.x + 1, pos.y + 1, width - 7 - 2, 14 - 2, StringTextComponent.EMPTY);
        if(!(screen instanceof IAddButtonWrapper)) {
            throw new IllegalAccessException("You need to implement IAddButtonWrapper");
        }
        impl = new DefaultAdvancedWidget<>(this);
        Arrays.asList(listener).forEach(this::addListener);
        addedOn = screen;
        width -= 7;
        numberPredicate = new NumberPredicate(minValue, maxValue);
        setValidator(numberPredicate);
        setValue(defaultValue);
        addWidget(new FunctionImageButton(new Point(pos.x + width, pos.y), BUTTON_SIZE, BUTTON_RESOURCE, new AddAction()), (IAddButtonWrapper)screen);
        addWidget(new FunctionImageButton(new Point(pos.x + width, pos.y + 7), BUTTON_SIZE, BUTTON_RESOURCE, new Point(7,0), new SubtractAction()), (IAddButtonWrapper)screen);
    }

    public void setValue(int value) {
        int correctValue = getCorrectValue(value);
        setText(Integer.toString(correctValue));
        notifyListener();
    }
    private int getCorrectValue(int newValue) {
        return Math.max(Math.min(numberPredicate.max, newValue), numberPredicate.min);
    }
    public int getValue() {
        return Integer.parseInt(getText());
    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    public void addListener(WidgetChangeListener<NumberInput> listener) {
        impl.addListener(listener);
    }

    @Override
    public List<WidgetChangeListener<NumberInput>> getListener() {
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
    public NumberInput getWidget() {
        return this;
    }

    @Override
    public void addWidget(Widget widget, IAddButtonWrapper screenWrapper) {
        impl.addWidget(widget, screenWrapper);
    }

    public interface MultipliableAction extends FunctionImageButton.Action {

        default int getMultiplier() {
            int multiplier = 1;
            if(Screen.hasShiftDown()) {
                multiplier *= 10;
            }
            if(Screen.hasControlDown()) {
                multiplier *= 100;
            }
            return multiplier;
        }
    }

    public class AddAction implements MultipliableAction {
        @Override
        public void doStuff() {
            setValue(getValue() + getMultiplier());
        }
    }
    public class SubtractAction implements MultipliableAction {
        @Override
        public void doStuff() {
            setValue(getValue() - getMultiplier());
        }
    }

    private static class NumberPredicate implements Predicate<String> {
        public int min, max;
        NumberPredicate(int min, int max) {
            this.min = min;
            this.max = max;
        }
        @Override
        public boolean test(String s) {
            try {
                int i = Integer.parseInt(s);
                return (i >= min && i <= max);
            }catch (NumberFormatException e) {
                return false;
            }
        }
    }

    @Override
    public void removeFromChildren(Screen screen) {
        impl.removeFromChildren(screen);
    }
}
