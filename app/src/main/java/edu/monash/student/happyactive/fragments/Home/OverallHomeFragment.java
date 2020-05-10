package edu.monash.student.happyactive.fragments.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.OverallActivity.OverallActivityViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

import static android.content.Context.MODE_PRIVATE;

/**
 * This activity generates the Home Page with total user statistics
 * which helps them to track their progress. This activity
 * generates data like total user steps across activities,
 * total activities completed and total time spent on those activities.
 */
public class OverallHomeFragment extends Fragment {


    private TextView overallStepCount;
    private TextView overallTimeSpent;
    private TextView overallActivitiesCompleted;
    private TextView newUserWelcomeText;
    private TextView totalStepsHomeLabel;
    private TextView totalTimeHomeLabel;
    private TextView totalActivitiesHomeLabel;
    private TextView welcomeHomeText;
    private TextView happyActiveHomeText;
    private TextView currentScoreLabel;
    private TextView nextLevelScoreLabel;
    private ProgressBar scoreProgressBar;
    private ArrayList<Integer> levelScores;
    private View homeView;
    private Context homeContext;
    SharedPreferences prefs = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView =  inflater.inflate(R.layout.fragment_overall_home, container, false);
        homeContext = container.getContext();
        overallStepCount = homeView.findViewById(R.id.overallStepCount);
        overallTimeSpent = homeView.findViewById(R.id.overallTimeSpent);
        overallActivitiesCompleted = homeView.findViewById(R.id.overallActivitiesCompleted);
        totalStepsHomeLabel = homeView.findViewById(R.id.TotalStepsHomeLabel);
        totalTimeHomeLabel = homeView.findViewById(R.id.TotalTimeHomeLabel);
        totalActivitiesHomeLabel = homeView.findViewById(R.id.TotalActivitiesHomeLabel);
        newUserWelcomeText = homeView.findViewById(R.id.newUserWelcomeText);
        welcomeHomeText = homeView.findViewById(R.id.welcomeHomeText);
        happyActiveHomeText = homeView.findViewById(R.id.happyActiveHomeText);
        currentScoreLabel = homeView.findViewById(R.id.currentProgressLabel);
        nextLevelScoreLabel = homeView.findViewById(R.id.nextLevelScoreLabel);
        scoreProgressBar = homeView.findViewById(R.id.scoreProgressBar);
        levelScores = new ArrayList<Integer>();
        levelScores.add(100);
        levelScores.add(500);
        levelScores.add(1000);
        prefs = getActivity().getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        return homeView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        OverallActivityViewModel overallActivityViewModel = new ViewModelProvider(this).get(OverallActivityViewModel.class);

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            startPreferencesDialog();
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }

        // Observer to track the live change in data for total step counts on activities.
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

        // Observer to track the live change in data for time spent on activities.
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

        final Observer<Integer> currentScoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer currentScore) {
                if (currentScore != null) {
                    currentScoreLabel.setText(currentScore.toString());
                    scoreProgressBar.setProgress(currentScore);
                    for (Integer item : levelScores) {
                        if (currentScore < item) {
                            nextLevelScoreLabel.setText(item.toString());
                            scoreProgressBar.setMax(item);
                            break;
                        }
                    }
                }
                else {
                    currentScoreLabel.setText("0");
                }
            }
        };

        // Observer to track the live change in data for total activities done.
        final Observer<Integer> overallActivitiesDoneObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer totalActivities) {
                // Update the UI, in this case, a TextView.
                if (totalActivities != 0 && totalActivities != null) {
                    overallActivitiesCompleted.setText(totalActivities.toString());
                    overallActivityViewModel.getTotalStepCount().observe(getViewLifecycleOwner(), overallStepCountObserver);
                    overallActivityViewModel.getTotalTimeSpentOnActivities().observe(getViewLifecycleOwner(), overallTimeSpentObserver);
                    overallActivityViewModel.getCurrentScore().observe(getViewLifecycleOwner(), currentScoreObserver);
                }
                else {
                    overallActivitiesCompleted.setText("0");
                    newUserWelcomeText.setVisibility(View.VISIBLE);
                    happyActiveHomeText.setVisibility(View.VISIBLE);
                    welcomeHomeText.setVisibility(View.VISIBLE);
                    overallStepCount.setVisibility(View.INVISIBLE);
                    overallTimeSpent.setVisibility(View.INVISIBLE);
                    overallActivitiesCompleted.setVisibility(View.INVISIBLE);
                    totalStepsHomeLabel.setVisibility(View.INVISIBLE);
                    totalTimeHomeLabel.setVisibility(View.INVISIBLE);
                    totalActivitiesHomeLabel.setVisibility(View.INVISIBLE);
                    currentScoreLabel.setVisibility(View.INVISIBLE);
                    nextLevelScoreLabel.setVisibility(View.INVISIBLE);
                    scoreProgressBar.setVisibility(View.INVISIBLE);
                }

            }
        };

        overallActivityViewModel.getTotalActivitiesCompleted().observe(getViewLifecycleOwner(), overallActivitiesDoneObserver);
        super.onViewCreated(view, savedInstanceState);
    }

    private void startPreferencesDialog() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogFragment dialog = new ShowPrefFormDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "show_pref_dialog_form");
            }
        }, 2000);
    }
}
