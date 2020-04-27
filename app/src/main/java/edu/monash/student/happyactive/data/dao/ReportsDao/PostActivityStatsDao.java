package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * DAO class for Post Activity reports screen.
 */
@Dao
public abstract class PostActivityStatsDao {

    /**
     * Method for fetching current activity.
     * @param currentId
     * @return
     */
    @Query("Select * from ActivitySession where id == :currentId")
    public abstract LiveData<ActivitySession> getDataForCurrentSession(Integer currentId);

    /**
     * Method for updating the activity session status to completed.
     * @param activitySession
     */
    @Update(onConflict = OnConflictStrategy.REPLACE, entity = ActivitySession.class)
    public abstract void setStatusCompletedPostActivity(ActivitySession activitySession);
}
