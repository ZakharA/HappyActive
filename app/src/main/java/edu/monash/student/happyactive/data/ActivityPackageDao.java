package edu.monash.student.happyactive.data;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;

@Dao
public interface ActivityPackageDao {
    @Query("SELECT * FROM activityPackage")
    LiveData<List<ActivityPackage>> getAllActivityPackages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertActivity(ActivityPackage activityPackage);

    @Query("SELECT * FROM activityPackage WHERE id = :id")
    ActivityPackage findById(long id);

    @Query("DELETE FROM activityPackage WHERE id = :id")
    void deletePackage(long id);
}
