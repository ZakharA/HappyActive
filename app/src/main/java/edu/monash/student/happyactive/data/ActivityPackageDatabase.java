package edu.monash.student.happyactive.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.SessionPhoto;
import edu.monash.student.happyactive.data.entities.Task;

@Database(entities = {ActivitySession.class, ActivityPackage.class, ActivityJournal.class, SessionPhoto.class, Task.class}, exportSchema = true, version = 1)
@TypeConverters({DateConverters.class, StatusConverters.class})
public abstract class ActivityPackageDatabase extends RoomDatabase {

    public abstract ActivityPackageDao activityPackageDao();
    public abstract ActivitySessionDao activitySessionDao();
    public abstract ActivityJournalDao activityJournalDao();
    private static ActivityPackageDatabase INSTANCE;

    public static ActivityPackageDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActivityPackageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActivityPackageDatabase.class, "happyActiveDB")
                            .createFromAsset("database/happyActiveDB.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
