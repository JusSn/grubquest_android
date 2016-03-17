package com.grubquest.grubquest_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.grubquest.grubquest_android.Adapters.CouponViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.Coupon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
    //TODO: still not implemented, make sure to add permission on manifest as well
    public boolean locationCheck() {
        return false;
    }

    public String getResourceFromFirebase(DataSnapshot quest, String child) {
        String[] array = quest.child(child).getValue().toString().split("/");
        String answer = array[array.length - 1];
        answer = answer.substring(0, answer.length() - 4);
        return answer;
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

    public String[] getIcons(DataSnapshot quest, String[] children) {
        String[] array = new String[children.length];

        for (int i = 0; i < children.length; i++) {
            array[i] = getResourceFromFirebase(quest, children[i]);
        }

        return array;
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
                        String[] array = {"mobile_quest_icon", "redeemIcon",
                                "mobile_background_img", "mobile_restaurant_icon"};
                        String[] icons = getIcons(postSnapshot, array);

                        String name = postSnapshot.child("title").getValue().toString();

                        //TODO:add rest of items to coupon and modify couponviewholder
                        items.add(new Coupon(postSnapshot));
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
//            holder.companyText.setText(coupon.restaurantName);
//            holder.offerSmallText.setText(coupon.frontDescription);
//            holder.offerInfo.setText(coupon.description);
//            holder.companyImage.setImageResource(getDrawable(coupon.mobile_loot_background_img));
//            holder.companyIcon.setImageResource(getDrawable(coupon.));
            Iterator tvIt = holder.textViewMap.entrySet().iterator();
            while (tvIt.hasNext()) {
                Map.Entry pair = (Map.Entry) tvIt.next();
                ((TextView)pair.getValue()).setText(coupon.stringMap.get(pair.getKey()));
            }
            Iterator ivIt = holder.imageViewMap.entrySet().iterator();
            while (ivIt.hasNext()) {
                Map.Entry pair = (Map.Entry) ivIt.next();
                ((ImageView) pair.getValue()).setImageResource(getDrawable(coupon.stringMap.get(pair.getKey())));
            }
            //TODO: figure out wtf we're gonna do with all these different colored icons with ambiguous fucking names
            Drawable d = getResources().getDrawable(R.drawable.delivery_white);
            d.setColorFilter(GQConstants.COLORFILTER_NEGATIVE);
            holder.imageViewMap.get("mobile_method_icon").setImageDrawable(d);


            //TODO: change data of rest of stuff
            long expireTimeLeft = coupon.expirationTime - System.currentTimeMillis(); //System.currentTimeMillis - expire date of loot or whatever time before to give user time to use
            String restName = "Ayy fucking lmao loot";
            holder.startCardTimer(expireTimeLeft);
            GrubquestNotifier.grubquestNotify(getContext(), new Intent(getContext(),
                    LoginActivity.class), getString(R.string.loot_expire_soon), restName, expireTimeLeft);

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
