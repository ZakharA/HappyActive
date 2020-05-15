package edu.monash.student.happyactive.fragments.Reports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import edu.monash.student.happyactive.Adapters.HistoryChartsSlidePagerAdapter;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.CompareHistory.CompareHistoryViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * An activity representing the comparison of the user data across time.
 * The activity processes the historical user data from the activities
 * and processes them to show insightful charts and data.
 */
public class CompareHistoryFragment extends Fragment {

    protected CompareHistoryViewModel compareHistoryViewModel;
    private TextView avgSteps;
    private TextView avgTime;
    private HistoryChartsSlidePagerAdapter historyChartsSlidePagerAdapter;
    private ViewPager2 historyChartsViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_compare, container, false);
        avgSteps = view.findViewById(R.id.avgStepsActivity);
        avgTime = view.findViewById(R.id.avgTimeActivity);
        historyChartsSlidePagerAdapter = new HistoryChartsSlidePagerAdapter(this, getContext());
        historyChartsViewPager = view.findViewById(R.id.historyCompareViewPager);
        historyChartsViewPager.setAdapter(historyChartsSlidePagerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "You can swipe across the charts to view them.", Toast.LENGTH_LONG).show();
        compareHistoryViewModel = new ViewModelProvider(this,
                new CompareHistoryViewModel.Factory(getActivity().getApplication())).get(CompareHistoryViewModel.class);

        compareHistoryViewModel.getDataForCompletedActivities().observe(getViewLifecycleOwner(), new Observer<List<ActivitySession>>() {
            @Override
            public void onChanged(List<ActivitySession> activitySession) {
                calculateAvgStepsAndTime(activitySession);
            }
        });
    }

    /**
     * Method to calculate average step count and time for user based on their
     * history across the activities.
     * @param activitySession
     */
    private void calculateAvgStepsAndTime(List<ActivitySession> activitySession) {
        Map<String,String> dataMap = compareHistoryViewModel.calculateAvgStepCountAndTime(activitySession);
        if (!dataMap.isEmpty()) {
            avgSteps.setText(dataMap.get("AvgSteps"));
            avgTime.setText(dataMap.get("AvgTime"));
        }
        else {
            avgSteps.setText("0 steps");
            avgTime.setText("0 hours");
        }
    }
}
