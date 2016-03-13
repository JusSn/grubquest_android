package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.grubquest.grubquest_android.Adapters.NavDrawerListAdapter;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.NavDrawerItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private LinearLayout drawerRel;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AuthData auth = new Firebase(GQConstants.DATABASE).getAuth();

        firebase = new Firebase(GQConstants.DATABASE).child("lolUsers").child(auth.getUid());

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.app_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        tabHost.addTab(setIndicator(this, tabHost.newTabSpec("Loot"),
                R.drawable.loot_icon, "Loot"), LootFragment.class, null);
        tabHost.addTab(setIndicator(this, tabHost.newTabSpec("Quests"),
                R.drawable.quest_icon, "Quests"), QuestsFragment.class, null);

        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.red_tab_selected);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.red_tab_selected);

        /** Nav Drawer Stuff **/
        String[] navMenuOptions = getResources().getStringArray(R.array.nav_array);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRel = (LinearLayout) findViewById(R.id.fuckme);
        ListView drawerList = (ListView) findViewById(R.id.list_slider_menu);
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();

        for (String option : navMenuOptions)
            navDrawerItems.add(new NavDrawerItem(option, R.drawable.warning));
        NavDrawerListAdapter adapter =
                new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new SlideMenuClickListener());

        GetRequest g = new GetRequest();
        g.execute();
    }

    private class GetRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String answer = "";
            String uri = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/" +
                    "realm?api_key=505034d8-0c69-4ff6-8dd4-253f89502783";

            try {
                URL real = new URL(uri);
                URLConnection con = real.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) response.append(inputLine);
                in.close();

                JSONObject obj = new JSONObject(response.toString());
                String hi = obj.getString("dd");

                Log.d("SHIT", hi);
                System.out.println(response.toString());

                answer = hi;
            } catch (Exception e) {
                Log.d("SHIT", e.toString());
            }

            return answer;
        }

        @Override
        protected void onPostExecute(final String message) {
            firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("summonerName").getValue().toString();
                    Integer img = Integer.parseInt(
                            dataSnapshot.child("profileIconId").getValue().toString());

                    /** Nav Image Stuff **/
                    ImageView profile = (ImageView) findViewById(R.id.slide_drawer_profile_img);
                    ImageLoader img_loader = ImageLoader.getInstance();
                    img_loader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

                    String string = String.format(Locale.US,
                            "http://ddragon.leagueoflegends.com/cdn/%s/img/profileicon/%d.png",
                            message, img);
                    img_loader.displayImage(string, profile);

                    TextView profile_name = (TextView) findViewById(R.id.slide_drawer_profile_text);
                    profile_name.setText(name);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            });
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

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            process(position);
            drawer.closeDrawer(drawerRel);
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
