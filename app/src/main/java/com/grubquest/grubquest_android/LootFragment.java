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

import java.util.List;

public class LootFragment extends Fragment {
    private RecyclerView lootRecyclerView;
    private RecyclerView.Adapter couponAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loot, container, false);
        RecyclerView loot_list = (RecyclerView) view.findViewById(R.id.loot_recycler_view);
        loot_list.setHasFixedSize(true);

        //might try getActivity.getContext();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        loot_list.setLayoutManager(llm);

        /**
         * get shit from firebase and put in the thingy
         */

        // CouponAdapter ca = new CouponAdapter();
        // loot_list.setAdapter(ca);

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

    public static class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {
        private List<CouponInfo> contactList;

        public CouponAdapter(List<CouponInfo> contactList) {
            this.contactList = contactList;
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }

        @Override
        public void onBindViewHolder(CouponViewHolder couponViewHolder, int i) {

        }

        @Override
        public CouponViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.coupon_view, viewGroup, false);

            return new CouponViewHolder(itemView);
        }


        public class CouponInfo {
            //add stuff to this
            public CouponInfo() {
                //stuff
            }
        }

        public static class CouponViewHolder extends RecyclerView.ViewHolder {
            //add TextView and stuff
            //add TextView and stuff
            //add TextView and stuff
            //add TextView and stuff

            public CouponViewHolder(View v) {
                super(v);
            }
        }
    }
}
