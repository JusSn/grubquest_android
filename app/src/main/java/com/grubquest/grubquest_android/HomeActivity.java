package com.grubquest.grubquest_android;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        /**
         * Was used to test user login, can resurrect if issues
         *
        Firebase firebase = new Firebase(GQConstants.DATABASE);

        TextView view = (TextView) findViewById(R.id.check_login_textview);

        if (firebase.getAuth() != null) {
            view.setText("Logged In");
        } else view.setText("Not logged in");


        //add button view to this
        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_activity_top_button_bar_container,
                        new Quest_Loot_Button_Bar_Fragment()).commit();

        //add loot content view to it
        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_activity_content_container, new LootFragment(), "LOOT_FRAGMENT")
                .commit();

         */
    }
}
