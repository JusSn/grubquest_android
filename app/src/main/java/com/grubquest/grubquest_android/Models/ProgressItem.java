package com.grubquest.grubquest_android.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justin on 3/20/16.
 */
public class ProgressItem {
    private Map<String, String> statMap = new HashMap<>();
    public String statDisplayName;
    public int progress;

    public ProgressItem(String statKey) {
        statDisplayName = statMap.get(statKey);
    }
}
