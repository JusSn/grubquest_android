package com.grubquest.grubquest_android.Utility;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grubquest.grubquest_android.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LootViewHolder extends RecyclerView.ViewHolder {
    public Map<String, ImageView> imageViewMap = new HashMap<>();
    public Map<String, TextView> textViewMap = new HashMap<>();

    public final Button redeemButton;
    public final RelativeLayout lootTopLayout;
    public final TextView couponTimer;
    final TextView offerInfo;

    private boolean expanded = false;

    public LootViewHolder(final View dataView) {
        super(dataView);

        imageViewMap.put("restaurantCodeName", (ImageView) dataView.findViewById(R.id.restaurantIcon));
        imageViewMap.put("backgroundImg", (ImageView) dataView.findViewById(R.id.backgroundImg));
        imageViewMap.put("isDelivery", (ImageView) dataView.findViewById(R.id.isDelivery));
        imageViewMap.put("partySize", (ImageView) dataView.findViewById(R.id.partySize));

        final TextView restaurantNameTextView = (TextView) dataView.findViewById(R.id.restaurantName);
        final TextView frontDescriptionTextView = (TextView) dataView.findViewById(R.id.frontDescription);
        offerInfo = (TextView) dataView.findViewById(R.id.description);

        textViewMap.put("restaurantName", restaurantNameTextView);
        textViewMap.put("frontDescription", frontDescriptionTextView);
        //TODO: change this description to match the coupon, nOT QUEST description once that is avail on FB
        textViewMap.put("lootDescription", offerInfo);
        couponTimer = (TextView) dataView.findViewById(R.id.time_remain_textview);

        redeemButton = (Button) dataView.findViewById(R.id.view_reward_button);
        lootTopLayout = (RelativeLayout) dataView.findViewById(R.id.loot_top_layout);

        lootTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandInfoView();
            }
        });

        frontDescriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandInfoView();
            }
        });
    }
    private void expandInfoView() {
        ViewGroup.LayoutParams params = offerInfo.getLayoutParams();
        if (!expanded) {
            expanded = true;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            offerInfo.setLayoutParams(params);
        } else {
            params.height = 0;
            offerInfo.setLayoutParams(params);
            expanded = false;
        }
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