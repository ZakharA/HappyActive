package edu.monash.student.happyactive.ActivityPackages;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageDao;
import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.SessionPhoto;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

public class ActivityPackageRepository {
    private ActivityPackageDao activityPackageDao;
    private LiveData<List<ActivityPackage>> allActivityPackages;

    public ActivityPackageRepository(Application application) {
        ActivityPackageDatabase db = ActivityPackageDatabase.getDatabase(application);
        activityPackageDao = db.activityPackageDao();
        allActivityPackages = activityPackageDao.getAllActivityPackages();
    }

    public LiveData<List<ActivityPackage>> getAllActivityPackages(){
        return allActivityPackages;
    }
    public LiveData<ActivityPackageWithTasks> getActivityWithTasks(long id) {
        return activityPackageDao.getActivityPackageWithTasksById(id);
    }

    public void saveSession(ActivitySession session) {
        activityPackageDao.insertSession(session);
    }

    public void updateSession(ActivitySession session) {
        activityPackageDao.updateSession(session);
    }

    public void cancelSession(ActivitySession session) {
        activityPackageDao.cancelSession(session);
    }
}
