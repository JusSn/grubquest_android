package com.grubquest.grubquest_android;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.grubquest.grubquest_android.Adapters.NavDrawerListAdapter;
import com.grubquest.grubquest_android.Models.NavDrawerItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private LinearLayout drawerRel;

    // uncomment when we have designs
    // private TypedArray navMenuIcons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.app_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        tabHost.addTab(tabHost.newTabSpec("Loot").setIndicator("Loot"),
                LootFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Quests").setIndicator("Quests"),
                QuestsFragment.class, null);

        /** Nav Drawer Stuff **/

        String[] navMenuOptions = getResources().getStringArray(R.array.nav_array);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRel = (LinearLayout) findViewById(R.id.fuckme);
        ListView drawerList = (ListView) findViewById(R.id.list_slider_menu);
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();

        for (String option : navMenuOptions) {
            navDrawerItems.add(new NavDrawerItem(option, R.drawable.warning));
        }

        NavDrawerListAdapter adapter =
                new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());

        /* set image in drawer to something */
    }

    /** Helper Classes Methods **/
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            process(position);
            drawer.closeDrawer(drawerRel);
        }
    }

    private void process(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                /** this means logout **/
                //logout
                break;
        }
    }
}
