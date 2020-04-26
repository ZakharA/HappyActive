package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import edu.monash.student.happyactive.data.entities.ActivitySession;

@Dao
public abstract class PostActivityStatsDao {

    @Query("Select * from ActivitySession where id == :currentId")
    public abstract LiveData<ActivitySession> getDataForCurrentSession(Integer currentId);

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = ActivitySession.class)
    public abstract void setStatusCompletedPostActivity(ActivitySession activitySession);
}
