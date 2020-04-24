package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
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

public class CompareHistoryActivity extends AppCompatActivity {

    protected CompareHistoryViewModel compareHistoryViewModel;
    private TextView avgSteps;
    private TextView avgTime;
    private BarChart chartTime;
    private BarChart chartSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_compare);

        avgSteps = findViewById(R.id.avgStepsActivity);
        avgTime = findViewById(R.id.avgTimeActivity);
        chartSteps = findViewById(R.id.chartStepCompare);
        chartTime = findViewById(R.id.chartTimeCompare);

        compareHistoryViewModel = new ViewModelProvider(this,
                new CompareHistoryViewModel.Factory(this.getApplication())).get(CompareHistoryViewModel.class);

        compareHistoryViewModel.getDataForCompletedActivities().observe(this, new Observer<List<ActivitySession>>() {
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
            entriesSteps.add(new BarEntry(activitySession.get(i).getStepCount(), i));
            long diffInMillis = Math.abs(activitySession.get(i).getCompletedDateTime().getTime() - activitySession.get(i).getStartDateTime().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            float seconds = diff / 1000;
            float minutes = seconds / 60;
            float hours =  minutes / 60;
            entriesTime.add(new BarEntry(hours, i));
            xAxisLabels.add(Long.toString(activitySession.get(i).getActivityId()));
        }
        setXAxisForCharts(xAxisLabels);
        generateChart(entriesSteps, "Step Count of each activity", chartSteps);
        generateChart(entriesTime, "Time Spent on each activity", chartTime);
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
        barData.setBarWidth(0.9f);
        chart.setData(barData);
        chart.setFitBars(true);
        chart.animateXY(2000, 2000);
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
