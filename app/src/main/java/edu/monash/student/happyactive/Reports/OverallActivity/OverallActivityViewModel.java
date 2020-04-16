package edu.monash.student.happyactive.Reports.OverallActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;

import edu.monash.student.happyactive.Reports.ReportsRepositories.OverallActivityRepository;

public class OverallActivityViewModel extends AndroidViewModel {

    private OverallActivityRepository overallActivityRepository;

    public OverallActivityViewModel(@NonNull Application application) {
        super(application);
        overallActivityRepository = new OverallActivityRepository(application);
    }

    public LiveData<Integer> getTotalStepCount() {
        return overallActivityRepository.getTotalStepCountForCompletedActivity();
    }

    public LiveData<Date> getTotalTimeSpentOnActivities() {
        return overallActivityRepository.getTotalTimeSpentOnActivities();
    }

    public LiveData<Integer> getTotalActivitiesCompleted() {
        return overallActivityRepository.getTotalCompletedActivity();
    }
}
