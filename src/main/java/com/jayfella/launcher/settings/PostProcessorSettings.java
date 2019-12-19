package com.jayfella.launcher.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.post.ssao.SSAOFilter;

public class PostProcessorSettings {

    private int samples = 16;
    private boolean ssao = true;
    private boolean bloom = true;
    private boolean depthOfField = true;

    public PostProcessorSettings() {

    }

    public void apply(FilterPostProcessor fpp) {

        if (ssao) {
            fpp.addFilter(new SSAOFilter());
        }

        if (bloom) {
            fpp.addFilter(new BloomFilter());
        }

        if (depthOfField) {
            fpp.addFilter(new DepthOfFieldFilter());
        }

        if (samples > 0) {
            fpp.setNumSamples(getJmeSamples());
            FXAAFilter fxaaFilter = new FXAAFilter();
            fpp.addFilter(fxaaFilter);
        }

    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }

    public boolean isSsao() {
        return ssao;
    }

    public void setSsao(boolean ssao) {
        this.ssao = ssao;
    }

    public boolean isBloom() {
        return bloom;
    }

    public void setBloom(boolean bloom) {
        this.bloom = bloom;
    }

    public boolean isDepthOfField() {
        return depthOfField;
    }

    public void setDepthOfField(boolean depthOfField) {
        this.depthOfField = depthOfField;
    }

    @JsonIgnore
    public int getJmeSamples() {

        switch (samples) {
            case 1: return 2;
            case 2: return 4;
            case 3: return 8;
            case 4: return 16;
            default: return 1;
        }
    }

}
