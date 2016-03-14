package com.grubquest.grubquest_android;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

//import com.grubquest.grubquest_android.Adapters.QuestRecyclerAdapter;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.grubquest.grubquest_android.Adapters.QuestViewHolder;
import com.grubquest.grubquest_android.Models.QuestCoupon;

public class QuestsFragment extends Fragment {
    private PopupWindow couponPopup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        final Firebase questRef = new Firebase("https://grubquest.firebaseio.com/quests/LeagueOfLegends/TestDuo/frontDescription");
        final DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
//        ArrayList<Quest> quests = new ArrayList<>();
/*
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
        }*/
        view = inflater.inflate(R.layout.fragment_quests, container, false);
        final RecyclerView questRecyclerView =
                (RecyclerView) view.findViewById(R.id.quest_recycler_view);

        questRecyclerView.setHasFixedSize(true);
        questRecyclerView.setBackgroundColor(Color.LTGRAY);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.Adapter couponAdapter = new FirebaseRecyclerAdapter<QuestCoupon, QuestViewHolder>(QuestCoupon.class, R.layout.layout_quest, QuestViewHolder.class, questRef) {
            private LayoutInflater inflater = LayoutInflater.from(getActivity());

            @Override
            protected void populateViewHolder(QuestViewHolder viewHolder, QuestCoupon model, int position) {
                viewHolder.companyText.setText(model.getName());
            }

            //
            @Override
            public QuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View quest_view = inflater.inflate(R.layout.layout_quest, parent, false);
                return new QuestViewHolder(quest_view);

            }

            @Override
            public void onBindViewHolder(QuestViewHolder viewHolder, int position) {
                viewHolder.chestIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = layoutInflater.inflate(R.layout.sample_coupon_layout, null, false);

                        couponPopup = new PopupWindow(layout, displayMetrics.widthPixels, displayMetrics.heightPixels, true);
                        couponPopup.setContentView(layout);
                        couponPopup.showAtLocation(questRecyclerView, Gravity.CENTER, 0, (int) (25 * displayMetrics.density));

                        Button close_button = (Button) layout.findViewById(R.id.close_button);
                        close_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                couponPopup.dismiss();
                            }
                        });
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };

        questRecyclerView.setAdapter(couponAdapter);

        return view;
    }
}
