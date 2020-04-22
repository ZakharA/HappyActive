package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivitySession;

@Dao
public abstract class CompareHistoryReportsDao {

    @Query("Select * from ActivitySession where status == 'COMPLETED'")
    public abstract List<ActivitySession> getDataForCompletedActivity();
}
