package com.agnor99.agnorsWidgets.widgets.widget;

import com.agnor99.agnorsWidgets.AgnorsWidgets;
import com.agnor99.agnorsWidgets.IAddButtonWrapper;
import com.agnor99.agnorsWidgets.util.CustomColor;
import com.agnor99.agnorsWidgets.util.Point;
import com.agnor99.agnorsWidgets.util.Dimension;
import com.agnor99.agnorsWidgets.widgets.AbstractAdvancedWidget;
import com.agnor99.agnorsWidgets.widgets.listener.WidgetChangeListener;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;


public class ColorPickerWidget extends AbstractAdvancedWidget<ColorPickerWidget> {

    CustomColor currentColor;
    boolean isChanging = true;
    NumberInput brightnes;
    NumberInput red, green, blue;
    Point mousePos;
    ColorPreviewWidget previewWidget;
    public static final Point CENTER = new Point(54, 72);
    public static final Point BAR = new Point(108, 22);

    public ColorPickerWidget(Point pos, Screen addedOn, CustomColor currentColor, ColorPreviewWidget previewWidget) {
        super(pos, new Dimension(123,181), addedOn);
        previewWidget.addWidget(this,(IAddButtonWrapper) addedOn);
        this.previewWidget = previewWidget;
        this.currentColor = currentColor;
        mousePos = getPositionOfColor();
        texture = new ResourceLocation(AgnorsWidgets.MOD_ID, "textures/color_widget.png");
        try {
            brightnes = NumberInput.create(new Point(pos.x+4, pos.y+4), 100, addedOn, 0, 100,(int)(getBrightness() * 100), new BrightnessListener());
            addWidget(brightnes, (IAddButtonWrapper) addedOn);
            red = NumberInput.create(new Point(pos.x+4, pos.y+126), 100, addedOn, 0, 255,currentColor.getRed(), new RedListener());
            addWidget(red , (IAddButtonWrapper) addedOn);
            green = NumberInput.create(new Point(pos.x+4, pos.y+144), 100, addedOn, 0, 255,currentColor.getGreen(), new GreenListener());
            addWidget(green, (IAddButtonWrapper) addedOn);
            blue = NumberInput.create(new Point(pos.x+4, pos.y+162), 100, addedOn, 0, 255,currentColor.getBlue(), new BlueListener());
            addWidget(blue, (IAddButtonWrapper) addedOn);
            isChanging = false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        drawPartOnScreen(matrixStack, new Point(0,0), new Dimension(width, height), new Point(0,0));
        int radius = 50;
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                double distance = Math.sqrt(x*x+y*y);
                if (distance > radius) {
                    continue;
                }
                fill(matrixStack, x+CENTER.x+this.x, y+CENTER.y+this.y, x+1+CENTER.x+this.x, y+1+CENTER.y+this.y, RGBofPosition(x,y,radius,1).getRGB());
            }
        }
        for(int brightness = 0; brightness < 100; brightness+=1) {
            fill(matrixStack, this.x+BAR.x, this.y+ BAR.y + brightness, this.x+BAR.x+10, this.y+ BAR.y + brightness + 1, RGBofPosition(mousePos.x, mousePos.y, 50, 1-brightness/100f).getRGB());
        }
        bindTexture(texture);
        drawPartOnScreen(matrixStack, new Point(mousePos.x-1+CENTER.x, mousePos.y-1+CENTER.y), new Dimension(3,3), new Point(123,0));
        drawPartOnScreen(matrixStack, new Point(BAR.x-1, BAR.y - 1 + (int)((1 - getHSBofColor()[2])*100)), new Dimension(12,3), new Point(123,3));

    }

    private static CustomColor RGBofPosition(int x, int y, int radius, float brightness) {
        double distance = Math.sqrt(x*x+y*y);
        double angle = Math.atan2(y, x);
        double degrees = Math.toDegrees(angle);
        float hue = (float) (degrees < 0 ? 360 + degrees : degrees);
        float saturation = (float) (distance/radius);
        return CustomColor.HSBtoRGB(hue/360f, saturation, brightness);
    }

    private Point getPositionOfColor() {
        float[] hsb = getHSBofColor();
        double distance = hsb[1] * 50;
        double angle = hsb[0];
        int x = (int)(Math.cos(angle*2*Math.PI) * distance);
        int y = (int)(Math.sin(angle*2*Math.PI) * distance);
        return new Point(x,y);
    }
    private float[] getHSBofColor() {
        return currentColor.toHSB();
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
       if(!isReallyHovered(new Point((int)mouseX, (int)mouseY))) {
           previewWidget.color = currentColor;
           previewWidget.removePicker(this);
           return false;
       }
       return drawMouse(mouseX, mouseY);
    }
    boolean drawMouse(double mouseX, double mouseY) {
        int dx = (int)mouseX - x;
        int dy = (int)mouseY - y;

        if(dx >= BAR.x && dx <= BAR.x + 10 && dy >= BAR.y && dy <= BAR.y + 200) {
            int brightness = dy - BAR.y;
            brightness = 100-brightness;
            brightnes.setValue(brightness);
            return true;
        }


        dx = (int)(mouseX - CENTER.x - x);
        dy = (int)(mouseY - CENTER.y - y);
        double distance = Math.sqrt(dx*dx+dy*dy);
        if (distance > 50) {
            return false;
        }
        currentColor = RGBofPosition(dx,dy,50, getBrightness());
        updateColor(VarChanged.ALL);
        return true;
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return drawMouse(mouseX+dragX, mouseY+dragY);
    }

    private float getHue() {
        return currentColor.toHSB()[0];
    }
    private float getSaturation() {
        return currentColor.toHSB()[1];
    }
    private float getBrightness() {
        return currentColor.toHSB()[2];
    }
    private void updateColor(VarChanged varChanged) {
        isChanging = true;
        if(varChanged != VarChanged.BRIGHTNESS) {
            brightnes.setValue((int) (getBrightness() * 100));
        }
        if(varChanged != VarChanged.RED) {
            red.setValue(currentColor.getRed());
        }
        if(varChanged != VarChanged.GREEN) {
            green.setValue(currentColor.getGreen());
        }
        if(varChanged != VarChanged.BLUE) {
            blue.setValue(currentColor.getBlue());
        }
        isChanging = false;
        mousePos = getPositionOfColor();
    }


    private class RedListener implements WidgetChangeListener<NumberInput> {
        @Override
        public void onChange(NumberInput widget) {
            if(isChanging) return;
            currentColor = new CustomColor(widget.getValue(), currentColor.getGreen(), currentColor.getBlue());
            updateColor(VarChanged.RED);
        }
    }
    private class GreenListener implements WidgetChangeListener<NumberInput> {
        @Override
        public void onChange(NumberInput widget) {
            if(isChanging) return;
            currentColor = new CustomColor(currentColor.getRed(), widget.getValue(), currentColor.getBlue());
            updateColor(VarChanged.GREEN);
        }
    }
    private class BlueListener implements WidgetChangeListener<NumberInput> {
        @Override
        public void onChange(NumberInput widget) {
            if(isChanging) return;
            currentColor = new CustomColor(currentColor.getRed(), currentColor.getGreen(), widget.getValue());
            updateColor(VarChanged.BLUE);
        }
    }
    private class BrightnessListener implements WidgetChangeListener<NumberInput> {
        @Override
        public void onChange(NumberInput widget) {
            if(isChanging)return;
            currentColor = CustomColor.HSBtoRGB(getHue(), getSaturation(), widget.getValue() / 100f);
            updateColor(VarChanged.BRIGHTNESS);
        }
    }
    private enum VarChanged {
        ALL,
        RED,
        GREEN,
        BLUE,
        BRIGHTNESS
    }
}
