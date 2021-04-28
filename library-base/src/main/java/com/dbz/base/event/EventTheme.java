package com.dbz.base.event;

public class EventTheme {
    private int theme;
    private int color;

    public EventTheme(int theme, int color) {
        this.theme = theme;
        this.color = color;
    }

    public int getTheme() {
        return theme;
    }

    public int getColor() {
        return color;
    }
}