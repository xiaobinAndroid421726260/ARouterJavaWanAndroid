package com.dbz.demo.bean;

public class ThemeBean {

    private int color;
    private int theme;
    private String colorName;
    private boolean isChoose;

    public ThemeBean(int color, int theme, String colorName, boolean isChoose) {
        this.color = color;
        this.theme = theme;
        this.colorName = colorName;
        this.isChoose = isChoose;
    }

    public int getColor() {
        return color;
    }

    public int getTheme() {
        return theme;
    }

    public String getColorName() {
        return colorName;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}