package com.grubquest.grubquest_android.Models;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Quest {
    private Map<String, String> statConvertMap = new HashMap<>();

    public Map<String, String> stringMap = new HashMap<>();
    public ArrayList<ProgressItem> progressList = new ArrayList<>();
    public long expirationTime;

    public Quest(DataSnapshot snapshot, Map<String, Long> progressMap) {
        /** convert Riot's stat names to their display names**/
        statConvertMap.put("win", "WINS");
        // TODO: 3/20/16 add "damage dealt", "damage taken", "enemy jungle camps", etc. when needed

        DataSnapshot n = snapshot.child("restaurant").child("name");
        String name = "fml";
        if (n.exists()) {
            name = n.getValue().toString();
            stringMap.put("restaurantName", name);
        }

        //// TODO: 3/19/16 recurse into children to get strings
        for (DataSnapshot child : snapshot.getChildren())
            if (child.getValue() instanceof String)
                stringMap.put(child.getKey(), getResourceFromFirebase(child.getValue(String.class)));

        expirationTime = snapshot.child("expirationTime").getValue(Long.class);

        snapshot = snapshot.child("completionParams");
        if (snapshot.child("statName").exists() && snapshot.child("statTotal").exists()) {
            String statName = snapshot.child("statName").getValue(String.class);
            statName = statConvertMap.get(statName);

            long statTotal = snapshot.child("statTotal").getValue(Long.class);
            long statHi = Long.parseLong(String.valueOf(progressMap.get("highestStatTotal")));
            progressList.add(new ProgressItem(statName, statHi, statTotal));
        }
        if (snapshot.child("numGamesPlayed").exists()) {
            long gamesReq = snapshot.child("numGamesPlayed").getValue(Long.class);
            long gamesHi = Long.parseLong(String.valueOf(progressMap.get("highestNumGamesPlayed")));
            progressList.add(new ProgressItem("GAMES", gamesHi, gamesReq));
        }
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
