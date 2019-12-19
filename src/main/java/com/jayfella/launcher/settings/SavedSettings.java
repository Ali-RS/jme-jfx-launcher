package com.jayfella.launcher.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SavedSettings {

    @JsonIgnore
    private File saveFile;

    private ScreenConfig screenConfig;
    private AnistropicFilteringLevel anistropicFilteringLevel;
    private PostProcessorSettings postProcessorSettings;

    public SavedSettings() {

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();

        this.screenConfig = new ScreenConfig();
        this.screenConfig.setDisplayModeIndex(modes.length - 1);

        this.anistropicFilteringLevel = new AnistropicFilteringLevel();
        this.postProcessorSettings = new PostProcessorSettings();
    }

    public ScreenConfig getScreenConfig() {
        return screenConfig;
    }

    public void setScreenConfig(ScreenConfig screenConfig) {
        this.screenConfig = screenConfig;
    }

    public AnistropicFilteringLevel getAnistropicFilteringLevel() {
        return anistropicFilteringLevel;
    }

    public void setAnistropicFilteringLevel(AnistropicFilteringLevel anistropicFilteringLevel) {
        this.anistropicFilteringLevel = anistropicFilteringLevel;
    }

    public PostProcessorSettings getPostProcessorSettings() {
        return postProcessorSettings;
    }

    public void setPostProcessorSettings(PostProcessorSettings postProcessorSettings) {
        this.postProcessorSettings = postProcessorSettings;
    }

    public static SavedSettings load(File file) {

        if (!file.exists()) {

            SavedSettings savedSettings = new SavedSettings();
            savedSettings.saveFile = file;
            savedSettings.save();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SavedSettings savedSettings = objectMapper.readValue(file, SavedSettings.class);
            savedSettings.saveFile = file;
            return savedSettings;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean save() {

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(saveFile, this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
