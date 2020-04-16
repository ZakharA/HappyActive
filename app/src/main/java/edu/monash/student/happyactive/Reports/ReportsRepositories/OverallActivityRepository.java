package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.dao.ReportsDao.OverallActivityReportsDao;
import edu.monash.student.happyactive.data.ReportsDatabase;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class OverallActivityRepository {

    private OverallActivityReportsDao overallActivityReportsDao;

    public OverallActivityRepository(Application application) {
        ReportsDatabase db = ReportsDatabase.getDatabase(application);
        overallActivityReportsDao = db.overallActivityReportsDao();
    }

    public List<ActivitySession> getDataForCompletedActivity() {
        return overallActivityReportsDao.getDataForCompletedActivity();
    }

    public LiveData<Integer> getTotalStepCountForCompletedActivity() {
        return overallActivityReportsDao.getTotalStepCountForCompletedActivity();
    }

    public LiveData<Integer> getTotalCompletedActivity() {
        return overallActivityReportsDao.getTotalCompletedActivity();
    }

    public LiveData<Date> getTotalTimeSpentOnActivities() {
        return overallActivityReportsDao.getTotalTimeSpentOnActivities();
    }
}
