package edu.monash.student.happyactive.Reports.OverallActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.Reports.ReportsRepositories.OverallActivityRepository;
import edu.monash.student.happyactive.data.entities.UserScore;
import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * View Model class for Overall Activity Home Screen.
 */
public class OverallActivityViewModel extends AndroidViewModel {

    private OverallActivityRepository overallActivityRepository;

    public OverallActivityViewModel(@NonNull Application application) {
        super(application);
        overallActivityRepository = new OverallActivityRepository(application);
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @param
     * @return
     */
    public LiveData<List<ActivitySession>> getTotalTimeSpentOnActivities() {
        return overallActivityRepository.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    /**
     * Method for fetching sum of step counts of completed activity sessions for Home Screen.
     * @param
     * @return
     */
    public LiveData<Integer> getTotalStepCount() {
        return overallActivityRepository.getTotalStepCountForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    /**
     * Method for fetching sum of completed activity sessions for Home Screen.
     * @param
     * @return
     */
    public LiveData<Integer> getTotalActivitiesCompleted() {
        return overallActivityRepository.getTotalCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    public LiveData<UserScore> getCurrentScore() {
        return overallActivityRepository.getCurrentScore();
    }
}
