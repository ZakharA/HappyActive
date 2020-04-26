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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.monash.student.happyactive.Reports.CompareHistory.CompareHistoryViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareHistoryFragment extends Fragment {

    protected CompareHistoryViewModel compareHistoryViewModel;
    private TextView avgSteps;
    private TextView avgTime;
    private BarChart chartTime;
    private BarChart chartSteps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_compare, container, false);
        avgSteps = view.findViewById(R.id.avgStepsActivity);
        avgTime = view.findViewById(R.id.avgTimeActivity);
        chartSteps = view.findViewById(R.id.chartStepCompare);
        chartTime = view.findViewById(R.id.chartTimeCompare);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compareHistoryViewModel = new ViewModelProvider(this,
                new CompareHistoryViewModel.Factory(getActivity().getApplication())).get(CompareHistoryViewModel.class);

        compareHistoryViewModel.getDataForCompletedActivities().observe(getViewLifecycleOwner(), new Observer<List<ActivitySession>>() {
            @Override
            public void onChanged(List<ActivitySession> activitySession) {
                calculateAvgStepsAndTime(activitySession);
                if (activitySession != null && !activitySession.isEmpty()) {
                    generateHistoryCharts(activitySession);
                }
            }
        });
    }

    private void generateHistoryCharts(List<ActivitySession> activitySession) {
        List<BarEntry> entriesSteps = new ArrayList<BarEntry>();
        List<BarEntry> entriesTime = new ArrayList<BarEntry>();
        List<String> xAxisLabels = new ArrayList<String>();

        for (int i=0 ; i<activitySession.size(); i++){
            entriesSteps.add(new BarEntry(i, activitySession.get(i).getStepCount()));
            long diffInMillis = Math.abs(activitySession.get(i).getCompletedDateTime().getTime() - activitySession.get(i).getStartDateTime().getTime());
            float seconds = diffInMillis / 1000;
            float minutes = seconds / 60;
            float hours =  minutes / 60;
            entriesTime.add(new BarEntry(i, minutes));
            xAxisLabels.add(Long.toString(activitySession.get(i).getActivityId()));
        }
        //setXAxisForCharts(xAxisLabels);
        generateChart(entriesSteps, "Step Count of each activity", chartSteps);
        generateChart(entriesTime, "Time Spent (minutes) on each activity", chartTime);
    }

    private void setXAxisForCharts(List<String> xAxisLabels) {
        XAxis xAxisSteps = chartSteps.getXAxis();
        XAxis xAxisTime = chartTime.getXAxis();
        setValueFormatterForXAxis(xAxisLabels, xAxisSteps);
        setValueFormatterForXAxis(xAxisLabels, xAxisTime);
    }

    private void setValueFormatterForXAxis(List<String> xAxisLabels, XAxis xAxisTime) {
        xAxisTime.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xAxisLabels.get((int) value);
            }
        });
    }

    private void generateChart(List<BarEntry> entries, String label, BarChart chart) {
        BarDataSet dataSet = new BarDataSet(entries, label);
        BarData barData = new BarData();
        barData.addDataSet(dataSet);
        barData.setBarWidth(0.8f);
        chart.setData(barData);
        chart.setFitBars(true);
        chart.animateXY(3000, 3000);
        chart.invalidate();
    }

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
