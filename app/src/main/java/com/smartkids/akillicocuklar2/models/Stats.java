package com.smartkids.akillicocuklar2.models;

public class Stats {

    private String konu;
    private int soru;
    private int dogru;
    private int yanlis;
    private int bos;
    private int points;
    private int image;


    public Stats(String konu, int soru, int dogru, int yanlis, int bos, int points, int image) {
        this.konu = konu;
        this.soru = soru;
        this.dogru = dogru;
        this.yanlis = yanlis;
        this.bos = bos;
        this.points = points;
        this.image = image;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public int getSoru() {
        return soru;
    }

    public void setSoru(int soru) {
        this.soru = soru;
    }

    public int getDogru() {
        return dogru;
    }

    public void setDogru(int dogru) {
        this.dogru = dogru;
    }

    public int getYanlis() {
        return yanlis;
    }

    public void setYanlis(int yanlis) {
        this.yanlis = yanlis;
    }

    public int getBos() {
        return bos;
    }

    public void setBos(int bos) {
        this.bos = bos;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
