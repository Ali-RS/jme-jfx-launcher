package com.jayfella.launcher.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AnistropicFilteringLevel {

    private int level = 0;

    public AnistropicFilteringLevel() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonIgnore
    public int getJmeLevel() {
        switch (level) {
            case 1: return 2;
            case 2: return 4;
            case 3: return 8;
            case 4: return 16;
            default: return 1;
        }
    }
}
