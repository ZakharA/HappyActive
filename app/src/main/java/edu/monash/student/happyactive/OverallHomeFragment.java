package edu.monash.student.happyactive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.Reports.OverallActivity.OverallActivityViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class OverallHomeFragment extends Fragment {


    private TextView overallStepCount;
    private TextView overallTimeSpent;
    private TextView overallActivitiesCompleted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView =  inflater.inflate(R.layout.fragment_overall_home, container, false);

        overallStepCount = homeView.findViewById(R.id.overallStepCount);
        overallTimeSpent = homeView.findViewById(R.id.overallTimeSpent);
        overallActivitiesCompleted = homeView.findViewById(R.id.overallActivitiesCompleted);

        return homeView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        OverallActivityViewModel overallActivityViewModel = new ViewModelProvider(this).get(OverallActivityViewModel.class);

        final Observer<Integer> overallStepCountObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer totalSteps) {
                // Update the UI, in this case, a TextView.
                if (totalSteps != null) {
                    overallStepCount.setText(totalSteps.toString()+" steps");
                }
                else {
                    overallStepCount.setText("0 steps");
                }

            }
        };

        final Observer<List<ActivitySession>> overallTimeSpentObserver = new Observer<List<ActivitySession>>() {
            @Override
            public void onChanged(@Nullable final List<ActivitySession> activitySessions) {
                long sumTime = 0l;
                // Update the UI, in this case, a TextView.
                if (activitySessions != null) {
                    for (int i=0 ; i<activitySessions.size(); i++){
                        long diffInMillis = Math.abs(activitySessions.get(i).getCompletedDateTime().getTime() - activitySessions.get(i).getStartDateTime().getTime());
                        sumTime += diffInMillis;
                    }
                    long seconds = sumTime / 1000;
                    long minutes = seconds / 60;
                    long hours =  minutes / 60;
                    overallTimeSpent.setText(hours + " hours " + minutes + " minutes");
                }
                else {
                    overallTimeSpent.setText("0 hours 0 minutes");
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

        overallActivityViewModel.getTotalStepCount().observe(getViewLifecycleOwner(), overallStepCountObserver);
        overallActivityViewModel.getTotalTimeSpentOnActivities().observe(getViewLifecycleOwner(), overallTimeSpentObserver);
        overallActivityViewModel.getTotalActivitiesCompleted().observe(getViewLifecycleOwner(), overallActivitiesDoneObserver);
        super.onViewCreated(view, savedInstanceState);
    }
}
