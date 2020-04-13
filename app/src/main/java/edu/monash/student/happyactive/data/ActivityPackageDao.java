package edu.monash.student.happyactive.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityPackage;

@Dao
public interface ActivityPackageDao {
    @Query("Select * FROM activitypackage")
    public List<ActivityPackage> getAllActivityPackages();
}
