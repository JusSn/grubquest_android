package com.grubquest.grubquest_android.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justin on 3/20/16.
 */
public class ProgressItem {
    public String stat;
    public long highest;
    public long max;
    public ProgressItem (String s, long h, long m) {
        stat = s;
        highest = h;
        max = m;
    }

}
