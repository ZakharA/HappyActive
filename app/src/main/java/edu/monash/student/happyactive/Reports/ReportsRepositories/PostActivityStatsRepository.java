package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityPackageDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.PostActivityStatsDao;
import edu.monash.student.happyactive.data.dao.UserScoreDao.UserScoreDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.UserScore;

/**
 * Repository class for Post Activity Statistics Screen.
 */
public class PostActivityStatsRepository {

    private PostActivityStatsDao postActivityStatsDao;
    private ActivityPackageDao activityPackageDao;
    private UserScoreDao userScoreDao;

    public PostActivityStatsRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        postActivityStatsDao = db.postActivityStatsDao();
        activityPackageDao = db.activityPackageDao();
        userScoreDao = db.userScoreDao();
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
        new PostActivityStatsRepository.setStatusCompleteAsyncTask(postActivityStatsDao).execute(activitySession);
    }

    public void updateUserScore(ActivitySession currentActivitySession) {
        new PostActivityStatsRepository.updateUserScoreAsyncTask(activityPackageDao, userScoreDao).execute(currentActivitySession);
    }

    private static class setStatusCompleteAsyncTask extends AsyncTask<ActivitySession, Void, Void> {
        private PostActivityStatsDao mSessionDao;

        public setStatusCompleteAsyncTask(PostActivityStatsDao mSessionDao) {
            this.mSessionDao = mSessionDao;
        }

        @Override
        protected Void doInBackground(ActivitySession... activitySessions) {
            mSessionDao.setStatusCompletedPostActivity(activitySessions[0]);
            return null;
        }

    }

    private static class updateUserScoreAsyncTask extends AsyncTask<ActivitySession, Void, Void> {
        private ActivityPackageDao mActivityPackageDao;
        private UserScoreDao mUserScoreDao;

        public updateUserScoreAsyncTask(ActivityPackageDao activityPackageDao, UserScoreDao userScoreDao) {
            this.mActivityPackageDao = activityPackageDao;
            this.mUserScoreDao = userScoreDao;
        }

        @Override
        protected Void doInBackground(ActivitySession... activitySessions) {
            long activityLevel = mActivityPackageDao.getActivityLevelFromId(activitySessions[0].getActivityId());
            UserScore userScore = mUserScoreDao.getUserScoreData(1);
            long currentScore = userScore.getCurrentScore();
            long newScore = (long) (currentScore + (0.1*activitySessions[0].getStepCount()) + (10*activityLevel));
            userScore.setOldScore(currentScore);
            userScore.setCurrentScore(newScore);
            mUserScoreDao.updateUserScoreData(userScore);
            return null;
        }

    }
}
