package com.grubquest.grubquest_android.Adapters;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grubquest.grubquest_android.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CouponViewHolder extends RecyclerView.ViewHolder {
    public final Button redeemButton;
//    public final ImageView companyImage, companyIcon, icon1Image, icon2Image;
    public Map<String, ImageView> imageViewMap;
    public final LinearLayout offerTextLayout;
    public final TextView couponTimer;
    public Map<String, TextView> textViewMap;

    private boolean expanded = false;

    public CouponViewHolder(final View dataView) {
        super(dataView);
        imageViewMap = new HashMap<>();
        textViewMap = new HashMap<>();
//        companyIcon = (ImageView) dataView.findViewById(R.id.mobile_restaurant_icon);
//        companyImage = (ImageView) dataView.findViewById(R.id.restaurant_image);
        imageViewMap.put("mobile_restaurant_icon", (ImageView) dataView.findViewById(R.id.mobile_restaurant_icon));
        imageViewMap.put("mobile_restaurant_img", (ImageView) dataView.findViewById(R.id.mobile_restaurant_img));
        imageViewMap.put("mobile_method_icon", (ImageView) dataView.findViewById(R.id.mobile_method_icon));
        imageViewMap.put("mobile_quest_icon", (ImageView) dataView.findViewById(R.id.mobile_quest_icon));

        textViewMap.put("restaurantName", (TextView) dataView.findViewById(R.id.restaurantName));
        textViewMap.put("frontDescription", (TextView) dataView.findViewById(R.id.frontDescription));
        //TODO: change this description to match the coupon, nOT QUEST description once that is avail on FB
        textViewMap.put("description", (TextView) dataView.findViewById(R.id.description));
        couponTimer = (TextView) dataView.findViewById(R.id.time_remain_textview);
//        icon1Image = (ImageView) dataView.findViewById(R.id.type_icon_1);
//        icon2Image = (ImageView) dataView.findViewById(R.id.type_icon_2);
//        offerInfo = (TextView) dataView.findViewById(R.id.coupon_info_text);
//        offerSmallText = (TextView) dataView.findViewById(R.id.offer_textview);

//        cancelButton = (Button) dataView.findViewById(R.id.cancel_button);
        redeemButton = (Button) dataView.findViewById(R.id.view_reward_button);
        offerTextLayout = (LinearLayout) dataView.findViewById(R.id.offertext_layout);

        final TextView offerInfo = textViewMap.get("description");
        offerTextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expanded) {
                    expanded = true;
                    ViewGroup.LayoutParams params = offerInfo.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    offerInfo.setLayoutParams(params);
                } else {
                    offerInfo.performClick();
                    expanded = false;
                }
            }
        });

        offerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = offerInfo.getLayoutParams();
                params.height = 0;
                offerInfo.setLayoutParams(params);
            }
        });


    }
    public void startCardTimer(long expireTime) {

        new CountDownTimer(expireTime, 1000) { // adjust the milli seconds here depending on coupon expiration time
            public void onTick(long millisUntilFinished) {
                couponTimer.setText(String.format(Locale.US, "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                couponTimer.setText(R.string.expired);
            }
        }.start();
    }
}