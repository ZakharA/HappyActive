package edu.monash.student.happyactive.ActivityPackages.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import java.util.List;

import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityPackageDao;
import edu.monash.student.happyactive.data.ActivityPackageDatabase;
import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.Task;
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

    public long insertNewActivityPackage(ActivityPackage activityPackage) {
        return activityPackageDao.insertActivity(activityPackage);
    }

    public long insertNewActivityWithTasks(ActivityPackage activityPackage, List<Task> tasks) {
        return activityPackageDao.insertNew(activityPackage, tasks);
    }

    public LiveData<ActivityPackage> getActivityPackageById(long argItemId) {
        return  activityPackageDao.findById(argItemId);
    }

    public DataSource.Factory<Integer, ActivityPackage> getActivityPackagesAsList() {
        return activityPackageDao.getAllActivityPackagesAsPagedList();
    }

    public DataSource.Factory<Integer, ActivityPackage> getInProgressActivityPackagesAsPagedList() {
        return activityPackageDao.getInProgressActivityPackagesAsPagedList(ActivityPackageStatus.STARTED);
    }

    public DataSource.Factory<Integer, ActivityPackage> getRecommendedActivityPackagesAsPagedList() {
        return activityPackageDao.getRecommendedActivityPackagesAsPagedList(1);
    }
}
