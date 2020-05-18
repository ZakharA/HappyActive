package edu.monash.student.happyactive.data.dao.ReportsDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.enumerations.ActivityPackageStatus;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.relationships.SessionsWithActivity;

/**
 * DAO class for Compare History reports screen.
 */
@Dao
public abstract class CompareHistoryReportsDao {

    /**
     * Method for fetching activity sessions which are completed.
     * @param status
     * @return
     */
    @Query("Select * from ActivitySession where status == :status")
    public abstract LiveData<List<ActivitySession>> getDataForCompletedActivity(ActivityPackageStatus status);

    @Query("Select * from ActivitySession where status == :status order by completedDateTime desc limit 5")
    public abstract List<SessionsWithActivity> getDataForCompletedActivityCharts(ActivityPackageStatus status);
}
