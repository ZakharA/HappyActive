package edu.monash.student.happyactive.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.monash.student.happyactive.data.entities.ActivityJournal;

@Dao
public abstract class ActivityJournalDao {

    @Query("SELECT * FROM activityJournal")
    public abstract LiveData<List<ActivityJournal>> getAllEntries();

    @Insert
    public abstract long insertNewEntry(ActivityJournal journal);

    @Query("SELECT * FROM activityjournal WHERE id = :id")
    public abstract ActivityJournal findById(long id);
}
