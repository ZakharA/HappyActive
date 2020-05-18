package edu.monash.student.happyactive.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.monash.student.happyactive.data.converters.ArthritisConditionConverter;
import edu.monash.student.happyactive.data.converters.DateConverters;
import edu.monash.student.happyactive.data.converters.PrefAccessConverter;
import edu.monash.student.happyactive.data.converters.PrefFrequencyConverter;
import edu.monash.student.happyactive.data.converters.PromptConverters;
import edu.monash.student.happyactive.data.converters.StatusConverters;
import edu.monash.student.happyactive.data.converters.UserAgeConverter;
import edu.monash.student.happyactive.data.converters.UserGenderConverter;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityJournalDao;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityPackageDao;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivityPhotoDao;
import edu.monash.student.happyactive.data.dao.ActivityPackageDao.ActivitySessionDao;
import edu.monash.student.happyactive.data.dao.PreferencesDao.PreferencesDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareAverageReportsDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.CompareHistoryReportsDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.OverallActivityReportsDao;
import edu.monash.student.happyactive.data.dao.ReportsDao.PostActivityStatsDao;
import edu.monash.student.happyactive.data.dao.UserScoreDao.UserScoreDao;
import edu.monash.student.happyactive.data.entities.ActivityJournal;
import edu.monash.student.happyactive.data.entities.ActivityPackage;
import edu.monash.student.happyactive.data.entities.ActivitySession;
import edu.monash.student.happyactive.data.entities.InteractivePrompt;
import edu.monash.student.happyactive.data.entities.SessionPhoto;
import edu.monash.student.happyactive.data.entities.Task;
import edu.monash.student.happyactive.data.entities.UserPref;
import edu.monash.student.happyactive.data.entities.UserScore;


@Database(entities = {ActivitySession.class, ActivityPackage.class, ActivityJournal.class, SessionPhoto.class, Task.class, UserPref.class, UserScore.class, InteractivePrompt.class}, exportSchema = true, version = 6)
@TypeConverters({DateConverters.class, StatusConverters.class, PrefAccessConverter.class, PrefFrequencyConverter.class,  PromptConverters.class, ArthritisConditionConverter.class, UserAgeConverter.class, UserGenderConverter.class})
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
    public abstract UserScoreDao userScoreDao();
    
    private static ActivityPackageDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ActivityPackage ADD COLUMN activityLevel INTEGER NOT NULL DEFAULT 0");
        }
    };

    static final Migration MIGRATION_2_6 = new Migration(2, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `ActivitySession` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `activityId` INTEGER NOT NULL, `currentTaskId` INTEGER NOT NULL, `status` INTEGER, `stepCount` INTEGER NOT NULL, `startDateTime` INTEGER, `completedDateTime` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ActivityPackage` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT, `description` TEXT, `materials` TEXT, `type` TEXT, `approxTimeToComplete` INTEGER NOT NULL, `activityLevel` INTEGER NOT NULL, `parkAccess` INTEGER, `gardenAccess` INTEGER, `distance` INTEGER, `suitMaxArthritisCondition` INTEGER, `isUserPreferred` INTEGER NOT NULL, `imagePath` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ActivityJournal` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `entry` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `SessionPhoto` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `path` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Task` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `activityId` INTEGER NOT NULL, `title` TEXT, `description` TEXT, `imagePath` TEXT, `promptType` INTEGER)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UserPref` (`id` INTEGER NOT NULL, `hobbyList` TEXT, `gardenAccess` INTEGER, `parkAccess` INTEGER, `arthritisCondition` INTEGER, `activityTime` INTEGER, `activityDistance` INTEGER, `userAge` INTEGER, `userGender` INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UserScore` (`id` INTEGER NOT NULL, `currentScore` INTEGER NOT NULL, `oldScore` INTEGER NOT NULL, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `InteractivePrompt` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sessionId` INTEGER NOT NULL, `taskId` INTEGER NOT NULL, `answer` TEXT, `promptType` INTEGER)");
            database.execSQL("ALTER TABLE `Task` ADD COLUMN `promptType` INTEGER");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `materials` TEXT");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `type` TEXT");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `approxTimeToComplete` INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `parkAccess` INTEGER");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `gardenAccess` INTEGER");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `distance` INTEGER");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `suitMaxArthritisCondition` INTEGER");
            database.execSQL("ALTER TABLE `ActivityPackage` ADD COLUMN `isUserPreferred` INTEGER NOT NULL DEFAULT 0");
            database.execSQL("INSERT INTO UserPref (id) VALUES (1)");
            database.execSQL("INSERT INTO UserScore (id, currentScore, oldScore) VALUES (1, 0, 0)");
        }
    };

    public static ActivityPackageDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActivityPackageDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActivityPackageDatabase.class, "happyActiveDB")
                            .createFromAsset("database/happyActiveDB.db")
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_6)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
