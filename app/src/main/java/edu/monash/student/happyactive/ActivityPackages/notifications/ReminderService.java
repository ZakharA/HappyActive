package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import edu.monash.student.happyactive.OverallHomeFragment;
import edu.monash.student.happyactive.R;

public class ReminderService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReminderService(String name) {
        super(name);
    }

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showNotification();
    }

    private void showNotification() {
        Uri soundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this, "test")
                .setContentTitle("Happy Active")
                .setContentText("Let's plan an activity for tomorrow!")
                .setContentIntent(
                        PendingIntent.getActivity(this, 0, new Intent(this,
                                        OverallHomeFragment.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setSound(soundUri).setSmallIcon(R.drawable.happy_active_home)
                .build();
        NotificationManagerCompat.from(this).notify(0, notification);
    }
}
