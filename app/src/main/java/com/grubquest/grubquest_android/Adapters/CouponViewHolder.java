package com.grubquest.grubquest_android.Adapters;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grubquest.grubquest_android.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Justin on 3/2/16.
 */
public class CouponViewHolder extends RecyclerView.ViewHolder {
    public final TextView company_text, offer_small_text, offer_info, coupon_timer;
    public final ImageView company_image, company_icon,icon1_image, icon2_image;
    public LinearLayout offer_text_layout;
    String image_id;

    private boolean expanded = false;

    public CouponViewHolder(View dataView) {
        super(dataView);
        company_icon = (ImageView) dataView.findViewById(R.id.restaurant_icon_image);
        company_image = (ImageView) dataView.findViewById(R.id.restaurant_image);
        company_text = (TextView) dataView.findViewById(R.id.restaurant_textview);
        coupon_timer = (TextView) dataView.findViewById(R.id.time_remain_textview);
        icon1_image = (ImageView) dataView.findViewById(R.id.type_icon_1);
        icon2_image = (ImageView) dataView.findViewById(R.id.type_icon_2);
        offer_info = (TextView) dataView.findViewById(R.id.coupon_info_text);
        offer_small_text = (TextView) dataView.findViewById(R.id.offer_textview);

        new CountDownTimer(3000000, 1000) { // adjust the milli seconds here depending on coupon expiration time

            public void onTick(long millisUntilFinished) {
                coupon_timer.setText(String.format(Locale.US, "%d:%d:%d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                coupon_timer.setText(R.string.expired);
            }
        }.start();

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