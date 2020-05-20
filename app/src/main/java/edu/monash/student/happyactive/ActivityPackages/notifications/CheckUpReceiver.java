package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.ContextCompat;

/**
 * Receives the "trigger" for showing notification and initiate the notification
 * base on the version of the android, since on android api higher 23 it is required
 * that the notification executed in the foreground.
 */
public class CheckUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent checkUpService = new Intent(context, CheckUpService.class);
        checkUpService.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        checkUpService.putExtra("activity_id", intent.getLongExtra("activity_id", 0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, checkUpService);
        } else {
            context.startService(checkUpService);
        }
    }
}
