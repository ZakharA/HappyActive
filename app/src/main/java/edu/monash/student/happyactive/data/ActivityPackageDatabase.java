package edu.monash.student.happyactive.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.monash.student.happyactive.data.converters.PrefAccessConverter;
import edu.monash.student.happyactive.data.converters.PrefFrequencyConverter;
import edu.monash.student.happyactive.data.dao.PreferencesDao.PreferencesDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareAverageReportsDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareHistoryReportsDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.OverallActivityReportsDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.PostActivityStatsDao;
import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.SessionPhoto;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.entities.UserScore;

@Database(entities = {ActivitySession.class, ActivityPackage.class, ActivityJournal.class, SessionPhoto.class, Task.class, UserPref.class, UserScore.class}, exportSchema = true, version = 2)
@TypeConverters({DateConverters.class, StatusConverters.class, PrefAccessConverter.class, PrefFrequencyConverter.class})
public abstract class ActivityPackageDatabase extends RoomDatabase {

    public abstract ActivityPackageDao activityPackageDao();
    public abstract ActivitySessionDao activitySessionDao();
    public abstract ActivityJournalDao activityJournalDao();
    public abstract ActivityPhotoDao activityPhotoDao();
    public abstract OverallActivityReportsDao overallActivityReportsDao();
    public abstract CompareHistoryReportsDao compareHistoryReportsDao();
    public abstract CompareAverageReportsDao compareAverageReportsDao();
    public abstract PostActivityStatsDao postActivityStatsDao();
    public abstract PreferencesDao preferencesDao();
    
    private static ActivityPackageDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ActivityPackage ADD COLUMN activityLevel INTEGER NOT NULL DEFAULT 0");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UserPref` (`id` INTEGER NOT NULL, `hobbyList` TEXT, `exerciseFreq` INTEGER, `grandparentInteractionFreq` INTEGER, `gardenAccess` INTEGER, `dogAccess` INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UserScore` (`id` INTEGER NOT NULL, `currentScore` INTEGER NOT NULL, PRIMARY KEY(`id`))");
            database.execSQL("INSERT INTO UserPref (id) VALUES (1)");
            database.execSQL("INSERT INTO UserScore (id, currentScore) VALUES (1, 0)");
        }
    };

    public static ActivityPackageDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActivityPackageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActivityPackageDatabase.class, "happyActiveDB")
                            .createFromAsset("database/happyActiveDB.db")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
