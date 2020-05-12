package edu.monash.student.happyactive.data.dao.UserScoreDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.entities.UserScore;

/**
 * DAO class for Preferences screen.
 */
@Dao
public abstract class UserScoreDao {

    @Query("SELECT * FROM UserScore WHERE id = :id")
    public abstract UserScore getUserScoreData(long id);

    @Query("SELECT * FROM UserScore WHERE id = :id")
    public abstract LiveData<UserScore> getUserScoreLiveData(long id);

    @Update(onConflict = OnConflictStrategy.REPLACE, entity = UserScore.class)
    public abstract void updateUserScoreData(UserScore userScore);

    @Query("SELECT currentScore from UserScore WHERE id = :id")
    public abstract long getCurrentUserScoreForPref(long id);
}
