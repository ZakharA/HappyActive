package edu.monash.student.happyactive.fragments.Reports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.Adapters.AverageChartsSlidePagerAdapter;
import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.CompareAverage.CompareAverageViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * An activity representing a the comparison of the user data with the average
 * population data. This activity processes the user historical data and
 * compares it with the step count and wear time of a global australian
 * population to generate useful insights. It also charts the current trend
 * of arthritis patients growth.
 */
public class CompareAverageFragment extends Fragment {

    protected CompareAverageViewModel compareAverageViewModel;
    private TextView averageStepsAgeGroup;
    private TextView averageStepsAgeGroupLabel;
    private TextView stepDiffAvg;
    private TextView stepDiffAvgLabel;
    private TextView avgWearTimeAus;
    private TextView wearTimeDiff;
    private TextView wearTimeDiffLabel;
    private AverageChartsSlidePagerAdapter averageChartsSlidePagerAdapter;
    private ViewPager2 averageChartsViewPager;
    // Data file for age gender resources
    private static final String WEAR_TIME_AUS = "13";

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_compare_average, container, false);

        averageStepsAgeGroup = view.findViewById(R.id.averageStepsAgeGroup);
        averageStepsAgeGroupLabel = view.findViewById(R.id.averageStepsAgeGroupLabel);
        stepDiffAvg = view.findViewById(R.id.stepDiffAvg);
        stepDiffAvgLabel = view.findViewById(R.id.stepDiffAvgLabel);
        avgWearTimeAus = view.findViewById(R.id.avgWearTimeAus);
        wearTimeDiff = view.findViewById(R.id.wearTimeDiff);
        wearTimeDiffLabel = view.findViewById(R.id.wearTimeDiffLabel);
        averageChartsSlidePagerAdapter = new AverageChartsSlidePagerAdapter(this, getContext());
        averageChartsViewPager = view.findViewById(R.id.averageCompareViewPager);
        averageChartsViewPager.setAdapter(averageChartsSlidePagerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText( view.getContext(), "You can swipe across the charts to view them.", Toast.LENGTH_LONG);
        compareAverageViewModel = new ViewModelProvider(this,
                new CompareAverageViewModel.Factory(getActivity().getApplication())).get(CompareAverageViewModel.class);

        compareAverageViewModel.getDataForCompletedActivities().observe(getViewLifecycleOwner(), new Observer<List<ActivitySession>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<ActivitySession> activitySession) {
                try {
                    calculateAvgStepsAndTime(activitySession);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * Method to calculate the difference between the users steps/time against the population.
     * @param activitySession
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void calculateAvgStepsAndTime(List<ActivitySession> activitySession) throws ExecutionException, InterruptedException {
        Map<String,Long> dataMap = compareAverageViewModel.calculateAvgStepCountAndTime(activitySession);
        if (!dataMap.isEmpty()) {
            processAvgStepsAus(dataMap);
            processWearTimeAus(dataMap);
        }
        else {
            avgWearTimeAus.setText(WEAR_TIME_AUS + " hours");
            averageStepsAgeGroup.setText("3058 steps");
            averageStepsAgeGroupLabel.setText("Avg Steps/Day 70+ male");
            stepDiffAvg.setText("No data");
            wearTimeDiff.setText("No data");
            TextView youAre = view.findViewById(R.id.youAreLabel);
            youAre.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Method to calculate wear time for user against the population average.
     * @param dataMap
     */
    private void processWearTimeAus(Map<String, Long> dataMap) {
        avgWearTimeAus.setText(WEAR_TIME_AUS + " hours");
        Long diffWearTime = dataMap.get("AvgTime") - Long.parseLong(WEAR_TIME_AUS);
        wearTimeDiff.setText(Math.abs(diffWearTime) + "hours");
        if (diffWearTime < 0) {
            wearTimeDiff.setTextColor(Color.parseColor("#FF0000"));
            wearTimeDiffLabel.setText("behind the Australian Average Steps!");
        }
        else if (diffWearTime > 0) {
            wearTimeDiff.setTextColor(Color.parseColor("#228B22"));
            wearTimeDiffLabel.setText("ahead of the Australian Average Steps!");
        }
        else {
            wearTimeDiffLabel.setText("behind the Australian Average Steps!");
        }
    }

    /**
     * Method to calculate step count for user against the population average.
     * @param dataMap
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processAvgStepsAus(Map<String, Long> dataMap) throws ExecutionException, InterruptedException {
        ArrayList<String> result = compareAverageViewModel.processStepsByAgeFile(getActivity());
        Long globalAvgSteps;
        String[] record = null;
        globalAvgSteps = Long.parseLong(result.get(0));
        averageStepsAgeGroup.setText(globalAvgSteps.toString() + " steps");
        averageStepsAgeGroupLabel.setText(result.get(1));
        Long diffSteps = dataMap.get("AvgSteps") - globalAvgSteps;
        stepDiffAvg.setText(Math.abs(diffSteps) + " steps");
        if (diffSteps < 0) {
            stepDiffAvg.setTextColor(Color.parseColor("#FF0000"));
            stepDiffAvgLabel.setText("behind the Australian Average Steps!");
        }
        else if (diffSteps > 0) {
            stepDiffAvg.setTextColor(Color.parseColor("#228B22"));
            stepDiffAvgLabel.setText("ahead of the Australian Average Steps!");
        }
        else {
            stepDiffAvgLabel.setText("behind the Australian Average Steps!");
        }
    }
}
