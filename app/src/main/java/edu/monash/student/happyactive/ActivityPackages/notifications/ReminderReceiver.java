package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * Receives the "trigger" for showing notification and initiate the notification
 * base on the version of the android, since on android api higher 26 it is required
 * that the notification executed in the foreground.
 */
public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent reminderService = new Intent(context, ReminderService.class);
        reminderService.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(reminderService);
        } else {
            context.startService(reminderService);
        }
    }
}
