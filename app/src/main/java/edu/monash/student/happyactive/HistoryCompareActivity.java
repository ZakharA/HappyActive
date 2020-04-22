package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import edu.monash.student.happyactive.Reports.CompareHistory.CompareHistoryViewModel;

public class HistoryCompareActivity extends AppCompatActivity {

    private TextView avgSteps;
    private TextView avgTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_compare);

        avgSteps = findViewById(R.id.avgStepsActivity);
        avgTime = findViewById(R.id.avgTimeActivity);

        CompareHistoryViewModel compareHistoryViewModel = new ViewModelProvider(this).get(CompareHistoryViewModel.class);

        avgSteps.setText(compareHistoryViewModel.getAvgSteps().toString());
        avgTime.setText(compareHistoryViewModel.getAvgTime());

        /*BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();*/
    }
}
