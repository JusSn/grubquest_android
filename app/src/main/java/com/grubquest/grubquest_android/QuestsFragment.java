package com.grubquest.grubquest_android;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

//import com.grubquest.grubquest_android.Adapters.QuestRecyclerAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.grubquest.grubquest_android.Adapters.QuestViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.Coupon;
import com.grubquest.grubquest_android.Models.Quest;

import java.util.ArrayList;

public class QuestsFragment extends Fragment {
    private ArrayList<Quest> items = new ArrayList<>();
    private DisplayMetrics displayMetrics;
    private PopupWindow couponPopup;
    private RecyclerView questRecyclerView;
    private RelativeLayout emptyRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quests, container, false);
        Firebase ref = new Firebase(GQConstants.DATABASE);
        ref = ref.child("quests/LeagueOfLegends");

        displayMetrics = getResources().getDisplayMetrics();
        emptyRecyclerView = (RelativeLayout) view.findViewById(R.id.quests_empty_recycler_view);
        questRecyclerView = (RecyclerView) view.findViewById(R.id.quest_recycler_view);
        questRecyclerView.setHasFixedSize(true);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyRecyclerView.setVisibility(View.VISIBLE);
        questRecyclerView.setVisibility(View.GONE);

        QuestAdapter adapter = new QuestAdapter(ref);
        questRecyclerView.setAdapter(adapter);
        return view;
    }

    /**********************************************************************************************
     * Classes
     */
    public class QuestAdapter extends RecyclerView.Adapter<QuestViewHolder> {
        public QuestAdapter(Firebase ref) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    items.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        items.add(new Quest(snapshot.child("questTypeIcon")
                                .getValue().toString(),
                                snapshot.child("redeemIcon")
                                        .getValue().toString(),
                                snapshot.child("savings")
                                        .getValue().toString(),
                                snapshot.child("backgroundImg")
                                        .getValue().toString(),
                                snapshot.child("frontDescription")
                                        .getValue().toString(),
                                snapshot.child("restaurant/address")
                                        .getValue().toString(),
                                snapshot.child("restaurant/address")
                                        .getValue().toString()));
                    }

                    if (items.size() == 0) {
                        emptyRecyclerView.setVisibility(View.VISIBLE);
                        questRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyRecyclerView.setVisibility(View.GONE);
                        questRecyclerView.setVisibility(View.VISIBLE);
                    }

                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("FIREBASEERROR", firebaseError.getMessage());
                }
            });
        }

        @Override
        public void onBindViewHolder(QuestViewHolder holder, int position) {
            Quest quest = items.get(position);

            //change name of stuff from items

            holder.chestIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater)getActivity()
                            .getBaseContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = layoutInflater.inflate(R.layout.sample_coupon_layout,
                            null, false);

                    couponPopup = new PopupWindow(layout, displayMetrics.widthPixels,
                            displayMetrics.heightPixels, true);
                    couponPopup.setContentView(layout);
                    couponPopup.showAtLocation(questRecyclerView, Gravity.CENTER, 0,
                            (int) (25 * displayMetrics.density));

                    Button close_button = (Button) layout.findViewById(R.id.close_button);
                    close_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            couponPopup.dismiss();
                            //program w/e
                        }
                    });
                }
            });
        }

        @Override
        public QuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_quest, parent, false);
            return new QuestViewHolder(view);
        }

        @Override
        public int getItemCount() { return items.size(); }
    }
}
