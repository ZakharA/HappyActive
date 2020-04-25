package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CheckUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent checkUpService = new Intent(context, CheckUpService.class);
        checkUpService.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        context.startService(checkUpService);
    }
}
