package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.Intent;
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
import com.grubquest.grubquest_android.Adapters.QuestViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
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
     * Methods
     */
//    public String getResourceFromFirebase(DataSnapshot quest, String child) {
//        String[] array = quest.child(child).getValue().toString().split("/");
//        String answer = array[array.length - 1];
//        answer = answer.substring(0, answer.length() - 4);
//        return answer;
//    }

    public int getDrawable(String name) {
        return getResources().getIdentifier(name, "drawable",
                getContext().getPackageName());
    }

//    public String[] getIcons(DataSnapshot quest, String[] children) {
//        String[] array = new String[children.length];
//
//        for (int i = 0; i < children.length; i++) {
//            array[i] = getResourceFromFirebase(quest, children[i]);
//        }
//
//        return array;
//    }

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
                        String[] array = {"mobile_quest_icon", "redeemIcon",
                                "mobile_background_img", "mobile_restaurant_icon"};

                        //String[] array = {"questTypeIcon", "redeemIcon", "backgroundImg"};
//                        String[] icons = getIcons(snapshot, array);

//                        Log.d("FUCK", "questTypeIcon: " + icons[0] +
//                                "\nredeemIcon: " + icons[1] +
//                                "\nbackgroundImg: " + icons[2]);
//
//                        items.add(new Quest(icons[0],
//                                icons[1],
//                                snapshot.child("savings")
//                                        .getValue().toString(),
//                                icons[2],
//                                snapshot.child("frontDescription")
//                                        .getValue().toString(),
//                                snapshot.child("restaurant/address")
//                                        .getValue().toString(),
//                                icons[3]));
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

//            holder.companyIcon.setImageDrawable(ContextCompat
//                    .getDrawable(getContext(), getDrawable(quest.restaurant_icon)));
//            holder.questImage.setImageDrawable(ContextCompat
//                    .getDrawable(getContext(), getDrawable(quest.quest_image)));
//            holder.companyText.setText(quest.restaurant);
//            holder.icon1Image.setImageDrawable(ContextCompat
//                    .getDrawable(getContext(), getDrawable(quest.icon1)));
//            holder.icon2Image.setImageDrawable(ContextCompat
//                    .getDrawable(getContext(), getDrawable(quest.icon2)));
//            holder.questInfo.setText(quest.quest_info);
//            holder.offerSmallText.setText(quest.offer);

            //change name of stuff from items

            long expireDate = 10000; //System.currentTimeMillis - expire date of quest
            String questName = "Ayy fucking lmao";
            holder.startCardTimer(expireDate); //System.currentTimeMillis - expire date of quest
            GrubquestNotifier.grubquestNotify(getContext(), new Intent(getContext(),
                    LoginActivity.class), getString(R.string.quest_expire_soon), questName, expireDate);

            holder.chestIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                            .getBaseContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = layoutInflater.inflate(R.layout.sample_coupon_layout,
                            null, false);

                    couponPopup = new PopupWindow(layout, displayMetrics.widthPixels,
                            displayMetrics.heightPixels, true);
                    couponPopup.setContentView(layout);
                    couponPopup.showAtLocation(questRecyclerView, Gravity.CENTER, 0,
                            (int) (25 * displayMetrics.density));

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
