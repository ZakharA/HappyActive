package edu.monash.student.happyactive.data.dao.ActivityPackageDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import edu.monash.student.happyactive.data.entities.SessionPhoto;

@Dao
public abstract class ActivityPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long saveSessionPhoto(SessionPhoto sessionPhoto);
}
