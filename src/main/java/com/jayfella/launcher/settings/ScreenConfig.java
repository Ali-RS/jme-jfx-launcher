package com.jayfella.launcher.settings;

public class ScreenConfig {

    private boolean fullscreen = false;
    private int displayModeIndex;

    public ScreenConfig() {

    }

    public int getDisplayModeIndex() {
        return displayModeIndex;
    }

    public void setDisplayModeIndex(int displayModeIndex) {
        this.displayModeIndex = displayModeIndex;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

}
