package com.jayfella.launcher.controller;

import com.jayfella.launcher.jme.AnistropicFilteringListener;
import com.jayfella.launcher.jme.DisplayModeSorter;
import com.jayfella.launcher.settings.SavedSettings;
import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import com.jme3.system.AppSettings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {

    @FXML private AnchorPane anchorPane;
    @FXML private Label titleLabel;

    private AppSettings appSettings;
    private SimpleApplication jmeApp;

    private SavedSettings savedSettings;

    private int bitsPerPixel = 24;

    public LauncherController() {
        this.appSettings = new AppSettings(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Pane getContainer() {
        return anchorPane;
    }

    public void setJmeApp(SimpleApplication jmeApp) {
        this.jmeApp = jmeApp;
    }

    public void setTitle(String title) {
        appSettings.setTitle(title);
        Platform.runLater(() -> titleLabel.setText(title));
    }

    public void setBitsPerPixel(int bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }

    public void setSettingsFile(File settingsFile) {
        savedSettings = SavedSettings.load(settingsFile);
    }

    protected SavedSettings getSavedSettings() {
        return this.savedSettings;
    }

    public void setBackgroundImage(String resourceImage) {

        BackgroundImage backgroundImage = new BackgroundImage(new Image(resourceImage),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        anchorPane.setBackground(new Background(backgroundImage));
    }

    public void applyPostProcessors(FilterPostProcessor fpp) {
        savedSettings.getPostProcessorSettings().apply(fpp);
    }

    @FXML
    public void playButtonPressed(ActionEvent event) {
        Platform.exit();

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        Arrays.sort(modes, new DisplayModeSorter());

        DisplayMode selectedMode = modes[savedSettings.getScreenConfig().getDisplayModeIndex()];

        appSettings.setResolution(selectedMode.getWidth(), selectedMode.getHeight());
        appSettings.setFrequency(selectedMode.getRefreshRate());
        appSettings.setBitsPerPixel(bitsPerPixel);
        appSettings.setFullscreen(savedSettings.getScreenConfig().isFullscreen());

        appSettings.setSamples(savedSettings.getPostProcessorSettings().getJmeSamples());

        jmeApp.setSettings(appSettings);
        jmeApp.setShowSettings(false);

        jmeApp.start();
    }

    public void applyPostSettings() {

        if (savedSettings.getAnistropicFilteringLevel().getLevel() > 0) {
            jmeApp.getAssetManager().addAssetEventListener(
                    new AnistropicFilteringListener(savedSettings.getAnistropicFilteringLevel().getJmeLevel()));
        }

    }

    @FXML
    public void closeButtonPressed(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void optionsButtonPressed(ActionEvent event) {

        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/options.fxml"));
            Parent root = fxmlLoader.load();

            OptionsController optionsController = fxmlLoader.getController();
            optionsController.setSettings(savedSettings);

            stage.setScene(new Scene(root));
            stage.setTitle("Options");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
