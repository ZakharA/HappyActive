package edu.monash.student.happyactive.Reports.OverallActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.monash.student.happyactive.Reports.ReportsRepositories.OverallActivityRepository;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class OverallActivityViewModel extends AndroidViewModel {

    private OverallActivityRepository overallActivityRepository;

    public OverallActivityViewModel(@NonNull Application application) {
        super(application);
        overallActivityRepository = new OverallActivityRepository(application);
    }

    public LiveData<List<ActivitySession>> getTotalTimeSpentOnActivities() {
        return overallActivityRepository.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }
    
    public LiveData<Integer> getTotalStepCount() {
        return overallActivityRepository.getTotalStepCountForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    public LiveData<Integer> getTotalActivitiesCompleted() {
        return overallActivityRepository.getTotalCompletedActivity(ActivityPackageStatus.COMPLETED);
    }
}
