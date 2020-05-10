package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareAverageReportsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * Repository class for Compare Average Screen.
 */
public class CompareAverageRepository {

    private CompareAverageReportsDao compareAverageReportsDao;
    private LiveData<List<ActivitySession>> dataForCompletedActivity;

    public CompareAverageRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        compareAverageReportsDao = db.compareAverageReportsDao();
        dataForCompletedActivity = compareAverageReportsDao.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @return
     */
    public LiveData<List<ActivitySession>> getDataForCompletedActivity() {
        return dataForCompletedActivity;
    }
}
