package edu.monash.student.happyactive.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.monash.student.happyactive.data.entities.ActivitySession;

@Database(entities = {ActivitySession.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ActivityPackageDao activityPackageDao();
}
