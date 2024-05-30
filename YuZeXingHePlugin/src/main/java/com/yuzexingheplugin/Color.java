package com.yuzexingheplugin;

public abstract class Color {

    private Color() {}

    public static String parseColour(String text) {
        return text.replace("&", "§").replace("§§", "&");
    }
}
