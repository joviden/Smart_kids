package com.smartkids.akillicocuklar2.models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Character {

    private List<String> names;
    private String level;
    private List<Integer> images;

    public Character(List<String> names, String level, List<Integer>  images) {
        this.names = names;
        this.level = level;
        this.images = images;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }
}
