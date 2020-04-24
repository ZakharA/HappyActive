package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.OverallActivityReportsDao;
import edu.monash.student.happyactive.data.ReportsDatabase;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class OverallActivityRepository {

    private OverallActivityReportsDao overallActivityReportsDao;

    public OverallActivityRepository(Application application) {
        ReportsDatabase db = ReportsDatabase.getDatabase(application);
        overallActivityReportsDao = db.overallActivityReportsDao();
    }

    public LiveData<Date> getDataForCompletedActivity(ActivityPackageStatus status) {
        return overallActivityReportsDao.getDataForCompletedActivity(status);
    }

    public LiveData<Integer> getTotalStepCountForCompletedActivity(ActivityPackageStatus status) {
        return overallActivityReportsDao.getTotalStepCountForCompletedActivity(status);
    }

    public LiveData<Integer> getTotalCompletedActivity(ActivityPackageStatus status) {
        return overallActivityReportsDao.getTotalCompletedActivity(status);
    }
}
