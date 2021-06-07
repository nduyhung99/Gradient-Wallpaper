package com.example.gradientwallpaper;

import java.io.Serializable;

public class GradientSaved implements Serializable {
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public GradientSaved() {
    }

    public GradientSaved(String imagePath) {
        this.imagePath = imagePath;
    }
}
