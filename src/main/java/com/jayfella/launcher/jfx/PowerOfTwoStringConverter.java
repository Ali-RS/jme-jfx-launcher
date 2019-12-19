package com.jayfella.launcher.jfx;

import javafx.util.StringConverter;

public class PowerOfTwoStringConverter extends StringConverter<Double> {

    @Override
    public String toString(Double n) {

        if (n == 0) return "Off";
        else if (n == 1) return "2x";
        else if (n == 2) return "4x";
        else if (n == 3) return "8x";
        else if (n == 4) return "16x";

        else return "unknown";
    }

    @Override
    public Double fromString(String s) {

        switch (s) {
            case "Off":
                return 0d;
            case "2x":
                return 1d;
            case "4x":
                return 2d;
            case "8x":
                return 3d;
            case "16x":
                return 4d;
            default:
                return -1d;
        }
    }

}
