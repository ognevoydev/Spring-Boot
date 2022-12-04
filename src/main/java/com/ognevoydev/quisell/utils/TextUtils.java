package com.ognevoydev.quisell.utils;

public class TextUtils {

    private TextUtils(){}

    public static String getEntityName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

}
