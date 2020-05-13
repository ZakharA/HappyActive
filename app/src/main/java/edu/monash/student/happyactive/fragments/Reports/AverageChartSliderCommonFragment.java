package edu.monash.student.happyactive.fragments.Reports;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.R;
import edu.monash.student.happyactive.Reports.CompareAverage.CompareAverageViewModel;
import edu.monash.student.happyactive.Reports.CompareHistory.CompareHistoryViewModel;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.relationships.SessionsWithActivity;

public class AverageChartSliderCommonFragment extends Fragment {

    private static final String TAB_POS = "Tab Position";

    private Integer tabPosition;
    private View rootView;
    private LinearLayout linearLayout;
    private CompareAverageViewModel compareAverageViewModel;

    public static AverageChartSliderCommonFragment newInstance(Integer tabPosition) {
        AverageChartSliderCommonFragment fragment = new AverageChartSliderCommonFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POS, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compareAverageViewModel = new ViewModelProvider(this,
                new CompareAverageViewModel.Factory(getActivity().getApplication())).get(CompareAverageViewModel.class);
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
        List<ActivitySession> activitySession = null;
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
        paramTextView.gravity = Gravity.CENTER;

        switch (tabPosition) {
            // Step count chart
            case 0:
                try {
                    activitySession = compareAverageViewModel.getDataForCompletedActivitiesForChart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BarChart barChartSteps = new BarChart(getContext());
                try {
                    makeBarChartSteps(activitySession, barChartSteps);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                barChartSteps.setLayoutParams(chartParam);
                TextView textView = new TextView(getContext());
                textView.setText("Comparison of User Steps Counts vs Average Population");
                textView.setLayoutParams(paramTextView);
                linearLayout.addView(barChartSteps,0);
                linearLayout.addView(textView,1);
                break;
            // Activity Time Chart
            case 1:
                try {
                    activitySession = compareAverageViewModel.getDataForCompletedActivitiesForChart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BarChart barChartWearTime = new BarChart(getContext());
                makeBarChartWearTime(activitySession, barChartWearTime);
                barChartWearTime.setLayoutParams(chartParam);
                TextView textView2 = new TextView(getContext());
                textView2.setText("Comparison of User Activity Time vs Average Population");
                textView2.setLayoutParams(paramTextView);
                linearLayout.addView(barChartWearTime,0);
                linearLayout.addView(textView2,1);
                break;
            // Arthritis Population Chart
            case 2:
                LineChart lineChart = new LineChart(getContext());
                generateLineChart(lineChart);
                lineChart.setLayoutParams(chartParam);
                TextView textView3 = new TextView(getContext());
                textView3.setText("Number of people suffering from Arthritis - Population Growth");
                textView3.setLayoutParams(paramTextView);
                linearLayout.addView(lineChart,0);
                linearLayout.addView(textView3,1);
                break;
            default:
                return;
        }
    }

    /**
     * Method to make the bar chart for step count and wear time for user vs average population data.
     * @param activitySession, barChart
     */
    private void makeBarChartSteps(List<ActivitySession> activitySession, BarChart barChart) throws ExecutionException, InterruptedException {
        Map<String,Long> dataMap = compareAverageViewModel.calculateAvgStepCountAndTime(activitySession);
        ArrayList<String> result = compareAverageViewModel.processStepsByAgeFile(getActivity());
        List<BarEntry> entriesStep = new ArrayList<BarEntry>();

        entriesStep.add(new BarEntry(0, Long.parseLong(result.get(0))));
        entriesStep.add(new BarEntry(1, dataMap.get("AvgSteps")));

        BarDataSet dataSet = new BarDataSet(entriesStep, "Avg Steps/Day-Australia vs User");
        BarData barData = new BarData();
        barData.addDataSet(dataSet);
        barData.setBarWidth(0.9f);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    /**
     * Method to make the bar chart for step count and wear time for user vs average population data.
     * @param activitySession, barChart
     */
    private void makeBarChartWearTime(List<ActivitySession> activitySession, BarChart barChart) {
        Map<String,Long> dataMap = compareAverageViewModel.calculateAvgStepCountAndTime(activitySession);
        List<BarEntry> entriesTime = new ArrayList<BarEntry>();

        entriesTime.add(new BarEntry(0, 13.07f));
        entriesTime.add(new BarEntry(1, dataMap.get("AvgTime")));

        BarDataSet dataSet = new BarDataSet(entriesTime, "Avg Time-Australia vs User");
        BarData barData = new BarData();
        barData.addDataSet(dataSet);
        barData.setBarWidth(0.9f);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    /**
     * Method to generate the line graph for arthritis population trend across the years.
     * @param lineChart
     */
    private void generateLineChart(LineChart lineChart) {
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
}
