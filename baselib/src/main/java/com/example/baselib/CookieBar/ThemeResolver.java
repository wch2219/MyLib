package com.example.baselib.CookieBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;

public class ThemeResolver {

    public static int getColor(Context context, @AttrRes int attr) {
        return getColor(context, attr, 0);
    }

    static int getColor(Context context, @AttrRes int attr, int defaultColor) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getColor(0, defaultColor);
        } finally {
            a.recycle();
        }
    }
}
