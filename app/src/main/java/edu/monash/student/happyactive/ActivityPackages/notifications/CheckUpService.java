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

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CheckUpService extends IntentService {

    public CheckUpService() {
        super("checkupService");
    }

    public CheckUpService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showCompleteActivityNotification();
    }

    private void showCompleteActivityNotification() {
        Uri soundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this, "test")
                .setContentTitle("Happy Active")
                .setContentText("You have not been active for some time!")
                .setContentIntent(
                        PendingIntent.getActivity(this, 0, new Intent(this,
                                        ActivitySession.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setSound(soundUri).setSmallIcon(R.drawable.happy_active_home)
                .build();
        NotificationManagerCompat.from(this).notify(0, notification);
    }
}
