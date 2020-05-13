package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * DAO class for Compare Average reports screen.
 */
@Dao
public abstract class CompareAverageReportsDao {

    /**
     * Method for fetching activity sessions which are completed.
     * @param status
     * @return
     */
    @Query("Select * from ActivitySession where status == :status")
    public abstract LiveData<List<ActivitySession>> getDataForCompletedActivity(ActivityPackageStatus status);

    /**
     * Method for fetching activity sessions which are completed.
     * @param status
     * @return
     */
    @Query("Select * from ActivitySession where status == :status")
    public abstract List<ActivitySession> getDataForCompletedActivitiesForChart(ActivityPackageStatus status);
}
