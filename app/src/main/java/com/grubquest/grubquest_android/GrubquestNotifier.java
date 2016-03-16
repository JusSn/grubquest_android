package com.grubquest.grubquest_android;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by Justin on 3/15/16.
 */
//TODO: Figure out how multiple notifications interact; set up actual notification times from Firebase; check icon on phone
public class GrubquestNotifier {
    public static void grubquestNotify(Context context, Intent intent, String title, String message, long expireTime) {
        PendingIntent clickIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentIntent(clickIntent)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class)
                .putExtra(NotificationPublisher.NOTIFICATION_ID, 1)
                .putExtra(NotificationPublisher.NOTIFICATION, builder.build());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + expireTime;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
