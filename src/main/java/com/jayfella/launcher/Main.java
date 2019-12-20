package com.jayfella.launcher;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import java.io.File;

public class Main extends SimpleApplication implements ActionListener {

    public static void main(String... args) {

        JfxLauncher.initialize(Main.class);
        JfxLauncher.getInstance().setTitle("Some Amazing Game");
        JfxLauncher.getInstance().setSettingsFile(new File("./settings.json"));
        JfxLauncher.getInstance().show();

    }

    @Override
    public void simpleInitApp() {

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);

        JfxLauncher.getInstance().applySettings(fpp);

        // create a scene...
        Geometry box = new Geometry("Box", new Box(1,1,1));
        box.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        box.getMaterial().setColor("Color", ColorRGBA.Blue);

        rootNode.attachChild(box);

        // add a keybinding to test restart mode.
        inputManager.addMapping("restart", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(this, "restart");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {

        if (name.equals("restart") && !isPressed) {
            JfxLauncher.getInstance().setRestart(true);
            stop(true);
        }

    }

}
