package com.grubquest.grubquest_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grubquest.grubquest_android.Adapters.LootRecyclerAdapter;
import com.grubquest.grubquest_android.Adapters.QuestRecyclerAdapter;
import com.grubquest.grubquest_android.Models.Quest;

import java.util.ArrayList;

public class QuestsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        ArrayList<Quest> quests = new ArrayList<>();

        if (quests.size() > 0) {
            view = inflater.inflate(R.layout.fragment_quests, container, false);
            RecyclerView quest_recycler_view =
                    (RecyclerView) view.findViewById(R.id.quest_recycler_view);
            RecyclerView.Adapter couponAdapter = new QuestRecyclerAdapter(getActivity(), quests);
            quest_recycler_view.setAdapter(couponAdapter);
            quest_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            view = inflater.inflate(R.layout.empty_recycler_view_fragment, container, false);
            //ImageView empty_image = (ImageView) view.findViewById(R.id.empty_quest_image);
            TextView empty_text = (TextView) view.findViewById(R.id.empty_screen_text_view);
            empty_text.setText(getResources().getString(R.string.empty_loot_text));
        }

        return view;
    }
}
