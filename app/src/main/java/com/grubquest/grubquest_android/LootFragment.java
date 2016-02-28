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

import com.grubquest.grubquest_android.Adapters.LootRecyclerAdapter;
import com.grubquest.grubquest_android.Models.FirebaseCoupon;

import java.util.ArrayList;

public class LootFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        ArrayList<FirebaseCoupon> coupons = new ArrayList<>();

        //get coupons from Firebase, port to FirebaseCouponModel

        if (coupons.size() > 0) {
            view = inflater.inflate(R.layout.fragment_loot, container, false);
            RecyclerView lootRecyclerView =
                    (RecyclerView) view.findViewById(R.id.loot_recycler_view);
            RecyclerView.Adapter couponAdapter = new LootRecyclerAdapter(getActivity(), coupons);
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
