package edu.monash.student.happyactive.Reports.CompareHistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.monash.student.happyactive.Reports.ReportsRepositories.OverallActivityRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareHistoryViewModel extends AndroidViewModel {

    private OverallActivityRepository overallActivityRepository;
    private List<ActivitySession> dataForCompletedActivities;
    private String avgTime = "";
    private Double avgSteps = 0.0;

    public CompareHistoryViewModel(@NonNull Application application) {
        super(application);
        overallActivityRepository = new OverallActivityRepository(application);
        dataForCompletedActivities = overallActivityRepository.getDataForCompletedActivity();
        calculateAvgStepCountAndTime();
    }

    public void calculateAvgStepCountAndTime() {
        Double totalSteps = 0.0;
        long totalTime = 0;
        for (ActivitySession activity : this.dataForCompletedActivities) {
            totalSteps += activity.stepCount;
            long diffInMillies = Math.abs(activity.CompletedDateTime.getTime() - activity.StartDateTime.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            totalTime += diff;
        }
        this.setAvgTime(calculateAverageOfTime(totalTime));
        this.setAvgSteps(totalSteps/this.dataForCompletedActivities.size());
    }

    public static String calculateAverageOfTime(Long totalTimeMs) {
        long seconds = totalTimeMs / 1000;
        long minutes = seconds / 60;
        long hours =  minutes / 60;
        minutes = minutes % 60;

        String avgTime = hours + " hours " + minutes + " minutes";

        return avgTime;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public Double getAvgSteps() {
        return avgSteps;
    }

    public void setAvgSteps(Double avgSteps) {
        this.avgSteps = avgSteps;
    }
}
