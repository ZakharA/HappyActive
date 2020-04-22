package edu.monash.student.happyactive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.OverallActivity.OverallActivityViewModel;

public class OverallHomeActivity extends AppCompatActivity {


    private TextView overallStepCount;
    private TextView overallTimeSpent;
    private TextView overallActivitiesCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_home);

        overallStepCount = findViewById(R.id.overallStepCount);
        overallTimeSpent = findViewById(R.id.overallTimeSpent);
        overallActivitiesCompleted = findViewById(R.id.overallActivitiesCompleted);

        OverallActivityViewModel overallActivityViewModel = new ViewModelProvider(this).get(OverallActivityViewModel.class);

        final Observer<Integer> overallStepCountObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer totalSteps) {
                // Update the UI, in this case, a TextView.
                if (totalSteps != null) {
                    overallStepCount.setText(totalSteps.toString()+"steps");
                }
                else {
                    overallStepCount.setText("0 steps");
                }

            }
        };

        final Observer<Date> overallTimeSpentObserver = new Observer<Date>() {
            @Override
            public void onChanged(@Nullable final Date totalTime) {
                // Update the UI, in this case, a TextView.
                if (totalTime != null) {
                    overallTimeSpent.setText(totalTime.toString() + "hours");
                }
                else {
                    overallTimeSpent.setText("0 hours");
                }

            }
        };

        final Observer<Integer> overallActivitiesDoneObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer totalActivities) {
                // Update the UI, in this case, a TextView.
                if (totalActivities != null) {
                    overallActivitiesCompleted.setText(totalActivities.toString());
                }
                else {
                    overallActivitiesCompleted.setText("0");
                }

            }
        };

        overallActivityViewModel.getTotalStepCount().observe(this, overallStepCountObserver);
        overallActivityViewModel.getTotalTimeSpentOnActivities().observe(this, overallTimeSpentObserver);
        overallActivityViewModel.getTotalActivitiesCompleted().observe(this, overallActivitiesDoneObserver);
    }
}
