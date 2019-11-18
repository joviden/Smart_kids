package com.smartkids.akillicocuklar2.models;

public class Training {

    private String name;
    private int success;
    private int image;

    public Training(String name, int success, int image) {
        this.name = name;
        this.success = success;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
