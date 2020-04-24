package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent reminderService = new Intent(context, ReminderService.class);
        reminderService.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        context.startService(reminderService);
    }
}
