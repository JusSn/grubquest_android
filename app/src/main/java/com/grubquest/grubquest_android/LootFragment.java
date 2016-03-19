package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.grubquest.grubquest_android.Adapters.LootViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.Loot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LootFragment extends Fragment {
    private ArrayList<Loot> items = new ArrayList<>();
    private DisplayMetrics displayMetrics;
//    private PopupWindow deleteWarnPopup;
    private PopupWindow locTurnOnPopup;
    private RecyclerView lootRecyclerView;
    private RelativeLayout emptyRecyclerView;
    private ViewGroup container;
    private Set<String> completedQuests = new HashSet<>();
    private String authId;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loot, container, false);

        this.container = container;
        displayMetrics = getResources().getDisplayMetrics();
        emptyRecyclerView = (RelativeLayout) view.findViewById(R.id.loot_empty_recycler_view);
        lootRecyclerView = (RecyclerView) view.findViewById(R.id.loot_recycler_view);
        lootRecyclerView.setHasFixedSize(true);
        lootRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyRecyclerView.setVisibility(View.VISIBLE);
        lootRecyclerView.setVisibility(View.GONE);

        Firebase userQuestsRef = new Firebase(GQConstants.DATABASE).child("lolUsers");
        authId = userQuestsRef.getAuth().getUid();
        userQuestsRef = userQuestsRef.child(authId).child("acceptedQuests");
//        Firebase grubQuestRef = new Firebase(GQConstants.DATABASE);
//        auth = grubQuestRef.getAuth();

        userQuestsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Boolean complete = dataSnapshot.child("completed").getValue(Boolean.class);
                if (complete)
//                    Toast.makeText(getContext(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    completedQuests.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Boolean complete = dataSnapshot.child("completed").getValue(Boolean.class);
                if (complete)
//                    Toast.makeText(getContext(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    completedQuests.add(dataSnapshot.getKey());
                else
                    completedQuests.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getKey();
                completedQuests.remove(name);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase allQuestsRef = new Firebase(GQConstants.DATABASE).child("quests/LeagueOfLegends");
        LootAdapter adapter = new LootAdapter(allQuestsRef);
        lootRecyclerView.setAdapter(adapter);
        return view;
    }

    /**********************************************************************************************
     * Methods
     */
    //TODO: still not implemented, make sure to add permission on manifest as well
    public boolean locationCheck() {
        return true;
    }

    public int getDrawable(String name) {
        try {
            return getResources().getIdentifier(name, "drawable",
                    getContext().getPackageName());
        }
        catch (Exception e) {
            return R.color.darkRed;
        }
    }

    /**********************************************************************************************
     * Classes
     */
    public class LootAdapter extends RecyclerView.Adapter<LootViewHolder> {
        public LootAdapter(Firebase ref) {
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot questsSnapshot) {
                    items.clear();
//                    completedQuests.clear();

                    for (String completedName : completedQuests) {
                        items.add(new Loot(questsSnapshot.child(completedName)));
                    }
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        String name = postSnapshot.child("name").getValue().toString();
//
//                        //TODO:add rest of items to coupon and modify couponviewholder
//                        items.add(new Loot(postSnapshot));
//                    }

                    if (items.size() == 0) {
                        emptyRecyclerView.setVisibility(View.VISIBLE);
                        lootRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyRecyclerView.setVisibility(View.GONE);
                        lootRecyclerView.setVisibility(View.VISIBLE);
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
        public void onBindViewHolder(LootViewHolder holder, int position) {
            final Loot loot = items.get(position);
            Iterator tvIt = holder.textViewMap.entrySet().iterator();
            while (tvIt.hasNext()) {
                Map.Entry pair = (Map.Entry) tvIt.next();
                ((TextView)pair.getValue()).setText(loot.stringMap.get(pair.getKey()));
            }
            Iterator ivIt = holder.imageViewMap.entrySet().iterator();
            while (ivIt.hasNext()) {
                Map.Entry pair = (Map.Entry) ivIt.next();

                String name = loot.stringMap.get(pair.getKey());
                if (name != null && name.endsWith("white")) {
                    Drawable d = getResources().getDrawable(getDrawable(name));
                    d.setColorFilter(GQConstants.COLORFILTER_NEGATIVE);
                    ((ImageView) pair.getValue()).setImageDrawable(d);
                }
                else
                    ((ImageView) pair.getValue()).setImageResource(getDrawable(name));
            }
            //TODO: figure out wtf we're gonna do with all these different colored icons with ambiguous fucking names


            //TODO: change data of rest of stuff
            //TODO: for some reason timer is being set across all cards when only the last loot had a valid expiration date, not sure if I'm going insane. Maybe move startTimer() to this class instead of viewHolder
            long expireTimeLeft = loot.expirationTime - System.currentTimeMillis();
            String restName = loot.stringMap.get("frontDescription");
            holder.startCardTimer(expireTimeLeft);
            GrubquestNotifier.grubquestNotify(getContext(), new Intent(getContext(),
                    LoginActivity.class), getString(R.string.loot_expire_soon), restName, expireTimeLeft - 86400000); //One day prior to loot expiration

            holder.redeemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!locationCheck()) {
                        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                                .getBaseContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = layoutInflater.inflate(R.layout.turn_on_location_layout,
                                container, false);

                        locTurnOnPopup = new PopupWindow(layout, displayMetrics.widthPixels,
                                displayMetrics.heightPixels, true);
                        locTurnOnPopup.setContentView(layout);
                        locTurnOnPopup.showAtLocation(lootRecyclerView, Gravity.CENTER, 0,
                                (int) (25 * displayMetrics.density));

                        ImageView closeIcon = (ImageView) layout.findViewById(R.id.close_icon);
                        closeIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                locTurnOnPopup.dismiss();
                            }
                        });
                    }
                    else {
                        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                                .getBaseContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = layoutInflater.inflate(R.layout.sample_coupon_layout,
                                null, false);

                        final PopupWindow couponPopup = new PopupWindow(layout, displayMetrics.widthPixels,
                                displayMetrics.heightPixels, true);
                        couponPopup.setContentView(layout);
                        couponPopup.showAtLocation(lootRecyclerView, Gravity.CENTER, 0,
                                (int) (25 * displayMetrics.density));

                        Button closeButton = (Button) layout.findViewById(R.id.close_button);
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                couponPopup.dismiss();
                                //program w/e
                            }
                        });

                        ImageView coupon_image = (ImageView) layout.findViewById(R.id.coupon_image);
                        coupon_image.setImageResource(getDrawable(loot.stringMap.get("emailCoupon")));
                    }
                }
            });

//            holder.cancelButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LayoutInflater layoutInflater = (LayoutInflater)getActivity()
//                            .getBaseContext()
//                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View layout = layoutInflater.inflate(R.layout.delete_warn_popup,
//                            null, false);
//
//                    deleteWarnPopup = new PopupWindow(layout, displayMetrics.widthPixels,
//                            displayMetrics.heightPixels, true);
//                    deleteWarnPopup.setContentView(layout);
//                    deleteWarnPopup.showAtLocation(lootRecyclerView, Gravity.CENTER, 0,
//                            (int) (25 * displayMetrics.density));
//
//                    Button cancelDeleteButton =
//                            (Button) layout.findViewById(R.id.cancel_delete_button);
//                    cancelDeleteButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            deleteWarnPopup.dismiss();
//                        }
//                    });
//                }
//            });
        }

        @Override
        public LootViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_coupon, parent, false);
            return new LootViewHolder(view);
        }

        @Override
        public int getItemCount() {
            Log.d("NICERE", Integer.toString(items.size()));
            return items.size();
        }
    }
}
