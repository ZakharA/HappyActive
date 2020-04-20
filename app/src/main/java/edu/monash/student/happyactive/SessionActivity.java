package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import edu.monash.student.happyactive.ActivityPackages.StepCounterService;


public class SessionActivity extends AppCompatActivity {

    public static final String ACTIVITY_ID = "activity_id";
    private StepCounterService mService;
    private long activityId;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityId = getIntent().getLongExtra(ACTIVITY_ID, 0l);
        setContentView(R.layout.activity_session);
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
}
