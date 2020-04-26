package edu.monash.student.happyactive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import edu.monash.student.happyactive.Reports.PostActivityStats.PostActivityStatsViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class PostActivityStatsFragment extends Fragment {

    TextView stepCountPostActivity;
    TextView timePostActivity;
    Button finishActivityButton;
    PostActivityStatsViewModel postActivityStatsViewModel;
    int currentSessionId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_stats, container, false);

        stepCountPostActivity = (TextView) view.findViewById(R.id.stepCountForActivity);
        timePostActivity = (TextView) view.findViewById(R.id.timeSpentForActivity);
        finishActivityButton = (Button) view.findViewById(R.id.finishActivityButton);
        currentSessionId = (int) PostActivityStatsFragmentArgs.fromBundle(getArguments()).getSessionId();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        postActivityStatsViewModel = new ViewModelProvider(this,
                new PostActivityStatsViewModel.Factory(getActivity().getApplication())).get(PostActivityStatsViewModel.class);

        postActivityStatsViewModel.getDataForCurrentSession(currentSessionId).observe(getViewLifecycleOwner(), new Observer<ActivitySession>() {
            @Override
            public void onChanged(ActivitySession activitySession) {
                if(activitySession != null) {
                    stepCountPostActivity.setText(Long.toString(activitySession.getStepCount()));
                    long diffInMillis = Math.abs(activitySession.getCompletedDateTime().getTime() - activitySession.getStartDateTime().getTime());
                    long hours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                    long minutes = TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS);
                    long seconds = TimeUnit.SECONDS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                    String displayTime = Long.toString(hours) + " hours " + Long.toString (minutes) + " minutes " + Long.toString (seconds - (minutes * 60)) + " seconds";
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
                Navigation.findNavController(view).navigate(
                        PostActivityStatsFragmentDirections.returnToHome()
                );
            }
        });
    }


}
