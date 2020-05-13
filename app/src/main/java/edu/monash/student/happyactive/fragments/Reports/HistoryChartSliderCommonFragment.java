package edu.monash.student.happyactive.fragments.Reports;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.CompareHistory.CompareHistoryViewModel;
import edu.monash.student.happyactive.data.relationships.ActivityWithSessions;
import edu.monash.student.happyactive.data.relationships.SessionsWithActivity;

public class HistoryChartSliderCommonFragment extends Fragment {

    private static final String TAB_POS = "Tab Position";

    private Integer tabPosition;
    private View rootView;
    private LinearLayout linearLayout;
    private CompareHistoryViewModel compareHistoryViewModel;

    public static HistoryChartSliderCommonFragment newInstance(Integer tabPosition) {
        HistoryChartSliderCommonFragment fragment = new HistoryChartSliderCommonFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POS, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compareHistoryViewModel = new ViewModelProvider(this,
                new CompareHistoryViewModel.Factory(getActivity().getApplication())).get(CompareHistoryViewModel.class);
        if (getArguments() != null) {
            tabPosition = getArguments().getInt(TAB_POS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chart_common, container, false);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.chartCommonLinearLayout);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<SessionsWithActivity> sessionsWithActivities = null;
        LinearLayout.LayoutParams chartParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.9f
        );
        LinearLayout.LayoutParams paramTextView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.1f
        );
        paramTextView.setMargins(0,20, 0, 0);

        switch (tabPosition) {
            // Step count chart
            case 0:
                try {
                    sessionsWithActivities = compareHistoryViewModel.getDataForCompletedActivityCharts();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sessionsWithActivities != null && !sessionsWithActivities.isEmpty()) {
                    BarChart barChart = new BarChart(getContext());
                    generateStepCountCharts(sessionsWithActivities, barChart);
                    barChart.setLayoutParams(chartParam);
                    TextView textView = new TextView(getContext());
                    textView.setText("Step Counts across all Activities");
                    textView.setLayoutParams(paramTextView);
                    linearLayout.addView(barChart);
                    linearLayout.addView(textView);
                }
                break;
            // Activity Time Chart
            case 1:
                try {
                    sessionsWithActivities = compareHistoryViewModel.getDataForCompletedActivityCharts();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sessionsWithActivities != null && !sessionsWithActivities.isEmpty()) {
                    BarChart barChart = new BarChart(getContext());
                    generateActivityTimeCharts(sessionsWithActivities, barChart);
                    barChart.setLayoutParams(chartParam);
                    TextView textView2 = new TextView(getContext());
                    textView2.setText("Time Spent across all Activities");
                    textView2.setLayoutParams(paramTextView);
                    linearLayout.addView(barChart);
                    linearLayout.addView(textView2);
                }
                break;
            default:
                return;
        }
    }

    private void generateActivityTimeCharts(List<SessionsWithActivity> sessionsWithActivities, BarChart chartTime) {
        List<BarEntry> entriesTime = new ArrayList<BarEntry>();
        List<String> xAxisLabels = new ArrayList<String>();

        for (int i = 0 ; i < sessionsWithActivities.size(); i++){
            long diffInMillis = Math.abs(
                    sessionsWithActivities.get(i).getActivitySession().getCompletedDateTime().getTime() -
                            sessionsWithActivities.get(i).getActivitySession().getStartDateTime().getTime());
            float seconds = diffInMillis / 1000;
            float minutes = seconds / 60;
            float hours =  minutes / 60;
            entriesTime.add(new BarEntry(i, minutes));
            xAxisLabels.add(sessionsWithActivities.get(i).getActivityPackage().getTitle());
        }
        setXAxisForCharts(xAxisLabels, chartTime);
        generateChart(entriesTime, "Time Spent (minutes) on each activity", chartTime);
    }

    private void generateStepCountCharts(List<SessionsWithActivity> sessionsWithActivities, BarChart chartSteps) {
        List<BarEntry> entriesSteps = new ArrayList<BarEntry>();
        List<String> xAxisLabels = new ArrayList<String>();

        for (int i = 0 ; i < sessionsWithActivities.size(); i++){
            entriesSteps.add(new BarEntry(i, sessionsWithActivities.get(i).getActivitySession().getStepCount()));
            xAxisLabels.add(sessionsWithActivities.get(i).getActivityPackage().getTitle());
        }
        setXAxisForCharts(xAxisLabels, chartSteps);
        generateChart(entriesSteps, "Step Count of each activity", chartSteps);
    }

    /**
     * Method to set X-axis for charts
     * @param xAxisLabels
     */
    private void setXAxisForCharts(List<String> xAxisLabels, BarChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xAxisLabels.get((int) value);
            }
        });
    }

    /**
     * Method to generate bar charts based on parameter dataset.
     * @param entries
     * @param label
     * @param chart
     */
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
}
