package com.grubquest.grubquest_android;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.app_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        tabHost.addTab(tabHost.newTabSpec("Loot").setIndicator("Loot"),
                LootFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Quests").setIndicator("Quests"),
                QuestsFragment.class, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.home_drawer_layout);
        ListView lv = (ListView) drawer.findViewById(R.id.nav_list_views);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,
                getResources().getStringArray(R.array.nav_array)));
        lv.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        if (position == 1) {
            Fragment fragment = new SettingsFragment();
        } else {
            //log out
            //clear backstack
            //go to home page
        }
    }
}
