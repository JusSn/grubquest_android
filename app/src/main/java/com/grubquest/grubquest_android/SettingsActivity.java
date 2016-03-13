package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPref =  getSharedPreferences(getString(R.string.shared_pref_file),
                Context.MODE_PRIVATE);

        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setTitle("Settings");

        Switch location_notif_switch = (Switch) findViewById(R.id.settings_location_switch);
        Switch loot_notif_switch = (Switch) findViewById(R.id.settings_loot_switch);
        Switch quest_notif_switch = (Switch) findViewById(R.id.settings_quest_switch);

        location_notif_switch.setChecked(sharedPref.getBoolean("Location_Notif", true));
        loot_notif_switch.setChecked(sharedPref.getBoolean("Loot_Notif", true));
        quest_notif_switch.setChecked(sharedPref.getBoolean("Quest_Notif", true));

        location_notif_switch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                writeToPref("Location_Notif", isChecked);
            }
        });

        loot_notif_switch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                writeToPref("Loot_Notif", isChecked);
            }
        });

        quest_notif_switch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 writeToPref("Quest_Notif", isChecked);
            }
        });
    }

    private void writeToPref(String to_where, boolean value) {
        edit = sharedPref.edit();
        edit.putBoolean(to_where, value);
        edit.apply();
    }
}
