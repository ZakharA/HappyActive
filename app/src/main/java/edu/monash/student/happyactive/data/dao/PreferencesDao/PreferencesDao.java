package edu.monash.student.happyactive.data.dao.PreferencesDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import edu.monash.student.happyactive.data.entities.UserPref;

/**
 * DAO class for Preferences screen.
 */
@Dao
public abstract class PreferencesDao {

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = UserPref.class)
    public abstract void updatePreferences(UserPref userPref);

    @Query("SELECT * FROM UserPref WHERE id = :id")
    public abstract UserPref getPreferences(long id);
}
