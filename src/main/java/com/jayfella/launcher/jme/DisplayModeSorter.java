package com.jayfella.launcher.jme;

import java.awt.*;
import java.util.Comparator;

public class DisplayModeSorter  implements Comparator<DisplayMode> {

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(DisplayMode a, DisplayMode b) {
        // Width
        if (a.getWidth() != b.getWidth()) {
            return (a.getWidth() > b.getWidth()) ? 1 : -1;
        }
        // Height
        if (a.getHeight() != b.getHeight()) {
            return (a.getHeight() > b.getHeight()) ? 1 : -1;
        }
        // Bit depth
        if (a.getBitDepth() != b.getBitDepth()) {
            return (a.getBitDepth() > b.getBitDepth()) ? 1 : -1;
        }
        // Refresh rate
        if (a.getRefreshRate() != b.getRefreshRate()) {
            return (a.getRefreshRate() > b.getRefreshRate()) ? 1 : -1;
        }
        // All fields are equal
        return 0;
    }
}