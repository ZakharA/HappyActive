package edu.monash.student.happyactive.data;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.ActivityPackages.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.relationships.ActivityPackageWithTasks;
import edu.monash.student.happyactive.data.relationships.ActivityWithSessions;

@Dao
public abstract class ActivityPackageDao {
    @Query("SELECT * FROM activityPackage")
    public abstract LiveData<List<ActivityPackage>> getAllActivityPackages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertActivity(ActivityPackage activityPackage);

    @Query("SELECT * FROM activityPackage WHERE id = :id")
    public abstract ActivityPackage findById(long id);

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

    public void insertNew(ActivityPackage activityPackage, List<Task> tasks) {
        long id = insertActivity(activityPackage);
        for(Task task : tasks) {
            task.activityId = id;
        }
        insertTaskList(tasks);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertSession(ActivitySession activitySession);

    @Query("SELECT * FROM activitySession")
    public abstract LiveData<List<ActivitySession>> getAllSessionsRecords();

    @Transaction
    @Query("SELECT * FROM activityPackage WHERE id = :id")
    public abstract LiveData<List<ActivityWithSessions>> getActivityWithSessionById(long id);

    public void insertActivityWithSession(ActivityPackage activityPackage, ActivitySession session){
        long id = insertActivity(activityPackage);
        session.activityId = id;
        insertSession(session);
    }

    public  void cancelSession(ActivitySession session){
        session.completedDateTime = new Date();
        session.status = ActivityPackageStatus.CANCELED;
        updateSession(session);
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract void updateSession(ActivitySession activitySession);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addNewJournalEntry(ActivityJournal journalEntry);
}
