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

public class LootFragment extends Fragment {
    private RecyclerView lootRecyclerView;
    private RecyclerView.Adapter couponAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loot, container, false);

        /**
         *
         * add Recycler View here wit Justin
         */
        lootRecyclerView = (RecyclerView) view.findViewById(R.id.lootRecyclerView);
        /**
         * get data from Firebase. One object that contains all coupons available to the user. Pass into adapter by constructor
         */
        couponAdapter = new LootRecyclerAdapter(getActivity());

        lootRecyclerView.setAdapter(couponAdapter);

        lootRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}
