package com.grubquest.grubquest_android.Models;

import com.firebase.client.DataSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Coupon {
//    public String restaurantName = null;
//            frontDescription = null,
//            description = null,
//            mobile_loot_background_img = null,
//            mobile_method_icon = null,
//            mobile_quest_icon = null;
    public Map<String, String> stringMap;
    public long expirationTime;

    public Coupon(String title) {
        stringMap.put("restaurantName", title);
    }
    public Coupon(DataSnapshot snapshot) {
        stringMap = new HashMap<>();
        stringMap.put("restaurantName", snapshot.child("restaurant").child("name").getValue().toString());
//        this.frontDescription = snapshot.child("frontDescription").getValue().toString();
//        this.description = snapshot.child("description").getValue().toString();
//        this.mobile_loot_background_img = snapshot.child("mobile_loot_background_img").getValue().toString();
//        this.mobile_method_icon = snapshot.child("mobile_method_icon").getValue().toString();
//        this.mobile_quest_icon
        for (DataSnapshot child : snapshot.getChildren()) {
            if (child.getValue() instanceof String) {
                stringMap.put(child.getKey(), getResourceFromFirebase(child.getValue().toString()));
            }
        }
        expirationTime = (long) snapshot.child("expirationTime").getValue();
    }
    public String getResourceFromFirebase(String fullPath) {
        if (fullPath != null) {
            String[] array = fullPath.split(Pattern.quote("/"));
            String answer = array[array.length - 1];
            array = answer.split(Pattern.quote("."));
            answer = array[0];
            return answer;
        }
        return null;
    }

    public String[] getIcons(DataSnapshot quest, String[] children) {
        String[] array = new String[children.length];

        for (int i = 0; i < children.length; i++) {
            array[i] = getResourceFromFirebase(children[i]);
        }

        return array;
    }

}