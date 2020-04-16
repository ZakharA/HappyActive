package edu.monash.student.happyactive.Reports.CompareHistory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import edu.monash.student.happyactive.Reports.ReportsRepositories.OverallActivityRepository;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareHistoryViewModel extends AndroidViewModel {

    private OverallActivityRepository overallActivityRepository;

    public CompareHistoryViewModel(@NonNull Application application) {
        super(application);
        overallActivityRepository = new OverallActivityRepository(application);
    }

    public List<ActivitySession> getDataForCompletedActivity() {
        return overallActivityRepository.getDataForCompletedActivity();
    }
}
