package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import java.util.List;

import edu.monash.student.happyactive.data.dao.ReportsDao.CompareHistoryReportsDao;
import edu.monash.student.happyactive.data.ReportsDatabase;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class CompareHistoryRepository {

    private CompareHistoryReportsDao compareHistoryReportsDao;

    public CompareHistoryRepository(Application application) {
        ReportsDatabase db = ReportsDatabase.getDatabase(application);
        compareHistoryReportsDao = db.compareHistoryReportsDao();
    }

    public List<ActivitySession> getDataForCompletedActivity() {
        return compareHistoryReportsDao.getDataForCompletedActivity();
    }
}
