package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.dao.ReportsDao.PostActivityStatsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class PostActivityStatsRepository {

    private PostActivityStatsDao postActivityStatsDao;

    public PostActivityStatsRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        postActivityStatsDao = db.postActivityStatsDao();
    }

    public LiveData<ActivitySession> getDataForCurrentSession(Integer currentId) {
        return postActivityStatsDao.getDataForCurrentSession(currentId);
    }

    public void setStatusCompletedPostActivity(ActivitySession activitySession) {
        postActivityStatsDao.setStatusCompletedPostActivity(activitySession);
    }
}
