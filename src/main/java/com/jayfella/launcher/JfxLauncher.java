package com.jayfella.launcher;

import com.jayfella.launcher.controller.LauncherController;
import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class JfxLauncher extends Application {

    private static JfxLauncher INSTANCE;

    private Scene scene;
    private Stage primaryStage;
    private LauncherController launcherController;

    // drag handlers
    private double xOffset = 0;
    private double yOffset = 0;

    public static synchronized void initialize(Class<? extends SimpleApplication> gameClass) {

        new Thread(() -> launch(JfxLauncher.class)).start();

        while (INSTANCE == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        INSTANCE.launcherController.setJmeClass(gameClass);
    }

    public static synchronized JfxLauncher getInstance() {
        return INSTANCE;
    }

    public void setTitle(String title) {
        Platform.runLater(() -> primaryStage.setTitle(title));
        launcherController.setTitle(title);
    }

    public void setBitsPerPixel(int bpp) {
        launcherController.setBitsPerPixel(bpp);
    }

    public void setBackgroundImage(String resourceImage) {
        Platform.runLater(() -> launcherController.setBackgroundImage(resourceImage));
    }

    public void addCss(String... css) {
        scene.getStylesheets().addAll(css);
    }

    public void setSettingsFile(File file) {
        launcherController.setSettingsFile(file);
    }

    public void show() {
        Platform.runLater(() -> primaryStage.show());
    }

    public void applySettings(FilterPostProcessor fpp) {
        launcherController.applyPostProcessors(fpp);
        launcherController.applyPostSettings();
    }

    public void setRestart(boolean restart) {
        launcherController.setRestart(restart);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        FXMLLoader primaryLoader = new FXMLLoader(getClass().getResource("/launcher.fxml"));
        Parent root = primaryLoader.load();

        launcherController = primaryLoader.getController();

        scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);

        setBackgroundImage("/splash.png");

        addDragHandlers();

        INSTANCE = this;
    }

    private void addDragHandlers() {

        launcherController.getContainer().setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });

        launcherController.getContainer().setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });

    }


}
