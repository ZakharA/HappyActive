package edu.monash.student.happyactive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import edu.monash.student.happyactive.ActivityPackages.StepCounterService;
import edu.monash.student.happyactive.ActivityPackages.notifications.CheckUpReceiver;
import edu.monash.student.happyactive.ActivityPackages.notifications.ReminderReceiver;

public class MainActivity extends AppCompatActivity implements ShowPrefFormDialogFragment.ShowPrefFormDialogListener {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Intent alarmIntent;
    private BottomNavigationView navigationView;
    private StepCounterService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding navigation bar with nav graph
        navigationView = findViewById(R.id.report_bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView,
                navHostFragment.getNavController());

        if (savedInstanceState ==  null) {
            setDailyReminder();
        }

    }

    /**
     *  here we binging stepcounter with the app itself and run it as a background service
     *  every time then activity is created.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, StepCounterService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     *  stop stepcounter service before the activity is closed.
     */
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    /**
     * create an intent to show the check up notification if the user is not active for two hours
     * @param activityId id of the activityPackage to show when the user open notification.
     */
    public void setCheckUpNotification(long activityId) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent = new Intent(getApplicationContext(), CheckUpReceiver.class);
        alarmIntent.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        alarmIntent.putExtra("activity_id", activityId);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 2);

        alarmManager.set(AlarmManager.RTC_WAKEUP,  now.getTimeInMillis() , pendingIntent);
    }

    /**
     * cancel delayed intent to show checkup notification.
     * it invokes when avtivitySession is finished by the user
     */
    public void cancelCheckUpNotification(){
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }

    /**
     * create an intent to show daily notification and set an alarm for 20:00 pm
     */
    private void setDailyReminder() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmIntent = new Intent(getApplicationContext(), ReminderReceiver.class);
        alarmIntent.setData((Uri.parse("happyActive://"+System.currentTimeMillis())));
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 20);
        alarmStartTime.set(Calendar.MINUTE, 00);
        alarmStartTime.set(Calendar.SECOND, 0);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     *  here we binging stepcounter with the app itself and run it as a background service
     *  every time then activity is created.
     */
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

    /**
     *  fragments can access the stepcounter service using this method
     *  and it will return current stepCount registered in the background service.
     */
    public int getNumberOfSteps(){
        return mService.getNumSteps();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Navigation.findNavController(findViewById(R.id.constraintLayoutHome)).navigate(
                OverallHomeFragmentDirections.showPreferencesFragment()
        );
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(), R.string.pref_dialog_set_later, Toast.LENGTH_LONG).show();
    }
}
