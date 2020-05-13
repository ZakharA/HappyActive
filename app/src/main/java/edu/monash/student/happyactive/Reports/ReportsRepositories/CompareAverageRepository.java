package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.PreferencesDao.PreferencesDao;
import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareAverageReportsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * Repository class for Compare Average Screen.
 */
public class CompareAverageRepository {

    private CompareAverageReportsDao compareAverageReportsDao;
    private PreferencesDao preferencesDao;

    public CompareAverageRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        compareAverageReportsDao = db.compareAverageReportsDao();
        preferencesDao = db.preferencesDao();
    }

    /**
     * Method for fetching activity sessions which are completed.
     * @return
     */
    public LiveData<List<ActivitySession>> getDataForCompletedActivity() {
        return compareAverageReportsDao.getDataForCompletedActivity(ActivityPackageStatus.COMPLETED);
    }

    /**
     * Method for fetching activity sessions which are completed for charts.
     * @return
     */
    public List<ActivitySession> getDataForCompletedActivitiesForChart() throws ExecutionException, InterruptedException {
        return new fetchCompletedActivityDataAsyncTask(compareAverageReportsDao).execute(ActivityPackageStatus.COMPLETED).get();
    }

    public UserPref fetchUserAgeGender() throws ExecutionException, InterruptedException {
        return new fetchUserAgeGenderAsyncTask(preferencesDao).execute().get();
    }

    private static class fetchCompletedActivityDataAsyncTask extends AsyncTask<Object, Void, List<ActivitySession>> {
        private CompareAverageReportsDao mSessionDao;

        public fetchCompletedActivityDataAsyncTask(CompareAverageReportsDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected List<ActivitySession> doInBackground(Object[] objects) {
            return mSessionDao.getDataForCompletedActivitiesForChart((ActivityPackageStatus) objects[0]);
        }
    }

    private static class fetchUserAgeGenderAsyncTask extends AsyncTask<Object, Void, UserPref> {
        private PreferencesDao mSessionDao;

        public fetchUserAgeGenderAsyncTask(PreferencesDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected UserPref doInBackground(Object[] objects) {
            return mSessionDao.getPreferences(1);
        }
    }
}
