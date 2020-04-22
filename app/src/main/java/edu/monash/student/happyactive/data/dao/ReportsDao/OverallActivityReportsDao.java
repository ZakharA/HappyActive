package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;

@Dao
public abstract class OverallActivityReportsDao {

    @Query("Select sum(julianday(completedDateTime) - julianday(startDateTime)) from ActivitySession where status == :status")
    public abstract LiveData<Date> getDataForCompletedActivity(ActivityPackageStatus status);

    @Query("Select sum(stepCount) from ActivitySession where status == :status")
    public abstract LiveData<Integer> getTotalStepCountForCompletedActivity(ActivityPackageStatus status);

    @Query("Select COUNT(*) from ActivitySession where status == :status")
    public abstract LiveData<Integer> getTotalCompletedActivity(ActivityPackageStatus status);
}
