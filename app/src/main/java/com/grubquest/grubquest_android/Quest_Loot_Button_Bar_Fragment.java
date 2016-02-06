package com.grubquest.grubquest_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Quest_Loot_Button_Bar_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest__loot__button__bar, container, false);

        Button loot_button = (Button) view.findViewById(R.id.loot_button);
        Button quest_button = (Button) view.findViewById(R.id.quests_button);

        loot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HELP", "get's here");
                FragmentTransaction ftr = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                ftr.replace(R.id.home_activity_content_container, new LootFragment(),
                        "LOOT_FRAGMENT");
                ftr.commit();
            }
        });

        quest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ftr = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                ftr.replace(R.id.home_activity_content_container, new QuestsFragment(),
                        "QUESTS_FRAGMENT");
                ftr.commit();
            }
        });

        return view;
    }
}

