package edu.monash.student.happyactive.data;

import android.app.Activity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;

@Dao
public interface ActivityPackageDao {
    @Query("SELECT * FROM activityPackage")
    public List<ActivityPackage> getAllActivityPackages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertActivity(ActivityPackage activityPackage);

    @Query("SELECT * FROM activityPackage WHERE id = :id")
    public ActivityPackage findById(long id);

    @Query("DELETE FROM activityPackage WHERE id = :id")
    public void deletePackage(long id);
}
