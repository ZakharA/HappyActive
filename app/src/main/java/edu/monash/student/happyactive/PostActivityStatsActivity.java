package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import edu.monash.student.happyactive.Reports.PostActivityStats.PostActivityStatsViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class PostActivityStatsActivity extends AppCompatActivity {

    TextView stepCountPostActivity;
    TextView timePostActivity;
    Button finishActivityButton;
    PostActivityStatsViewModel postActivityStatsViewModel;
    Integer currentSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_stats);

        stepCountPostActivity = (TextView) findViewById(R.id.stepCountForActivity);
        timePostActivity = (TextView) findViewById(R.id.timeSpentForActivity);
        finishActivityButton = (Button) findViewById(R.id.finishActivityButton);
        currentSessionId = getIntent().getIntExtra("currentSessionId", 0);

        postActivityStatsViewModel = new ViewModelProvider(this,
                new PostActivityStatsViewModel.Factory(this.getApplication())).get(PostActivityStatsViewModel.class);

        postActivityStatsViewModel.getDataForCurrentSession(currentSessionId).observe(this, new Observer<ActivitySession>() {
            @Override
            public void onChanged(ActivitySession activitySession) {
                if(activitySession != null) {
                    stepCountPostActivity.setText(Long.toString(activitySession.getStepCount()));
                    long diffInMillis = Math.abs(activitySession.getCompletedDateTime().getTime() - activitySession.getStartDateTime().getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                    long seconds = diff / 1000;
                    long minutes = seconds / 60;
                    long hours =  minutes / 60;
                    String displayTime = Long.toString(hours) + " hours " + Long.toString(minutes) + "minutes";
                    timePostActivity.setText(displayTime);
                }
                else {
                    stepCountPostActivity.setText("0 steps");
                    timePostActivity.setText("0 hours 0 minutes");
                }
            }
        });

        finishActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postActivityStatsViewModel.setStatusCompletedPostActivity(currentSessionId);
                Intent intent = new Intent(PostActivityStatsActivity.this,
                        OverallHomeFragment.class);
                startActivity(intent);
            }
        });

    }
}
