package com.jayfella.launcher.jme;

import com.jme3.asset.AssetEventListener;
import com.jme3.asset.AssetKey;
import com.jme3.asset.TextureKey;

public class AnistropicFilteringListener implements AssetEventListener {

    private static final String[] extensions = { "png", "jpg", "dds", "gif" };
    private final int level;

    public AnistropicFilteringListener(int level) {
        this.level = level;
    }

    @Override
    public void assetRequested(AssetKey key) {

        for (String ext : extensions) {
            if (key.getExtension().equalsIgnoreCase(ext)) {
                TextureKey textureKey = (TextureKey) key;
                textureKey.setAnisotropy(level);
            }
        }

    }

    @Override public void assetLoaded(AssetKey key) { }
    @Override public void assetDependencyNotFound(AssetKey parentKey, AssetKey dependentAssetKey) { }

}
