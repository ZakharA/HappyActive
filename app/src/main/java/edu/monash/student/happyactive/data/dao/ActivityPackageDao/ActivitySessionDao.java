package edu.monash.student.happyactive.data.dao.ActivityPackageDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotos;
import edu.monash.student.happyactive.data.relationships.ActivitySessionWithPhotosAndPrompts;
import edu.monash.student.happyactive.data.relationships.ActivityWithSessions;
import edu.monash.student.happyactive.data.relationships.SessionWithPhotoAndJournal;

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
    @Query("SELECT * FROM activitySession WHERE activityId = :activityId AND id = :id")
    public abstract ActivitySession findSessionByActivityId(long activityId, long id);

    @Insert
    public abstract void insertPrompts(List<InteractivePrompt> list);

    @Query("SELECT * FROM activitySession where status = :status ")
    public abstract LiveData<List<ActivitySession>> getAllCompletedSessions(ActivityPackageStatus status);

    @Transaction
    @Query("SELECT * FROM activitySession WHERE strftime('%m', datetime(completedDateTime/1000, 'unixepoch')) = :mSelectedMonth")
    public abstract LiveData<List<ActivitySessionWithPhotos>> getSessionWithPhotoBy(String mSelectedMonth);

    @Transaction
    @Query("SELECT * FROM activitySession WHERE id = :sessionId")
    public abstract LiveData<SessionWithPhotoAndJournal> getSessionWIthPhotoAndJournalBy(long sessionId);

    @Query("SELECT * FROM ActivitySession WHERE activityId = :activityId and status = :status")
    public abstract ActivitySession checkInProgress(long activityId, ActivityPackageStatus status);

    @Transaction
    @Query("SELECT * FROM activitySession WHERE strftime('%m', datetime(completedDateTime/1000, 'unixepoch')) = :mSelectedMonth")
    public abstract LiveData<List<ActivitySessionWithPhotosAndPrompts>>  getSessionWithPhotoAndPromptsBy(String mSelectedMonth);

    @Transaction
    @Query("SELECT * FROM activitySession WHERE status = 1")
    public abstract LiveData<List<ActivitySessionWithPhotosAndPrompts>> getSessionsWithPhotoAndPrompts();

    @Transaction
    @Query("SELECT * FROM activitySession WHERE completedDateTime BETWEEN :first AND :second")
    public abstract LiveData<List<ActivitySessionWithPhotosAndPrompts>> getSessionWithPromptsInRange(Long first, Long second);
}
