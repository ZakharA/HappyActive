package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;

@Dao
public abstract class CompareHistoryReportsDao {

    @Query("Select * from ActivitySession where status == :status")
    public abstract LiveData<List<ActivitySession>> getDataForCompletedActivity(ActivityPackageStatus status);
}
