package edu.monash.student.happyactive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.monash.student.happyactive.Reports.CompareAverage.CompareAverageViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class CompareAverageFragment extends Fragment {

    protected CompareAverageViewModel compareAverageViewModel;
    TextView averageStepsAgeGroup;
    TextView averageStepsAgeGroupLabel;
    TextView stepDiffAvg;
    TextView stepDiffAvgLabel;
    TextView avgWearTimeAus;
    TextView wearTimeDiff;
    TextView wearTimeDiffLabel;
    BarChart stepsComparePopBarChart;
    BarChart wearComparePopBarChart;
    LineChart lineChart;
    private static final String STEPS_AGE_GENDER = "statisticsFiles/steps_by_age_gender_20170508.csv";
    private static final String WEAR_TIME_AUS = "13";
    private Long STEP_COUNT_AUS = 3058l;
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
        stepsComparePopBarChart = view.findViewById(R.id.stepsComparePopBarChart);
        wearComparePopBarChart = view.findViewById(R.id.wearComparePopBarChart);
        lineChart = view.findViewById(R.id.lineChart);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compareAverageViewModel = new ViewModelProvider(this,
                new CompareAverageViewModel.Factory(getActivity().getApplication())).get(CompareAverageViewModel.class);

        generateLineChart();
        compareAverageViewModel.getDataForCompletedActivities().observe(getViewLifecycleOwner(), new Observer<List<ActivitySession>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(List<ActivitySession> activitySession) {
                calculateAvgStepsAndTime(activitySession);
            }
        });
    }

    private void generateLineChart() {
        ArrayList<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry(2001,613.8f));
        lineEntries.add(new Entry(2005,719.6f));
        lineEntries.add(new Entry(2008,785.6f));
        lineEntries.add(new Entry(2012, 778.7f));
        lineEntries.add(new Entry(2015,877.7f));
        lineEntries.add(new Entry(2018,960.8f));

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "No of people with Arthritis (in '000s)");
        LineData lineData = new LineData(lineDataSet);

        lineDataSet.setDrawIcons(false);
        lineDataSet.enableDashedLine(10f, 5f, 0f);
        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        lineDataSet.setColor(Color.DKGRAY);
        lineDataSet.setCircleColor(Color.DKGRAY);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);

        lineChart.setData(lineData);
        lineChart.animateXY(3000, 3000);
        lineChart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void calculateAvgStepsAndTime(List<ActivitySession> activitySession) {
        Map<String,Long> dataMap = compareAverageViewModel.calculateAvgStepCountAndTime(activitySession);
        if (!dataMap.isEmpty()) {
            processAvgStepsAus(dataMap);
            processWearTimeAus(dataMap);
            makeBarChart(dataMap);
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

    private void makeBarChart(Map<String, Long> dataMap) {
        List<BarEntry> entriesStep = new ArrayList<BarEntry>();
        List<BarEntry> entriesTime = new ArrayList<BarEntry>();

        entriesStep.add(new BarEntry(0, STEP_COUNT_AUS));
        entriesStep.add(new BarEntry(1, dataMap.get("AvgSteps")));
        entriesTime.add(new BarEntry(0, 13.07f));
        entriesTime.add(new BarEntry(1, dataMap.get("AvgTime")));

        BarDataSet dataSet = new BarDataSet(entriesStep, "Avg Steps/Day-Australia vs User");
        BarData barData = new BarData();
        barData.addDataSet(dataSet);
        barData.setBarWidth(0.9f);
        stepsComparePopBarChart.setData(barData);
        stepsComparePopBarChart.setFitBars(true);
        stepsComparePopBarChart.animateXY(2000, 2000);
        stepsComparePopBarChart.invalidate();

        BarDataSet dataSet2 = new BarDataSet(entriesTime, "Avg Time-Australia vs User");
        BarData barData2 = new BarData();
        barData2.addDataSet(dataSet2);
        barData2.setBarWidth(0.9f);
        wearComparePopBarChart.setData(barData2);
        wearComparePopBarChart.setFitBars(true);
        wearComparePopBarChart.animateXY(2000, 2000);
        wearComparePopBarChart.invalidate();
    }

    private void processWearTimeAus(Map<String, Long> dataMap) {
        avgWearTimeAus.setText(WEAR_TIME_AUS + " hours");
        Long diffWearTime = dataMap.get("AvgTime") - Long.parseLong(WEAR_TIME_AUS);
        wearTimeDiff.setText(diffWearTime.toString() + "hours");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void processAvgStepsAus(Map<String, Long> dataMap) {
        Long globalAvgSteps;
        String[] record = null;
        try {
                InputStream inputStream = getActivity().getAssets().open(STEPS_AGE_GENDER);
                //InputStream inputStream = getAssets().open(STEPS_AGE_GENDER);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while((line = br.readLine()) != null) {
                    record = line.split(",");
                    if (record[1].contains("[70")) {
                        globalAvgSteps = Long.parseLong(record[3]);
                        averageStepsAgeGroup.setText(globalAvgSteps.toString() + " steps");
                        STEP_COUNT_AUS = globalAvgSteps;
                        averageStepsAgeGroupLabel.setText("Avg Steps/Day 70+ male");
                        Long diffSteps = dataMap.get("AvgSteps") - globalAvgSteps;
                        stepDiffAvg.setText(diffSteps.toString() + " steps");
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
                        break;
                    }
                }
                br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
