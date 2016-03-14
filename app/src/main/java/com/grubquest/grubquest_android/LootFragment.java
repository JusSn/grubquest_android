//TODO: Get the darn Firebase reference to display something to the view
//TODO: Create a proper model(s) to hold Firebase reference
//TODO: What the fuck is going on with onBindViewHolder() vs populateViewHoler()
//TODO: Attempt to use quests to display something from Firebase to check server connection, at least?

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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.grubquest.grubquest_android.Adapters.CouponViewHolder;
import com.grubquest.grubquest_android.Models.QuestCoupon;

public class LootFragment extends Fragment {
    private PopupWindow locTurnOnPopup;
    private PopupWindow deleteWarnPopup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        final Firebase couponRef = new Firebase("https://grubquest.firebaseio.com/quests/LeagueOfLegends/TestDuo/frontDescription");
        final DisplayMetrics displayMetrics=getResources().getDisplayMetrics();

        //get coupons from Firebase, port to FirebaseCouponModel


        if (true) {
            view = inflater.inflate(R.layout.fragment_loot, container, false);

            final RecyclerView lootRecyclerView =
                    (RecyclerView) view.findViewById(R.id.loot_recycler_view);

            lootRecyclerView.setHasFixedSize(true);
            lootRecyclerView.setBackgroundColor(Color.LTGRAY);
            lootRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            RecyclerView.Adapter couponAdapter = new FirebaseRecyclerAdapter<QuestCoupon, CouponViewHolder>(QuestCoupon.class, R.layout.layout_coupon, CouponViewHolder.class, couponRef) {
                private LayoutInflater inflater = LayoutInflater.from(getActivity());

                @Override
                protected void populateViewHolder(CouponViewHolder viewHolder, QuestCoupon model, int position) {
                    viewHolder.companyText.setText(model.getName());
                }

                //
                @Override
                public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View coupon_view = inflater.inflate(R.layout.layout_coupon, parent, false);
                    return new CouponViewHolder(coupon_view);

                }

                @Override
                public void onBindViewHolder(CouponViewHolder viewHolder, int position) {
                    viewHolder.redeemButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /** check if location services on, and check if location matches. Set to always show popup for testing **/
                            if (!locationCheck()) {
                                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View layout = layoutInflater.inflate(R.layout.turn_on_location_layout, null, false);

                                locTurnOnPopup = new PopupWindow(layout, displayMetrics.widthPixels, displayMetrics.heightPixels, true);
                                locTurnOnPopup.setContentView(layout);
                                locTurnOnPopup.showAtLocation(lootRecyclerView, Gravity.CENTER, 0, (int) (25 * displayMetrics.density));

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
                    viewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View layout = layoutInflater.inflate(R.layout.delete_warn_popup, null, false);

                            deleteWarnPopup = new PopupWindow(layout, displayMetrics.widthPixels, displayMetrics.heightPixels, true);
                            deleteWarnPopup.setContentView(layout);
                            deleteWarnPopup.showAtLocation(lootRecyclerView, Gravity.CENTER, 0, (int) (25 * displayMetrics.density));

                            Button cancelDeleteButton = (Button) layout.findViewById(R.id.cancel_delete_button);
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
                public int getItemCount() {
                    return 5;
                }
            };

            lootRecyclerView.setAdapter(couponAdapter);
        } else {
            view = inflater.inflate(R.layout.empty_recycler_view_fragment, container, false);
            //ImageView empty_image = (ImageView) view.findViewById(R.id.empty_quest_image);
            TextView empty_text = (TextView) view.findViewById(R.id.empty_screen_text_view);
            empty_text.setText(getResources().getString(R.string.empty_loot_text));
        }

        return view;
    }

    public boolean removeItem() {


        return false;
    }
    public boolean locationCheck() {return false;}

}
