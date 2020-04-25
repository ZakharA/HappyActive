package edu.monash.student.happyactive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ReportsActivity extends AppCompatActivity {

    ImageView compareHistoryImg;
    ImageView compareAverageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        //compareHistoryImg = findViewById(R.id.compareHistoryImg);
        //compareAverageImg = findViewById(R.id.compareAverageImg);

        compareHistoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsActivity.this,
                        edu.monash.student.happyactive.CompareHistoryActivity.class);
                startActivity(intent);
            }
        });

        compareAverageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsActivity.this,
                        edu.monash.student.happyactive.CompareAverageActivity.class);
                startActivity(intent);
            }
        });
    }
}
