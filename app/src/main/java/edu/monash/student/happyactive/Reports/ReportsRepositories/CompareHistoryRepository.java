package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareHistoryReportsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.relationships.SessionsWithActivity;

/**
 * Repository class for Compare History Screen.
 */
public class CompareHistoryRepository {

    private CompareHistoryReportsDao compareHistoryReportsDao;

    public CompareHistoryRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        compareHistoryReportsDao = db.compareHistoryReportsDao();
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @return
     */
    public LiveData<List<ActivitySession>> getDataForCompletedActivity() {
        return compareHistoryReportsDao.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    public List<SessionsWithActivity> getDataForCompletedActivityCharts() throws ExecutionException, InterruptedException {
        return new setAsyncTask(compareHistoryReportsDao).execute(ActivityPackageStatus.COMPLETED, ActivityPackageStatus.STARTED).get();
    }

    private static class setAsyncTask extends AsyncTask<Object, Void, List<SessionsWithActivity>> {
        private CompareHistoryReportsDao mSessionDao;

        public setAsyncTask(CompareHistoryReportsDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected List<SessionsWithActivity> doInBackground(Object[] objects) {
            return mSessionDao.getDataForCompletedActivityCharts((ActivityPackageStatus) objects[0]);
        }
    }
}
