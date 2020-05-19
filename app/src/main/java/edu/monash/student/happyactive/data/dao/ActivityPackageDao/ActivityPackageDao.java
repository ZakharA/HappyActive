package edu.monash.student.happyactive.data.dao.ActivityPackageDao;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;

@Dao
public abstract class ActivityPackageDao {
    @Query("SELECT * FROM activityPackage")
    public abstract LiveData<List<ActivityPackage>> getAllActivityPackages();

    @Query("SELECT * FROM activityPackage order by activityLevel asc")
    public abstract DataSource.Factory<Integer, ActivityPackage>  getAllActivityPackagesAsPagedList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertActivity(ActivityPackage activityPackage);

    @Query("SELECT * FROM activityPackage WHERE id = :id")
    public abstract LiveData<ActivityPackage> findById(long id);

    @Query("DELETE FROM activityPackage WHERE id = :id")
    public abstract void deletePackage(long id);

    @Transaction
    @Query("SELECT * FROM ActivityPackage")
    public abstract LiveData<List<ActivityPackageWithTasks>> getAll();

    @Transaction
    @Query("SELECT * FROM ActivityPackage WHERE id = :id")
    public abstract LiveData<ActivityPackageWithTasks> getActivityPackageWithTasksById(long id);

    @Insert
    public abstract void insertTaskList(List<Task> tasks);

    @Query("SELECT * FROM task")
    public abstract  List<Task> getAllTasks();

    public long insertNew(ActivityPackage activityPackage, List<Task> tasks) {
        long id = insertActivity(activityPackage);
        for(Task task : tasks) {
            task.activityId = id;
        }
        insertTaskList(tasks);
        return id;
    }

    @Query("SELECT * FROM ActivityPackage WHERE id IN (SELECT activityId FROM ActivitySession WHERE status = :status) order by activityLevel asc")
    public abstract DataSource.Factory<Integer, ActivityPackage>  getInProgressActivityPackagesAsPagedList(ActivityPackageStatus status);

    @Query("SELECT activityLevel from ActivityPackage where id = :activityId")
    public abstract long getActivityLevelFromId(long activityId);

    @Query("SELECT * FROM ActivityPackage")
    public abstract List<ActivityPackage> getAllActivityPackagesForPref();

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = ActivityPackage.class)
    public abstract void updateActivityPackageForPref(ActivityPackage activityPackage);

    @Query("SELECT * FROM ActivityPackage WHERE isUserPreferred = :value")
    public abstract DataSource.Factory<Integer, ActivityPackage> getRecommendedActivityPackagesAsPagedList(long value);

    @Query("SELECT * FROM task WHERE id = :taskId")
    public abstract LiveData<Task> getTaskById(long taskId);
}
