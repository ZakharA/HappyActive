package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import edu.monash.student.happyactive.ActivityPackages.Repositories.ActivityPhotoRepository;
import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.ReportsDao.PostActivityStatsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * Repository class for Post Activity Statistics Screen.
 */
public class PostActivityStatsRepository {

    private PostActivityStatsDao postActivityStatsDao;

    public PostActivityStatsRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        postActivityStatsDao = db.postActivityStatsDao();
    }

    /**
     * Method for fetching current activity.
     * @param currentId
     * @return
     */
    public LiveData<ActivitySession> getDataForCurrentSession(Integer currentId) {
        return postActivityStatsDao.getDataForCurrentSession(currentId);
    }

    /**
     * Method for updating the activity session status to completed.
     * @param activitySession
     */
    public void setStatusCompletedPostActivity(ActivitySession activitySession) {
        new PostActivityStatsRepository.setAsyncTask(postActivityStatsDao).execute(activitySession);
    }

    private static class setAsyncTask extends AsyncTask<ActivitySession, Void, Void> {
        private PostActivityStatsDao mSessionDao;

        public setAsyncTask(PostActivityStatsDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected Void doInBackground(ActivitySession... activitySessions) {
            mSessionDao.setStatusCompletedPostActivity(activitySessions[0]);
            return null;
        }

    }
}
