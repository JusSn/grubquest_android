package com.grubquest.grubquest_android.Utility;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grubquest.grubquest_android.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Justin on 3/8/16.
 */
public class QuestViewHolder extends RecyclerView.ViewHolder {
    public Map<String, ImageView> imageViewMap = new HashMap<>();
    public Map<String, TextView> textViewMap = new HashMap<>();

    public ListView progressListView;

    public final TextView questTimer, description;
    public final ImageView chestIcon;
    public RelativeLayout restOfferLayout;

    private boolean expanded = false;

    public QuestViewHolder(final View dataView) {
        super(dataView);

        imageViewMap.put("restaurantCodeName", (ImageView) dataView.findViewById(R.id.restaurantIcon));
        imageViewMap.put("isDelivery", (ImageView) dataView.findViewById(R.id.isDelivery));
        imageViewMap.put("partySize", (ImageView) dataView.findViewById(R.id.partySize));
        imageViewMap.put("name", (ImageView) dataView.findViewById(R.id.questName));

        final TextView restaurantNameTextView = (TextView) dataView.findViewById(R.id.restaurantName);
        final TextView titleView = (TextView) dataView.findViewById(R.id.title);

        textViewMap.put("restaurantName", restaurantNameTextView);
        textViewMap.put("description", (TextView) dataView.findViewById(R.id.description));
        textViewMap.put("title", titleView);

        questTimer = (TextView) dataView.findViewById(R.id.time_remain_textview);
        description = (TextView) dataView.findViewById(R.id.description);

        chestIcon = (ImageView) dataView.findViewById(R.id.chest_icon);
        restOfferLayout = (RelativeLayout) dataView.findViewById(R.id.rest_offer_layout);

        progressListView = (ListView) dataView.findViewById(R.id.progress_list);

        restOfferLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandViewHolder();
            }
        });

        restaurantNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandViewHolder();
            }
        });

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandViewHolder();
            }
        });

    }
    private void expandViewHolder() {
        ViewGroup.LayoutParams params = description.getLayoutParams();
        if (!expanded) {
            expanded = true;

            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            description.setLayoutParams(params);
        } else {
            params.height = 0;
            description.setLayoutParams(params);
            expanded = false;
        }
    }

    public void startCardTimer(long expireTime) {

        new CountDownTimer(expireTime, 1000) { // adjust the milli seconds here depending on coupon expiration time
            public void onTick(long millisUntilFinished) {
                questTimer.setText(String.format(Locale.US, "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                questTimer.setText(R.string.expired);
            }
        }.start();
    }
}
