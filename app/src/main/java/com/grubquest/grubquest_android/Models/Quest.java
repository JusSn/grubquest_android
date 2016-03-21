package com.grubquest.grubquest_android.Models;

import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Quest {
    private Map<String, String> statConvertMap = new HashMap<>();
    private String templateName;

    public Map<String, String> stringMap = new HashMap<>();

    public ArrayList<ProgressItem> progressList = new ArrayList<>();
    public long expirationTime;

    public Quest(DataSnapshot snapshot, Map<String, Long> progressMap) {
        /** convert Riot's stat names to their display names**/
        statConvertMap.put("win", "WINS");
        statConvertMap.put("totalHeal", "HEAL");
        // TODO: 3/20/16 add "damage dealt", "damage taken", "enemy jungle camps", etc. when needed
        for (DataSnapshot child : snapshot.getChildren())
            if (child.getValue() instanceof String)
                stringMap.put(child.getKey(), child.getValue(String.class));

        expirationTime = snapshot.child("expirationTime").getValue(Long.class);

        stringMap.put("name", stringMap.get("name").toLowerCase());
        stringMap.put("voidCoupon", stringMap.get("name") + "_void_loot");

        boolean isDelivery = snapshot.child("isDelivery").getValue(Boolean.class);
        if (isDelivery)
            stringMap.put("isDelivery", "delivery_white");
        else
            stringMap.put("isDelivery", "instore_white");

        snapshot = snapshot.child("completionParams");
        stringMap.put("partySize", "ic_" + String.valueOf(snapshot.child("partySize").getValue(Long.class)) + "_white");

        switch (stringMap.get("templateName")) {
            case "achievedStatTotalOrPlayedGames":
                long gamesReq = snapshot.child("numGamesPlayed").getValue(Long.class);
                long gamesHi = Long.parseLong(String.valueOf(progressMap.get("highestNumGamesPlayed")));
                progressList.add(new ProgressItem("GAMES", gamesHi, gamesReq));
                // fall through here intentional
            case "achievedStatTotal":
                String statName = snapshot.child("statName").getValue(String.class);
                statName = statConvertMap.get(statName);

                long statTotal = snapshot.child("statTotal").getValue(Long.class);
                long statHi = Long.parseLong(String.valueOf(progressMap.get("highestStatTotal")));
                progressList.add(new ProgressItem(statName, statHi, statTotal));
                break;
            default:
                Log.d("Quests.java error", "Unknown template " + templateName);
                break;
        }
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
