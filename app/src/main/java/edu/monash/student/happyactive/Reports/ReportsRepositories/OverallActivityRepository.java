package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.OverallActivityReportsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * Repository class for Overall Activity Home Screen.
 */
public class OverallActivityRepository {

    private OverallActivityReportsDao overallActivityReportsDao;

    public OverallActivityRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        overallActivityReportsDao = db.overallActivityReportsDao();
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @param status
     * @return
     */
    public LiveData<List<ActivitySession>> getDataForCompletedActivity(ActivityPackageStatus status) {
        return overallActivityReportsDao.getDataForCompletedActivity(status);
    }

    /**
     * Method for fetching sum of step counts of completed activity sessions for Home Screen.
     * @param status
     * @return
     */
    public LiveData<Integer> getTotalStepCountForCompletedActivity(ActivityPackageStatus status) {
        return overallActivityReportsDao.getTotalStepCountForCompletedActivity(status);
    }

    /**
     * Method for fetching sum of completed activity sessions for Home Screen.
     * @param status
     * @return
     */
    public LiveData<Integer> getTotalCompletedActivity(ActivityPackageStatus status) {
        return overallActivityReportsDao.getTotalCompletedActivity(status);
    }

    public LiveData<Integer> getCurrentScore() {
        return overallActivityReportsDao.getCurrentScore();
    }
}
