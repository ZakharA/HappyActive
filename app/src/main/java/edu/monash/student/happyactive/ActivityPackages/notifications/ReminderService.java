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

        Notification notification = new NotificationCompat.Builder(this, "test")
                .setContentTitle("Happy Active")
                .setContentText("Let's plan an activity for tomorrow!")
                .setContentIntent(
                        PendingIntent.getActivity(this, 0, new Intent(this,
                                        MainActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setSound(soundUri).setSmallIcon(R.drawable.happy_active_home)
                .setChannelId(id)
                .build();

        NotificationManagerCompat.from(this).notify(0, notification);
    }
}
