package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.grubquest.grubquest_android.Adapters.QuestRecyclerAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.grubquest.grubquest_android.Utility.GrubquestNotifier;
import com.grubquest.grubquest_android.Utility.ProgressListAdapter;
import com.grubquest.grubquest_android.Utility.QuestViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuestsFragment extends Fragment {
    private ArrayList<Quest> items = new ArrayList<>();
    private Map<String, Map<String, Long>> userProgressMap = new HashMap<>();
    private DisplayMetrics displayMetrics;

    private ViewGroup container;

    private PopupWindow couponPopup;
    private RecyclerView questRecyclerView;
    private RelativeLayout emptyRecyclerView;

    private Set<String> incompleteQuests = new HashSet<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quests, container, false);

        this.container = container;
        displayMetrics = getResources().getDisplayMetrics();
        emptyRecyclerView = (RelativeLayout) view.findViewById(R.id.quests_empty_recycler_view);
        questRecyclerView = (RecyclerView) view.findViewById(R.id.quest_recycler_view);
        questRecyclerView.setHasFixedSize(true);
        questRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyRecyclerView.setVisibility(View.VISIBLE);
        questRecyclerView.setVisibility(View.GONE);

        Firebase userQuestsRef = new Firebase(GQConstants.DATABASE).child("lolUsers");
        String authId = userQuestsRef.getAuth().getUid();
        userQuestsRef = userQuestsRef.child(authId).child("acceptedQuests");

        final Firebase allQuestsRef = new Firebase(GQConstants.DATABASE).child("quests/LeagueOfLegends");

        refreshView(questRecyclerView, allQuestsRef);

        userQuestsRef.addChildEventListener(new ChildEventListener() {
            private void trackQuest(DataSnapshot dataSnapshot) {
                String questName = dataSnapshot.getKey();
                incompleteQuests.add(questName);
                try {
                    Map<String, Long> progress = dataSnapshot.child("progress").getValue(Map.class);
                    userProgressMap.put(questName, progress);
                } catch (Exception e) {
                    Toast.makeText(getContext(), questName + ": parameter error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Boolean complete = dataSnapshot.child("completed").getValue(Boolean.class);
                if (complete != null && !complete) {
                    trackQuest(dataSnapshot);
                    refreshView(questRecyclerView, allQuestsRef);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Boolean complete = dataSnapshot.child("completed").getValue(Boolean.class);
                if (complete != null && !complete) {
                    trackQuest(dataSnapshot);
                    refreshView(questRecyclerView, allQuestsRef);
                } else {
                    incompleteQuests.remove(dataSnapshot.getKey());
                    refreshView(questRecyclerView, allQuestsRef);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getKey();
                incompleteQuests.remove(name);
                refreshView(questRecyclerView, allQuestsRef);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        return view;
    }

    /**********************************************************************************************
     * Methods
     */
    private void refreshView(RecyclerView view, Firebase ref) {
        QuestAdapter newAdapter = new QuestAdapter(ref);
        view.setAdapter(newAdapter);
    }

    public int getDrawable(String name) {
        try {
            return getResources().getIdentifier(name, "drawable",
                    getContext().getPackageName());
        }
        catch (Exception e) { return R.color.darkRed; }
    }


    /**********************************************************************************************
     * Classes
     */
    public class QuestAdapter extends RecyclerView.Adapter<QuestViewHolder> {
        public ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            // TODO: 3/20/16 try one time listener to get quest metadata
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();

                for (String questName : incompleteQuests) {
                    items.add(new Quest(dataSnapshot.child(questName), userProgressMap.get(questName)));
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
        };

        public QuestAdapter(Firebase ref) { ref.addValueEventListener(valueEventListener); }

        @Override
        public void onBindViewHolder(QuestViewHolder holder, int position) {
            final Quest quest = items.get(position);

            // TODO: 3/19/16 nullchecks on loot too
            // TODO: 3/19/16 URGENT: QUEST PROGRESS DISPLAY
            for (Map.Entry pair : holder.textViewMap.entrySet()) {
                TextView t = (TextView) pair.getValue();
                if (t != null)
                    t.setText(Html.fromHtml(quest.stringMap.get(pair.getKey())));
            }

            for (Map.Entry pair : holder.imageViewMap.entrySet()) {
                ImageView i = (ImageView) pair.getValue();
                String name = quest.stringMap.get(pair.getKey());
                if (i != null && name != null)
                    i.setImageResource(getDrawable(name));
            }

            // TODO: 3/19/16 polish notification behavior
            long expireTimeLeft = quest.expirationTime - System.currentTimeMillis();
            String restName = quest.stringMap.get("title");
            holder.startCardTimer(expireTimeLeft);
            GrubquestNotifier.grubquestNotify(
                    getContext(),
                    new Intent(getContext(),
                            LoginActivity.class),
                    getString(R.string.quest_expire_soon),
                    restName,
                    R.drawable.quest_notifications,
                    GQConstants.DAY * 3 / 4); //three days prior to quest expiration

            /** Set current quest progress**/

            ProgressListAdapter adapter = new ProgressListAdapter(getContext(), quest.progressList);
            holder.progressListView.setAdapter(adapter);


            holder.chestIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                            .getBaseContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = layoutInflater.inflate(R.layout.sample_coupon_layout,
                            container, false);

                    couponPopup = new PopupWindow(layout, displayMetrics.widthPixels,
                            displayMetrics.heightPixels, true);
                    couponPopup.setContentView(layout);
                    couponPopup.showAtLocation(questRecyclerView, Gravity.CENTER, 0,
                            (int) (25 * displayMetrics.density));

                    ImageView coupon_image = (ImageView) layout.findViewById(R.id.coupon_image);
                    coupon_image.setImageResource(getDrawable(quest.stringMap.get("voidCoupon")));

                    Button closeButton = (Button) layout.findViewById(R.id.close_button);
                    closeButton.setOnClickListener(new View.OnClickListener() {
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
