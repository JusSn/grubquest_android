package com.grubquest.grubquest_android.Adapters;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grubquest.grubquest_android.HomeActivity;
import com.grubquest.grubquest_android.QuestsFragment;
import com.grubquest.grubquest_android.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Justin on 3/8/16.
 */
public class QuestViewHolder extends RecyclerView.ViewHolder {
    public final TextView companyText, offerSmallText, questInfo, questTimer;
    public final ImageView questImage, companyIcon, icon1Image, icon2Image, chestIcon;
    public LinearLayout questImageLayout;


    private boolean expanded = false;

    public QuestViewHolder(final View dataView) {
        super(dataView);
        companyIcon = (ImageView) dataView.findViewById(R.id.restaurant_icon_image);
        questImage = (ImageView) dataView.findViewById(R.id.quest_image);
        companyText = (TextView) dataView.findViewById(R.id.restaurant_textview);
        questTimer = (TextView) dataView.findViewById(R.id.time_remain_textview);
        icon1Image = (ImageView) dataView.findViewById(R.id.type_icon_1);
        icon2Image = (ImageView) dataView.findViewById(R.id.type_icon_2);
        questInfo = (TextView) dataView.findViewById(R.id.quest_info_text);
        offerSmallText = (TextView) dataView.findViewById(R.id.offer_textview);

        chestIcon = (ImageView) dataView.findViewById(R.id.chest_icon);

        //TODO: CountDownTimer does not run in background. Need to use AlarmManager to time and send notifications

        questImageLayout = (LinearLayout) dataView.findViewById(R.id.quest_text_layout);

        questImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expanded) {
                    expanded = true;
                    ViewGroup.LayoutParams params = questInfo.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    questInfo.setLayoutParams(params);
                } else {
                    questInfo.performClick();
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
