package com.jayfella.launcher.controller;

import com.jayfella.launcher.jme.DisplayModeSorter;
import com.jayfella.launcher.jfx.PowerOfTwoStringConverter;
import com.jayfella.launcher.settings.SavedSettings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {

    @FXML private CheckBox fullScreenCheckBox;
    @FXML private ComboBox<String> resolutionComboBox;

    @FXML private Slider anistropicFilteringSlider;
    @FXML private Slider antiAliasingSlider;
    @FXML private CheckBox ambientOcclusionCheckBox;
    @FXML private CheckBox bloomCheckBox;
    @FXML private CheckBox depthOfFieldCheckBox;

    private SavedSettings savedSettings;

    // resolution settings

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();

        Arrays.sort(modes, new DisplayModeSorter());

        // jayfella
        for (DisplayMode displayMode : modes) {

            String format = "%d x %d @ %dHz";
            String output = String.format(format,
                    displayMode.getWidth(),
                    displayMode.getHeight(),
                    displayMode.getRefreshRate());

            resolutionComboBox.getItems().add(output);
            // if (savedSettings.getScreenConfig().isFullscreen() && displayMode.isFullscreenCapable())
        }


        // combo box
        resolutionComboBox.valueProperty().addListener((observableValue, s, t1) ->
                savedSettings.getScreenConfig().setDisplayModeIndex(resolutionComboBox.getSelectionModel().getSelectedIndex()));

        // sliders
        anistropicFilteringSlider.setLabelFormatter(new PowerOfTwoStringConverter());
        antiAliasingSlider.setLabelFormatter(new PowerOfTwoStringConverter());

        // checkboxes
        fullScreenCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            savedSettings.getScreenConfig().setFullscreen(newValue);
        });


        anistropicFilteringSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                savedSettings.getAnistropicFilteringLevel().setLevel(newValue.intValue()));

        antiAliasingSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                savedSettings.getPostProcessorSettings().setSamples(newValue.intValue()));

        ambientOcclusionCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) ->
            savedSettings.getPostProcessorSettings().setSsao(newValue));

        bloomCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) ->
            savedSettings.getPostProcessorSettings().setBloom(newValue));

        depthOfFieldCheckBox.selectedProperty().addListener((observableValue, oldValue, newValue) ->
            savedSettings.getPostProcessorSettings().setDepthOfField(true));
    }

    protected void setSettings(SavedSettings savedSettings) {
        this.savedSettings = savedSettings;

        fullScreenCheckBox.setSelected(savedSettings.getScreenConfig().isFullscreen());
        resolutionComboBox.getSelectionModel().select(savedSettings.getScreenConfig().getDisplayModeIndex());

        anistropicFilteringSlider.setValue(savedSettings.getAnistropicFilteringLevel().getLevel());
        antiAliasingSlider.setValue(savedSettings.getPostProcessorSettings().getSamples());

        ambientOcclusionCheckBox.setSelected(savedSettings.getPostProcessorSettings().isSsao());
        bloomCheckBox.setSelected(savedSettings.getPostProcessorSettings().isBloom());
        depthOfFieldCheckBox.setSelected(savedSettings.getPostProcessorSettings().isDepthOfField());
    }

    @FXML
    public void saveButtonPressed(ActionEvent event) {
        this.savedSettings.save();
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }




}
