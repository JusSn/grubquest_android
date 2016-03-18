package com.grubquest.grubquest_android.Data;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;

/**
 * Created by Derek on 1/30/2016.
 */
public class GQConstants {
    //TODO: Switch to reading from LIVE; need to get login info for an account to test on that actually accepts/completes quests, in order to test set of coupons/quests available to a unique user
    public static final String DATABASE = "https://grubquest.firebaseio.com";
//    public static final String DATABASE = "https://grubquest-live.firebaseio.com/";
    static float[] colorMatrix_Negative = {
            -1.0f, 0, 0, 0, 255, //red
            0, -1.0f, 0, 0, 255, //green
            0, 0, -1.0f, 0, 255, //blue
            0, 0, 0, 1.0f, 0 //alpha
    };

    public static final ColorFilter COLORFILTER_NEGATIVE = new ColorMatrixColorFilter(colorMatrix_Negative);
}
