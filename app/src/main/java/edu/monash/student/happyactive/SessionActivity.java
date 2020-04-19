package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.monash.student.happyactive.fragments.PackageDetailsFragment;


public class SessionActivity extends AppCompatActivity {

    public static final String ACTIVITY_ID = "activity_id";
    private long activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityId = getIntent().getLongExtra(ACTIVITY_ID, 0l);
        setContentView(R.layout.activity_session);
    }

    public long getActivityId() {
        return activityId;
    }
}
