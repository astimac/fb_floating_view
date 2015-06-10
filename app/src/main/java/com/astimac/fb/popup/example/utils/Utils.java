package com.astimac.fb.popup.example.utils;

import android.content.res.Resources;

/**
 * Created by alex on 6/10/15.
 */
public class Utils {

    /**
     * Converts dps to pixels.
     * @param dp Value in dp.
     * @param res Resources reference to get the screen density.
     * @return Value in pixels.
     */
    public static int dpToPixels(int dp, Resources res){
        return (int)(res.getDisplayMetrics().density * dp + 0.5f);
    }
}
