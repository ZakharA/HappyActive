package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareHistoryReportsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareHistoryRepository {

    private CompareHistoryReportsDao compareHistoryReportsDao;
    private LiveData<List<ActivitySession>> dataForCompletedActivity;

    public CompareHistoryRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        compareHistoryReportsDao = db.compareHistoryReportsDao();
        dataForCompletedActivity = compareHistoryReportsDao.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    public LiveData<List<ActivitySession>> getDataForCompletedActivity() {
        return dataForCompletedActivity;
    }
}
