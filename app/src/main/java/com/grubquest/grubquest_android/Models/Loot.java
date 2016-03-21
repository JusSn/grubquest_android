package com.grubquest.grubquest_android.Models;

import com.firebase.client.DataSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Loot {
    public Map<String, String> stringMap = new HashMap<>();
    private String shortName, questName;
    public long expirationTime;

    public Loot(DataSnapshot snapshot) {
        for (DataSnapshot child : snapshot.getChildren())
            // TODO: 3/21/16 try casting to map
            if (child.getValue() instanceof String)
                stringMap.put(child.getKey(), child.getValue(String.class));

        shortName = stringMap.get("restaurantName");
        questName = snapshot.getKey();

        if (shortName != null) {
            shortName = shortName.replace(" ", "_").toLowerCase();
            stringMap.put("backgroundImg", shortName + "_loot");
        }
        stringMap.put("questName",questName);
        expirationTime = (long) snapshot.child("expirationTime").getValue();
    }

//    public String getResourceFromFirebase(String fullPath) {
//        if (fullPath != null) {
//            String[] array = fullPath.split(Pattern.quote("/"));
//            String answer = array[array.length - 1];
//            array = answer.split(Pattern.quote("."));
//            answer = array[0];
//            return answer;
//        }
//        return null;
//    }
}