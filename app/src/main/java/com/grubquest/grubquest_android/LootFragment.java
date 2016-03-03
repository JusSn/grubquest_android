//TODO: margins, background, padding for linear layout
//TODO: coupon integration and display with Firebase
//TODO: fix the darn buttons
//TODO: on-tap card expansion, consider switching to android:Cardview?

package com.grubquest.grubquest_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.grubquest.grubquest_android.Adapters.CouponViewHolder;
import com.grubquest.grubquest_android.Data.GQConstants;
import com.grubquest.grubquest_android.Models.QuestCoupon;

import java.util.ArrayList;

public class LootFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        final Firebase couponRef = new Firebase("https://grubquest.firebaseio.com/quests/LeagueOfLegends/TestDuo/frontDescription");

        //get coupons from Firebase, port to FirebaseCouponModel

        if (true) {
            view = inflater.inflate(R.layout.fragment_loot, container, false);
            RecyclerView lootRecyclerView =
                    (RecyclerView) view.findViewById(R.id.loot_recycler_view);

            lootRecyclerView.setHasFixedSize(true);

            RecyclerView.Adapter couponAdapter = new FirebaseRecyclerAdapter<QuestCoupon, CouponViewHolder>(QuestCoupon.class, R.layout.layout_coupon, CouponViewHolder.class, couponRef) {
                private LayoutInflater inflater = LayoutInflater.from(getActivity());

                @Override
                protected void populateViewHolder(CouponViewHolder viewHolder, QuestCoupon model, int position) {
                    viewHolder.company_text.setText(model.getName());
                }

                //
//                @Override
//                public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                    View coupon_view = inflater.inflate(R.layout.layout_coupon, parent, false);
//                    return new CouponViewHolder(coupon_view);
//
//                }
//
//                @Override
//                public void onBindViewHolder(CouponViewHolder viewHolder, int position) {
//                    viewHolder.company_text.setText("What the fuck why won't this work");
//                }

//                @Override
//                public int getItemCount() {
//                    return 1;
//                }
            };

            lootRecyclerView.setAdapter(couponAdapter);

            lootRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            view = inflater.inflate(R.layout.empty_recycler_view_fragment, container, false);
            //ImageView empty_image = (ImageView) view.findViewById(R.id.empty_quest_image);
            TextView empty_text = (TextView) view.findViewById(R.id.empty_screen_text_view);
            empty_text.setText(getResources().getString(R.string.empty_loot_text));
        }

        return view;
    }
}
