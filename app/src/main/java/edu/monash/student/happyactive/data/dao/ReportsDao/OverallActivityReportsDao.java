package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivitySession;

@Dao
public abstract class OverallActivityReportsDao {

    @Query("Select * from ActivitySession where status == 'COMPLETED'")
    public abstract List<ActivitySession> getDataForCompletedActivity();

    @Query("Select sum(stepCount) from ActivitySession where status == 'COMPLETED'")
    public abstract LiveData<Integer> getTotalStepCountForCompletedActivity();

    @Query("Select sum(*) from ActivitySession where status == 'COMPLETED'")
    public abstract LiveData<Integer> getTotalCompletedActivity();

    @Query("Select sum(julianday(CompletedDateTime) - julianday(StartDateTime)) from ActivitySession where status == 'COMPLETED'")
    public abstract LiveData<Date> getTotalTimeSpentOnActivities();
}
