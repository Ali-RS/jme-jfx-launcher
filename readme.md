JME JavaFX Launcher
---

Replaces the default Swing launcher with a customizable JavaFX launcher.

```java

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import java.io.File;

public class Main extends SimpleApplication {

    public static void main(String... args) {

        Main main = new Main();

        JfxLauncher.initialize(main);
        JfxLauncher.getInstance().setTitle("Some Amazing Game");
        JfxLauncher.getInstance().setSettingsFile(new File("./settings.json"));

        JfxLauncher.getInstance().setBackgroundImage("/mybackground.jpg");        

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
    }

}

```