package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareHistoryReportsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * Repository class for Compare History Screen.
 */
public class CompareHistoryRepository {

    private CompareHistoryReportsDao compareHistoryReportsDao;
    private LiveData<List<ActivitySession>> dataForCompletedActivity;

    public CompareHistoryRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        compareHistoryReportsDao = db.compareHistoryReportsDao();
        dataForCompletedActivity = compareHistoryReportsDao.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @return
     */
    public LiveData<List<ActivitySession>> getDataForCompletedActivity() {
        return dataForCompletedActivity;
    }
}
