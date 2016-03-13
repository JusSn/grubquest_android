package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.grubquest.grubquest_android.Adapters.NavDrawerListAdapter;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.NavDrawerItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private LinearLayout drawerRel;
    // private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.app_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        tabHost.addTab(setIndicator(this, tabHost.newTabSpec("Loot"), R.drawable.loot_icon, "Loot"),
                LootFragment.class, null);
        tabHost.addTab(setIndicator(this, tabHost.newTabSpec("Quests"),R.drawable.quest_icon, "Quests"),
                QuestsFragment.class, null);
//        tabHost.addTab(tabHost.newTabSpec("Quests").setIndicator("", getResources().getDrawable(R.drawable.quest_icon)),
//                QuestsFragment.class, null);
//
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.red_tab_selected);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.red_tab_selected);

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

        /** set image in drawer to something
        ImageView profile = (ImageView) findViewById(R.id.slide_drawer_profile_img);
        profile.setImageBitmap();

        TextView profile_name = (TextView) findViewById(R.id.slide_drawer_profile_text);
        profile_name.setText();
        **/
    }

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
                Firebase ref = new Firebase(GQConstants.DATABASE);
                ref.unauth();
                Intent exit_intent = new Intent(this, LoginActivity.class);
                exit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exit_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit_intent);
                finish();
        }
    }
    public TabHost.TabSpec setIndicator(Context ctx,TabHost.TabSpec spec, int resId,String name) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.tabhost_row, null);
        TextView textTab = (TextView) v.findViewById(R.id.textTab);
        ImageView iconTab = (ImageView) v.findViewById(R.id.iconTab);
        iconTab.setImageDrawable(getResources().getDrawable(resId));
        textTab.setText(name);
        return spec.setIndicator(v);
    }
}
