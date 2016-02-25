package com.kv.iprojectlib.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationUtils {

    public static void showNotification(Context context, int notifyID, int iconRes, String title, String msg, Class actClass) {

        Notification messageNotification = new Notification();
        messageNotification.icon = iconRes;
        messageNotification.tickerText = msg;
        messageNotification.defaults = Notification.DEFAULT_SOUND;
        messageNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager messageNotificatioManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent messageIntent = new Intent(context, actClass);
        PendingIntent messagePendingIntent = PendingIntent.getActivity(context, 0, messageIntent, 0);

        messageNotification.setLatestEventInfo(context.getApplicationContext(), title, msg, messagePendingIntent);
        messageNotificatioManager.notify(notifyID, messageNotification);
    }
}
