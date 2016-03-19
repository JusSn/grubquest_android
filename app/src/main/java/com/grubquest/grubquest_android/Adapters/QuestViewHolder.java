package com.grubquest.grubquest_android.Adapters;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public final TextView questTimer, questInfo;
    public final ImageView chestIcon;
    public LinearLayout questImageLayout;

    private boolean expanded = false;

    public QuestViewHolder(final View dataView) {
        super(dataView);

        imageViewMap.put("mobile_restaurant_icon", (ImageView) dataView.findViewById(R.id.mobile_restaurant_icon));
        imageViewMap.put("mobile_method_icon", (ImageView) dataView.findViewById(R.id.mobile_method_icon));
        imageViewMap.put("mobile_quest_icon", (ImageView) dataView.findViewById(R.id.mobile_quest_icon));
        imageViewMap.put("mobile_background_img", (ImageView) dataView.findViewById(R.id.mobile_background_img));

        textViewMap.put("restaurantName", (TextView) dataView.findViewById(R.id.restaurantName));
        textViewMap.put("description", (TextView) dataView.findViewById(R.id.description));
        textViewMap.put("title", (TextView) dataView.findViewById(R.id.title));

        questTimer = (TextView) dataView.findViewById(R.id.time_remain_textview);
        questInfo = (TextView) dataView.findViewById(R.id.quest_info_text);

        chestIcon = (ImageView) dataView.findViewById(R.id.chest_icon);
        questImageLayout = (LinearLayout) dataView.findViewById(R.id.quest_text_layout);

        questImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = questInfo.getLayoutParams();
                if (!expanded) {
                    expanded = true;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    questInfo.setLayoutParams(params);
                } else {
                    params.height = 0;
                    questInfo.setLayoutParams(params);
                    expanded = false;
                }
            }
        });

        questInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = questInfo.getLayoutParams();
                params.height = 0;
                questInfo.setLayoutParams(params);
            }
        });
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
