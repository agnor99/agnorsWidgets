package com.agnor99.agnorsWidgets;

public class MathUtil {
    public static double mapValue(double value, double min, double max) {
        return mapValueInRange(value, min, max, 0, 1);
    }

    public static double mapValueInRange(double value, double min, double max, double toMin, double toMax) {
        return ((value - min) / (max - min) * (toMax - toMin) + toMin);
    }
}
