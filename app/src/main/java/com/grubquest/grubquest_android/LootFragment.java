package com.grubquest.grubquest_android;

import android.content.Context;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.grubquest.grubquest_android.Adapters.CouponViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.Coupon;

import java.util.ArrayList;

public class LootFragment extends Fragment {
    private ArrayList<Coupon> items = new ArrayList<>();
    private DisplayMetrics displayMetrics;
    private PopupWindow deleteWarnPopup;
    private PopupWindow locTurnOnPopup;
    private RecyclerView lootRecyclerView;
    private RelativeLayout emptyRecyclerView;
    private ViewGroup container;

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

        //Firebase couponRef = new Firebase(GQConstants.DATABASE).child("lolUsers");
        //couponRef = couponRef.child(couponRef.getAuth().getUid()).child("acceptedQuests");

        Firebase couponRef = new Firebase(GQConstants.DATABASE).child("quests/LeagueOfLegends");
        CouponAdapter adapter = new CouponAdapter(couponRef);
        lootRecyclerView.setAdapter(adapter);
        return view;
    }

    /**********************************************************************************************
     * Methods
     */
    //check for location services
    public boolean locationCheck() {
        return false;
    }

    /**********************************************************************************************
     * Classes
     */
    public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
        public CouponAdapter(Firebase ref) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    items.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String name = postSnapshot.child("name").getValue().toString();
                        items.add(new Coupon(name));
                    }

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
        public void onBindViewHolder(CouponViewHolder holder, int position) {
            Coupon coupon = items.get(position);
            holder.companyText.setText(coupon.name);

            //TODO: change data of rest of stuff

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
                }
            });

            holder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater)getActivity()
                            .getBaseContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = layoutInflater.inflate(R.layout.delete_warn_popup,
                            null, false);

                    deleteWarnPopup = new PopupWindow(layout, displayMetrics.widthPixels,
                            displayMetrics.heightPixels, true);
                    deleteWarnPopup.setContentView(layout);
                    deleteWarnPopup.showAtLocation(lootRecyclerView, Gravity.CENTER, 0,
                            (int) (25 * displayMetrics.density));

                    Button cancelDeleteButton =
                            (Button) layout.findViewById(R.id.cancel_delete_button);
                    cancelDeleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteWarnPopup.dismiss();
                        }
                    });
                }
            });
        }

        @Override
        public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_coupon, parent, false);
            return new CouponViewHolder(view);
        }

        @Override
        public int getItemCount() {
            Log.d("NICERE", Integer.toString(items.size()));
            return items.size();
        }
    }
}
