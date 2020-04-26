package edu.monash.student.happyactive.Reports.ReportsRepositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import edu.monash.student.happyactive.data.ReportsDatabase;
import edu.monash.student.happyactive.data.dao.ReportsDao.PostActivityStatsDao;
import edu.monash.student.happyactive.data.entities.ActivitySession;

public class PostActivityStatsRepository {

    private PostActivityStatsDao postActivityStatsDao;

    public PostActivityStatsRepository(Application application) {
        ReportsDatabase db = ReportsDatabase.getDatabase(application);
        postActivityStatsDao = db.postActivityStatsDao();
    }

    public LiveData<ActivitySession> getDataForCurrentSession(Integer currentId) {
        return postActivityStatsDao.getDataForCurrentSession(currentId);
    }

    public void setStatusCompletedPostActivity(ActivitySession activitySession) {
        postActivityStatsDao.setStatusCompletedPostActivity(activitySession);
    }
}
