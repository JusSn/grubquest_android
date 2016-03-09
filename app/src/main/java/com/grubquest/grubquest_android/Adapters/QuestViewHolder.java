package com.grubquest.grubquest_android.Adapters;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grubquest.grubquest_android.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Justin on 3/8/16.
 */
public class QuestViewHolder extends RecyclerView.ViewHolder {
    public final TextView company_text, offer_small_text, quest_info, quest_timer;
    public final ImageView quest_image, company_icon, icon1_image, icon2_image;
    public LinearLayout quest_image_layout;

    private boolean expanded = false;

    public QuestViewHolder(final View dataView) {
        super(dataView);
        company_icon = (ImageView) dataView.findViewById(R.id.restaurant_icon_image);
        quest_image = (ImageView) dataView.findViewById(R.id.quest_image);
        company_text = (TextView) dataView.findViewById(R.id.restaurant_textview);
        quest_timer = (TextView) dataView.findViewById(R.id.time_remain_textview);
        icon1_image = (ImageView) dataView.findViewById(R.id.type_icon_1);
        icon2_image = (ImageView) dataView.findViewById(R.id.type_icon_2);
        quest_info = (TextView) dataView.findViewById(R.id.quest_info_text);
        offer_small_text = (TextView) dataView.findViewById(R.id.offer_textview);

        new CountDownTimer(10000, 1000) { // adjust the milli seconds here depending on coupon expiration time

            public void onTick(long millisUntilFinished) {
                quest_timer.setText(String.format(Locale.US, "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished))));
            }
            public void onFinish() {

                quest_timer.setText(R.string.expired);
//                NotificationCompat.Builder expireNotifBuilder =
//                        (NotificationCompat.Builder) new NotificationCompat.Builder(dataView.getContext())
//                                .setSmallIcon(R.drawable.warning)
//                                .setContentTitle("My notification")
//                                .setContentText("Hello World!");
            }
        }.start();

        quest_image_layout = (LinearLayout) dataView.findViewById(R.id.quest_text_layout);

        quest_image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expanded) {
                    expanded = true;
                    ViewGroup.LayoutParams params = quest_info.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    quest_info.setLayoutParams(params);
                } else {
                    quest_info.performClick();
                    expanded = false;
                }
            }
        });

        quest_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = quest_info.getLayoutParams();
                params.height = 0;
                quest_info.setLayoutParams(params);
            }
        });
    }
}
