package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;

/**
 * DAO class for Overall Activity Home Screen.
 */
@Dao
public abstract class OverallActivityReportsDao {

    /**
     * Method for fetching all completed activity sessions for Home Screen.
     * @param status
     * @return
     */
    @Query("Select * from ActivitySession where status == :status")
    public abstract LiveData<List<ActivitySession>> getDataForCompletedActivity(ActivityPackageStatus status);

    /**
     * Method for fetching sum of step counts of completed activity sessions for Home Screen.
     * @param status
     * @return
     */
    @Query("Select sum(stepCount) from ActivitySession where status == :status")
    public abstract LiveData<Integer> getTotalStepCountForCompletedActivity(ActivityPackageStatus status);

    /**
     * Method for fetching sum of completed activity sessions for Home Screen.
     * @param status
     * @return
     */
    @Query("Select COUNT(*) from ActivitySession where status == :status")
    public abstract LiveData<Integer> getTotalCompletedActivity(ActivityPackageStatus status);

    @Query("Select currentScore from UserScore where id = 1")
    public abstract LiveData<Integer> getCurrentScore();
}
