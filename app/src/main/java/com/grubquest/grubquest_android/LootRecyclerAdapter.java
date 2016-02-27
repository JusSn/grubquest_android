package com.grubquest.grubquest_android;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Justin on 2/24/16.
 */
public class LootRecyclerAdapter extends RecyclerView.Adapter<LootRecyclerAdapter.CouponViewHolder> {

    private LayoutInflater inflater;
    //CouponFirebase couponFirebase;

    public LootRecyclerAdapter(Context context /*CouponFirebase couponsFromFirebase*/) {
        inflater = LayoutInflater.from(context);
        //this.couponFirebase = couponsFromFirebase
    }


    @Override
    public int getItemCount() {
        return 4;
        //return couponFirebase.count;
    }

    @Override
    public LootRecyclerAdapter.CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View coupon_view = inflater.inflate(R.layout.layout_coupon, parent, false);

        CouponViewHolder couponViewHolder = new CouponViewHolder(coupon_view);
        return couponViewHolder;
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        holder.company_text.setText("Hello, World!");
    }

    public static class CouponViewHolder extends RecyclerView.ViewHolder {
        //variables for coupon layout
        public final TextView company_text, offer_small_text, offer_info;
        public final ImageView company_image, company_icon,icon1_image, icon2_image;
        public LinearLayout offer_text_layout;
        String image_id;

        private boolean expanded = false;

        public CouponViewHolder(View dataView) {
            super(dataView);
            company_text = (TextView) dataView.findViewById(R.id.restaurant_textview);
            offer_small_text = (TextView) dataView.findViewById(R.id.offer_textview);

            offer_info = (TextView) dataView.findViewById(R.id.coupon_info_text);

            company_image = (ImageView) dataView.findViewById(R.id.restaurant_image);
            company_icon = (ImageView) dataView.findViewById(R.id.restaurant_icon_image);
            icon1_image = (ImageView) dataView.findViewById(R.id.type_icon_1);
            icon2_image = (ImageView) dataView.findViewById(R.id.type_icon_2);

            offer_text_layout = (LinearLayout) dataView.findViewById(R.id.offertext_layout);

            offer_text_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!expanded) {
                        expanded = true;
                        ViewGroup.LayoutParams params = offer_info.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        offer_info.setLayoutParams(params);
                    }
                    else {
                        offer_info.performClick();
                        expanded = false;
                    }
                }
            });

            offer_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup.LayoutParams params = offer_info.getLayoutParams();
                    params.height = 0;
                    offer_info.setLayoutParams(params);
                }
            });
        }
    }

}
