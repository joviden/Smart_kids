package com.smartkids.akillicocuklar2.models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Character {

    private String name;
    private String level;
    private Integer image;

    public Character(String name, String level, Integer  image) {
        this.name = name;
        this.level = level;
        this.image = image;
    }

    public String getNames() {
        return name;
    }

    public void setNames(String names) {
        this.name = names;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
