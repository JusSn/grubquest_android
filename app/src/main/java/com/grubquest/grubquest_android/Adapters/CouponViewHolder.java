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

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CouponViewHolder extends RecyclerView.ViewHolder {
    public final Button cancelButton, redeemButton;
    public final ImageView companyImage, companyIcon, icon1Image, icon2Image;
    public final LinearLayout offerTextLayout;
    public final TextView companyText, offerSmallText, offerInfo, couponTimer;

    private boolean expanded = false;

    public CouponViewHolder(final View dataView) {
        super(dataView);

        companyIcon = (ImageView) dataView.findViewById(R.id.restaurant_icon_image);
        companyImage = (ImageView) dataView.findViewById(R.id.restaurant_image);
        companyText = (TextView) dataView.findViewById(R.id.restaurant_textview);
        couponTimer = (TextView) dataView.findViewById(R.id.time_remain_textview);
        icon1Image = (ImageView) dataView.findViewById(R.id.type_icon_1);
        icon2Image = (ImageView) dataView.findViewById(R.id.type_icon_2);
        offerInfo = (TextView) dataView.findViewById(R.id.coupon_info_text);
        offerSmallText = (TextView) dataView.findViewById(R.id.offer_textview);

        cancelButton = (Button) dataView.findViewById(R.id.cancel_button);
        redeemButton = (Button) dataView.findViewById(R.id.view_reward_button);

        new CountDownTimer(10000, 1000) { // adjust the milli seconds here depending on coupon expiration time
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
//                NotificationCompat.Builder expireNotifBuilder =
//                        (NotificationCompat.Builder) new NotificationCompat.Builder(dataView.getContext())
//                        .setSmallIcon(R.drawable.warning)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");
            }
        }.start();

        offerTextLayout = (LinearLayout) dataView.findViewById(R.id.offertext_layout);

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
}