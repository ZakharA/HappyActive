package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.ContextCompat;

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
