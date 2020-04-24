package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivitySession;

@Dao
public abstract class PostActivityStatsDao {

    @Query("Select * from ActivitySession where id == :currentId")
    public abstract ActivitySession getDataForCurrentSession(Integer currentId);

    @Query("Update ActivitySession Set status = 'COMPLETED' where id == :currentId")
    public abstract void setStatusCompletedPostActivity(Integer currentId);
}
