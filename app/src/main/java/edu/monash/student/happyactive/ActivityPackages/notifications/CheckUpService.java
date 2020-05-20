package edu.monash.student.happyactive.ActivityPackages.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import edu.monash.student.happyactive.MainActivity;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.data.entities.ActivitySession;


/**
 * Service actually build a check up notification, android api higher than 26 it's is required
 * that notification channel is explicitly defined.
 */
public class CheckUpService extends IntentService {

    public CheckUpService() {
        super("checkupService");
    }

    public CheckUpService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showCompleteActivityNotification(intent.getExtras().getLong("activity_id"));
    }

    private void showCompleteActivityNotification(long activity_id) {
        String id = "Happy Active";
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Uri soundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, name,importance);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManagerCompat.createNotificationChannel(mChannel);
        }

        Intent pendingIntent = new Intent(this, MainActivity.class);
        pendingIntent.putExtra("incompleteActivity", activity_id);

        Notification notification = new NotificationCompat.Builder(this, "test")
                .setAutoCancel(true)
                .setContentTitle("Happy Active")
                .setContentText("You have not been active for some time!")
                .setContentIntent(
                        PendingIntent.getActivity(this, 0, pendingIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setSound(soundUri).setSmallIcon(R.drawable.happy_active_home)
                .setChannelId(id)
                .build();

        NotificationManagerCompat.from(this).notify(1, notification);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForeground(1, notification);
        }
        else {
            NotificationManagerCompat.from(this).notify(1, notification);
        }
    }
}
