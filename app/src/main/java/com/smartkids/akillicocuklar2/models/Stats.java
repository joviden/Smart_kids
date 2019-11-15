package com.smartkids.akillicocuklar2.models;

public class Stats {

    int soru;
    int dogru;
    int yanlis;
    int points;
    int total_soru;
    int total_dogru;
    int total_yanlis;
    int total_points;
    int level;
    int pointsto_nextlevel;

    public Stats(int soru, int dogru, int yanlis, int points, int total_soru, int total_dogru, int total_yanlis, int total_points, int level, int pointsto_nextlevel) {
        this.soru = soru;
        this.dogru = dogru;
        this.yanlis = yanlis;
        this.points = points;
        this.total_soru = total_soru;
        this.total_dogru = total_dogru;
        this.total_yanlis = total_yanlis;
        this.total_points = total_points;
        this.level = level;
        this.pointsto_nextlevel = pointsto_nextlevel;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTotal_soru() {
        return total_soru;
    }

    public void setTotal_soru(int total_soru) {
        this.total_soru = total_soru;
    }

    public int getTotal_dogru() {
        return total_dogru;
    }

    public void setTotal_dogru(int total_dogru) {
        this.total_dogru = total_dogru;
    }

    public int getTotal_yanlis() {
        return total_yanlis;
    }

    public void setTotal_yanlis(int total_yanlis) {
        this.total_yanlis = total_yanlis;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPointsto_nextlevel() {
        return pointsto_nextlevel;
    }

    public void setPointsto_nextlevel(int pointsto_nextlevel) {
        this.pointsto_nextlevel = pointsto_nextlevel;
    }
}
