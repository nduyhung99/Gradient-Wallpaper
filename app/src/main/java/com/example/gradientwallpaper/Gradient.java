package com.example.gradientwallpaper;

import java.io.Serializable;

public class Gradient implements Serializable {
    private int resGradient;
    private String name;

    public Gradient() {
    }

    public Gradient(int resGradient, String name) {
        this.resGradient = resGradient;
        this.name = name;
    }

    public int getResGradient() {
        return resGradient;
    }

    public void setResGradient(int resGradient) {
        this.resGradient = resGradient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
