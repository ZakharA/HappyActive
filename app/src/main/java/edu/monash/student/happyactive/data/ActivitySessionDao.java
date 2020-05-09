package edu.monash.student.happyactive.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.relationships.ActivityWithSessions;

@Dao
public abstract class ActivitySessionDao {
    @Update(onConflict = OnConflictStrategy.REPLACE, entity = ActivitySession.class)
    public abstract void updateSession(ActivitySession activitySession);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addNewJournalEntry(ActivityJournal journalEntry);

    @Insert
    public abstract long insertSession(ActivitySession activitySession);

    @Query("SELECT * FROM activitySession")
    public abstract LiveData<List<ActivitySession>> getAllSessionsRecords();

    @Transaction
    @Query("SELECT * FROM activityPackage WHERE id = :id")
    public abstract LiveData<List<ActivityWithSessions>> getActivityWithSessionById(long id);

    public  void cancelSession(ActivitySession session){
        session.completedDateTime = new Date();
        session.status = ActivityPackageStatus.CANCELED;
        updateSession(session);
    }
    @Query("SELECT * FROM activitysession WHERE activityId = :acitivityId AND id = :id")
    public abstract ActivitySession findSessionByActivityId(long acitivityId, long id);

    @Query("SELECT * FROM ActivitySession WHERE activityId = :activityId and status = :status")
    public abstract ActivitySession checkInProgress(long activityId, ActivityPackageStatus status);
}
