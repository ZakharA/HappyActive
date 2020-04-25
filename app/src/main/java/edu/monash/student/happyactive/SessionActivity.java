package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Calendar;

import edu.monash.student.happyactive.ActivityPackages.StepCounterService;
import edu.monash.student.happyactive.ActivityPackages.notifications.CheckUpReceiver;
import edu.monash.student.happyactive.ActivityPackages.notifications.ReminderReceiver;
import edu.monash.student.happyactive.fragments.TaskFragment;


public class SessionActivity extends AppCompatActivity {

    public static final String ACTIVITY_ID = "activity_id";
    private StepCounterService mService;
    private long activityId;
    boolean mBound = false;
    private AlarmManager alarmManager;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityId = getIntent().getLongExtra(ACTIVITY_ID, 0l);
        setContentView(R.layout.activity_session);
        TaskFragment nextFrag= new TaskFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.session_fragment_container, nextFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, StepCounterService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            StepCounterService.LocalBinder binder = (StepCounterService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public long getActivityId() {
        return activityId;
    }

    public int getNumberOfSteps(){
        return mService.getNumSteps();
    }


    public void setCheckUpNotification() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent = new Intent(getApplicationContext(), CheckUpReceiver.class);
        alarmIntent.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        alarmIntent.putExtra(SessionActivity.ACTIVITY_ID, activityId);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 2);

        alarmManager.set(AlarmManager.RTC_WAKEUP,  now.getTimeInMillis() , pendingIntent);
    }

    public void cancelCheckUpNotification(){
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }
}
