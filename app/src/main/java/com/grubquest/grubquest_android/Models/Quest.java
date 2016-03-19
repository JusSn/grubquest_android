package com.grubquest.grubquest_android.Models;

import com.firebase.client.DataSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Quest {
    public Map<String, String> stringMap = new HashMap<>();
    public long expirationTime;

    public Quest(DataSnapshot snapshot) {
        stringMap.put("restaurantName", snapshot.child("restaurant").child("name").getValue().toString());

        //// TODO: 3/19/16 recurse into children to get strings
        for (DataSnapshot child : snapshot.getChildren())
            if (child.getValue() instanceof String)
                stringMap.put(child.getKey(), getResourceFromFirebase(child.getValue().toString()));

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
}
